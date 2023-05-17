package com.aiops_web.service.mysql.impl;

import com.aiops_web.dao.mysql.*;
import com.aiops_web.dto.ExecStepDTO;
import com.aiops_web.dto.Neo4jRelationshipDto;
import com.aiops_web.entity.elasticsearch.OriginalData;
import com.aiops_web.entity.mysql.*;
import com.aiops_web.service.mysql.AnomalyInfoService;
import com.aiops_web.service.mysql.OriginalDataService;
import com.aiops_web.service.mysql.ReportService;
import com.aiops_web.service.mysql.WorkflowExecService;
import com.aiops_web.service.neo4j.KnowledgeGraphService;
import com.aiops_web.utils.Utils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author
 * @since 2023-04-12
 */
@Service
public class WorkflowExecServiceImpl extends ServiceImpl<WorkflowExecMapper, WorkflowExec> implements WorkflowExecService {

    @Resource
    WorkflowConfigMapper workflowConfigMapper;

    @Resource
    StepConfigMapper stepConfigMapper;

    @Resource
    ExecDataTypeEnumMapper execDataTypeEnumMapper;

    @Resource
    WorkflowExecMapper workflowExecMapper;

    @Resource
    WorkflowStatusEnumMapper workflowStatusEnumMapper;

    @Resource
    AiopsAlgMapper aiopsAlgMapper;

    @Resource
    ParsedLogMapper parsedLogMapper;

    @Resource
    VectorizedLogMapper vectorizedLogMapper;

    @Resource
    OriginalDataService originalDataService;

    @Resource
    AnodetectResultMapper anodetectResultMapper;

    @Lazy
    @Resource
    ReportService reportService;

    @Resource
    AnomalyInfoService anomalyInfoService;

    @Resource
    RootcauseResultMapper rootcauseResultMapper;

    @Resource
    KnowledgegraphResultMapper knowledgegraphResultMapper;

    @Lazy
    @Resource
    KnowledgeGraphService knowledgeGraphService;

    Utils utils = new Utils();

    // 简单包装的工具方法
    // 查一个执行步骤
    @Override
    public ExecStepDTO getOneExecStep(Integer wfId, Integer stepNum) {
        return workflowExecMapper.selectOneExecStep(wfId, stepNum);
    }

    // 单步执行
    @Override
    public Integer saveOneExecByStep(Integer stepId, Integer inputTypeId, String inputId) {
        // 1. 基本信息准备
        // 1.1 如果执行表中已经有该步骤, 则不能进行该执行(否则后面结果会非常混乱)
        QueryWrapper<WorkflowExec> wrapper_precheck = new QueryWrapper<>();
        wrapper_precheck.eq("step_id",stepId);
        WorkflowExec workflowExec_precheck = workflowExecMapper.selectOne(wrapper_precheck);
//        System.out.println(workflowExec_precheck);
        if (workflowExec_precheck != null) {
            System.out.println("同一个步骤不能执行两次!");
            return 0;
        }
        // 1.2 该单步执行是被允许的, 先拿到步骤信息并完善信息
        System.out.println("1. 先拿到步骤信息并完善信息");
        StepConfig stepConfig = stepConfigMapper.selectById(stepId);
        if (stepConfig == null) {
            System.out.println("该步骤不存在!");
            return 0;
        }
        System.out.println(stepConfig);

        if (inputTypeId == null || inputId == null || inputId.equals("")) {
            if (stepConfig.getStepNum() == 1) { // 第一步
                // 先找到源日志的枚举id
                QueryWrapper<ExecDataTypeEnum> wrapper = new QueryWrapper<>();
                wrapper.eq("name","源日志");
                // 赋值
                inputTypeId = execDataTypeEnumMapper.selectOne(wrapper).getTypeId();
                inputId = "8|1|100";
            }
            else {
                // 先找到上一个流程步骤
                // 注意这里判断空指针
                ExecStepDTO execStepDTO = workflowExecMapper.selectOneExecStep(stepConfig.getWfId(), (stepConfig.getStepNum() - 1));
                // 赋值
                inputTypeId = execStepDTO.getOutputTypeId();
                inputId = execStepDTO.getOutputId();
//                QueryWrapper<StepConfig> wrapper_1 = new QueryWrapper<>();
//                wrapper_1.eq("wf_id",stepConfig.getWfId()).eq("step_num", stepConfig.getStepNum() - 1);
//                StepConfig stepConfig_1 = stepConfigMapper.selectOne(wrapper_1);
            }
        }

        // 2. 开始执行并确定输入信息
        System.out.println("2. 开始执行并确定输入信息");
        WorkflowExec workflowExec = new WorkflowExec();
        workflowExec.setStepId(stepId);
        workflowExec.setInputTypeId(inputTypeId);
        workflowExec.setInputId(inputId);
        workflowExec.setReportId(-1); // 这里先不生成报告
        // 2.1 先判断执行类型: 类型和step_type_enum表中对应，分别为
        // 日志清洗、日志解析、日志向量化、日志异常检测、根因分析、生成知识图谱，编号从2开始递增
        // 在执行调度方法中判断

        // 2.2 调用函数，函数类型为void, 函数返回值为workflowExec的引用，函数参数包括步骤具体信息和要处理的执行信息
        System.out.println("2.2 调用函数");
        boolean execB = execAIops(stepConfig, workflowExec);
        if (!execB) {
            System.out.println("执行出现错误");
            return 0;
        }
        workflowExec.setTstamp(utils.getCurrentTstamp());
        System.out.println("The final workflowExec is: " + workflowExec);
//        return -1;

        // 3. 结束执行，根据workflowExec的情况生成报告
        // 此时，workflowExec的信息应已完成, 可以插入
        System.out.println("3. 结束执行，根据workflowExec的情况生成报告");
        reportService.saveOneReportByExec(workflowExec);
//        return 1;
        workflowExecMapper.insert(workflowExec);

        // 4. 最后再修改流程表
        System.out.println("4. 最后再修改流程表");
        QueryWrapper<WorkflowStatusEnum> wrapper = new QueryWrapper<>();
        wrapper.eq("name","执行中");
        int statusId = workflowStatusEnumMapper.selectOne(wrapper).getStatusId();

        WorkflowConfig workflowConfig = workflowConfigMapper.selectById(stepConfig.getWfId());
        if (workflowConfig.getStatusId() < statusId) { // 小于"执行中"的只能是未执行
            workflowConfig.setStatusId(statusId);
        }
        workflowConfig.setCurrentStep(stepConfig.getStepNum());
        workflowConfigMapper.updateById(workflowConfig);

        System.out.println("5. 单步执行完成");
        return workflowExec.getExecId();
    }

    // 执行调度
    public boolean execAIops(StepConfig stepConfig, WorkflowExec workflowExec) {
        // 1. 取出对应算法并更新执行信息
        AiopsAlg aiopsAlg = aiopsAlgMapper.selectById(stepConfig.getAlgId());
        if (stepConfig.getTypeId() != 1) {
            workflowExec.setOutputTypeId(stepConfig.getTypeId());
        }
        // 2. 判断type_id进入对应执行方法
        switch (stepConfig.getTypeId()) {
            case 1: // 目前只考虑源日志
                workflowExec.setOutputTypeId(1);
                System.out.println("Execute a Data Source Algorithm.");
                return execAIops_OriginalLog(stepConfig.getParam(), workflowExec);
            case 2:
                System.out.println("Execute a Log Cleaning Algorithm.");
                break;
            case 3:
                System.out.println("Execute a Log Parsing Algorithm.");
                return execAIops_LogParsing(aiopsAlg, stepConfig.getParam(), workflowExec);
            case 4:
                System.out.println("Execute a Log Vectorizing Algorithm.");
                return execAIops_LogVectorizing(aiopsAlg, stepConfig.getParam(), workflowExec);
            case 5:
                System.out.println("Execute a Log Anomaly Detecting Algorithm.");
                return execAIops_LogAnoDetecting(aiopsAlg, stepConfig.getParam(), workflowExec);
            case 6:
                System.out.println("Execute a Root Cause Analyzing Algorithm.");
                return execAIops_Rootcause(aiopsAlg, stepConfig.getParam(), workflowExec);
            case 7:
                System.out.println("Execute a Knowledge Graph Generating Algorithm.");
                return execAIops_genKGByLog(aiopsAlg, stepConfig.getParam(), workflowExec);
            default:
                System.out.println("Execute an Unknown Algorithm.");
                return false;
        }
        return false;
    }

    // 源日志选择
    public boolean execAIops_OriginalLog(String params, WorkflowExec workflowExec) {
        // 先获取输入值
        // 没有其他输入值

        // 2. 调用算法
        // 此处应该用到aiopsAlg, params等, 目前仅供模拟

        // 3. 生成输出结果
        // 只剩下outputId需要更新，看下和inputId的对应关系
        String inputId = workflowExec.getInputId();
        workflowExec.setOutputId(inputId);

        return true;
    }

    // 日志清洗
    public boolean execAIops_LogCleaning(AiopsAlg aiopsAlg, String params, WorkflowExec workflowExec) {
        // 先获取输入值
        if (workflowExec.getInputTypeId() == 1) { // 源日志
            List<OriginalData> inputDataList = getInOutData(workflowExec.getInputTypeId(),workflowExec.getInputId(),0);
        }
        else { // 其他都不可能了
            return false;
        }

        // 2. 调用算法
        // 此处应该用到aiopsAlg, params等, 目前仅供模拟

        // 3. 生成输出结果
        // 只剩下outputId需要更新，看下和inputId的对应关系
        workflowExec.setOutputId("201|300");

        return true;
    }

    // 日志解析
    public boolean execAIops_LogParsing(AiopsAlg aiopsAlg, String params, WorkflowExec workflowExec) {
        // 先获取输入值
        if (workflowExec.getInputTypeId() == 1) { // 源日志
            List<OriginalData> inputDataList = getInOutData(workflowExec.getInputTypeId(),workflowExec.getInputId(),0);
        }
        else if (workflowExec.getInputTypeId() == 2) { // 清洗后的日志
            ;
        }
        else { // 其他都不可能了
            return false;
        }

        // 2. 调用算法
        // 此处应该用到aiopsAlg, params等, 目前仅供模拟

        // 3. 生成输出结果
        // 只剩下outputId需要更新，看下和inputId的对应关系
        workflowExec.setOutputId("201|300");

        return true;
    }

    // 日志向量化
    public boolean execAIops_LogVectorizing(AiopsAlg aiopsAlg, String params, WorkflowExec workflowExec) {
        // 先获取输入值
        if (workflowExec.getInputTypeId() == 1) { // 源日志
            List<OriginalData> inputDataList = getInOutData(workflowExec.getInputTypeId(),workflowExec.getInputId(),0);
        }
        else if (workflowExec.getInputTypeId() == 2) { // 清洗后的日志
            ;
        }
        else if (workflowExec.getInputTypeId() == 3) { // 结构化的日志
            List<ParsedLog> inputDataList = getInOutData(workflowExec.getInputTypeId(),workflowExec.getInputId(),0);
        }
        else { // 其他都不可能了
            return false;
        }

        // 2. 调用算法
        // 此处应该用到aiopsAlg, params等, 目前进攻模拟

        // 3. 生成输出结果
        // 只剩下outputId需要更新，看下和inputId的对应关系
        workflowExec.setOutputId("1|101");

        return true;
    }

    // 日志故障检测
    public boolean execAIops_LogAnoDetecting(AiopsAlg aiopsAlg, String params, WorkflowExec workflowExec) {
        // 先获取输入值
        if (workflowExec.getInputTypeId() == 1) { // 源日志
            List<OriginalData> inputDataList = getInOutData(workflowExec.getInputTypeId(),workflowExec.getInputId(),0);
        }
        else if (workflowExec.getInputTypeId() == 2) { // 清洗后的日志
            ;
        }
        else if (workflowExec.getInputTypeId() == 3) { // 结构化的日志
            List<ParsedLog> inputDataList = getInOutData(workflowExec.getInputTypeId(),workflowExec.getInputId(),0);
        }
        else if (workflowExec.getInputTypeId() == 4) { // 向量化的日志
            List<VectorizedLog> inputDataList = getInOutData(workflowExec.getInputTypeId(),workflowExec.getInputId(),0);
        }
        else { // 其他都不可能了
            return false;
        }

        // 2. 调用算法
        // 此处应该用到aiopsAlg, params等, 目前进攻模拟

        // 3. 生成输出结果
        // 只剩下outputId需要更新，看下和inputId的对应关系
        String anodetectOutput = "100-200|300-400";

        // 4. 插入故障检测结果表并更新执行输出OutputId
        String[] anodetectOutputArray = anodetectOutput.split("\\|");
        long startId = 0;
        long endId = 0;
        for (int i = 0; i < anodetectOutputArray.length; i++) {
            AnodetectResult anodetectResult = new AnodetectResult();
            anodetectResult.setSourceDataSection(anodetectOutputArray[i]);
            anodetectResult.setDeleted(0);
            System.out.println(anodetectResult);
            anodetectResultMapper.insert(anodetectResult);
            if (i == 0) {
                startId = anodetectResult.getAdrId();
//                startId = i + 1;
            }
            else if (i == anodetectOutputArray.length - 1) {
                endId = anodetectResult.getAdrId();
//                endId = i + 1;
            }
        }
        workflowExec.setOutputId(startId + "|" + endId);
        System.out.println("After Ano Detection, the Workflow is: " + workflowExec);

        // 5. 插入故障检测信息表
        boolean anoInfoResult = anomalyInfoService.saveAnoInfoByExec(workflowExec);
        if (!anoInfoResult) {
            System.out.println("插入故障检测信息表出现错误!");
            return false;
        }

        return true;
    }

    // 日志根因分析
    public boolean execAIops_Rootcause(AiopsAlg aiopsAlg, String params, WorkflowExec workflowExec) {
        // 先获取输入值
        List<AnodetectResult> anodetectResultList = new ArrayList<>();
        if (workflowExec.getInputTypeId() == 5) { // 故障检测结果
            anodetectResultList = getInOutData(workflowExec.getInputTypeId(),workflowExec.getInputId(),0);
        }
        else { // 其他都不可能了
            return false;
        }

        // 2. 调用算法
        // 此处应该用到aiopsAlg, params等, 目前进攻模拟

        // 3. 生成输出结果
        // 只剩下outputId需要更新，看下和inputId的对应关系
        if (anodetectResultList.isEmpty()) {
            return false;
        }

        // 4. 插入故障检测结果表并更新执行输出OutputId
        long startId = 0;
        long endId = 0;
        for (int i = 0; i < anodetectResultList.size(); i++) {
            AnodetectResult anodetectResult = anodetectResultList.get(i);
            RootcauseResult rootcauseResult = new RootcauseResult();
            rootcauseResult.setSourceDataSection(anodetectResult.getSourceDataSection());
            String path = genRootCausePath(anodetectResult.getSourceDataSection());
            rootcauseResult.setPath(path);
            rootcauseResult.setDeleted(0);
//            System.out.println(rootcauseResult);
            rootcauseResultMapper.insert(rootcauseResult);
            if (i == 0) {
                startId = rootcauseResult.getRcrId();
            }
            else if (i == anodetectResultList.size() - 1) {
                endId = rootcauseResult.getRcrId();
            }
        }
        workflowExec.setOutputId(startId + "|" + endId);
//        System.out.println("After Root Cause Analysis, the Workflow is: " + workflowExec);

        return true;
    }

    public String genRootCausePath(String sourceDataSection) {
        List<String> path = new ArrayList<>();
        switch (sourceDataSection){
            case "100-200":
                path.add("ts-train-service->ts-travel-service->ts-travel2-service");
                path.add("ts-station-service->ts-station-food-service");
                break;
            case "200-300":
                path.add("ts-order-service->ts-order-other-service");
                path.add("ts-price-service->ts-payment-service");
                break;
            case "300-400":
                path.add("nacosdb-mysql->nacosdb-mysql-leader->nacosdb-mysql-follower");
                path.add("skywalking->skywalking-ui");
                break;
            case "400-500":
                path.add("ts-food-service->ts-delivery-service");
                path.add("ts-consign-service->ts-consign-price-service");
                break;
            default:
                path.add("ts-preserve-service->ts-preserve-other-service");
                break;
        }
        return path.toString();
    }

    // 根据日志根因分析生成知识图谱
    public boolean execAIops_genKGByLog(AiopsAlg aiopsAlg, String params, WorkflowExec workflowExec) {
        // 先获取输入值
        List<RootcauseResult> rootcauseResultList = new ArrayList<>();
        if (workflowExec.getInputTypeId() == 6) { // 故障检测结果
            rootcauseResultList = getInOutData(workflowExec.getInputTypeId(),workflowExec.getInputId(),0);
        }
        else { // 其他都不可能了
            return false;
        }

        // 2. 调用算法
        // 此处应该用到aiopsAlg, params等, 目前进攻模拟

        // 3. 生成输出结果(需要到第4步后才能生成outputId)
        // 只剩下outputId需要更新，看下和inputId的对应关系
        if (rootcauseResultList.isEmpty()) {
            return false;
        }

        // 4. 插入故障检测结果表并更新执行输出OutputId
        long startId = 0;
        long endId = 0;
        for (int i = 0; i < rootcauseResultList.size(); i++) {
            // 每一条根因数据new一个kgR数据, 存入List<Name>
            RootcauseResult rootcauseResult = rootcauseResultList.get(i);
            KnowledgegraphResult knowledgegraphResult = new KnowledgegraphResult();
            List<String> rcNodeNameList = utils.String2List(rootcauseResult.getPath());
            knowledgegraphResult.setSourceDataSection(rootcauseResult.getSourceDataSection()); // 1. 这部分表示故障数据的序号，不需要更新
            knowledgegraphResult.setRootcauseNodeNames(rcNodeNameList.toString()); // 2. kgR表中的rootcause_node_names字段和rcR表中的path字段应相同
            // 调用genKG, 更新KnowledgegraphResult表中的一条数据(还未插入)
            genKG(knowledgegraphResult);
//            System.out.println(rootcauseResult);
            // 将knowledgegraphResult插入KnowledgegraphResult表
            knowledgegraphResult.setDeleted(0);
            System.out.println("knowledgegraphResult: ");
            System.out.println(knowledgegraphResult);
            System.out.println();
            knowledgegraphResultMapper.insert(knowledgegraphResult);
            if (i == 0) {
                startId = knowledgegraphResult.getKgrId();
            }
            else if (i == rootcauseResultList.size() - 1) {
                endId = knowledgegraphResult.getKgrId();
            }
        }
        // 生成输出结果
        workflowExec.setOutputId(startId + "|" + endId);
//        System.out.println("After Knowledge Graph Generation, the Workflow is: " + workflowExec);

        return true;
    }

    // 根据KnowledgegraphResult表中的一条记录中的List<Name>(rootcause_node_names)更新该记录中的其他数据
    public boolean genKG(KnowledgegraphResult knowledgegraphResult) {
        if (knowledgegraphResult == null) {
            return false;
        }
        // 1., 2., 见上
        // 1. 为source_data_section, 不需要更新
        // 2. kgR表中的rootcause_node_names字段和rcR表中的path字段应相同, 也不需要更新
        String rootcause_node_names = knowledgegraphResult.getRootcauseNodeNames();
        List<String> rcNodeNameList = utils.String2List(rootcause_node_names);

//        knowledgegraphResult.setRootcauseNodeNames(rcNodeNameList.toString()); 不用这一步了

        // 3 根据上述name, 生成rootcause_node_ids字段
        List<String> rcNodeIdList = new ArrayList<>();
        List<Long> rcNodeIDList_rd = new ArrayList<>(); // 去重nodeids, 为第5步准备
        for (int i = 0; i < rcNodeNameList.size(); i++) {
            String[] nodeNameList = rcNodeNameList.get(i).split("->");
            StringBuilder rcNodeIdsSb = new StringBuilder("");
            for (String nodeName : nodeNameList) {
                Long rcNodeId = knowledgeGraphService.getNodeByNameInService(nodeName).getId();

                // 判断是否加到rcNodeIDList_rd中
                if (!rcNodeIDList_rd.contains(rcNodeId)) {
                    rcNodeIDList_rd.add(rcNodeId);
                }

                rcNodeIdsSb.append(rcNodeId);
                rcNodeIdsSb.append("|");
            }
            rcNodeIdsSb.deleteCharAt(rcNodeIdsSb.length() - 1); // 删除最后一个|
            rcNodeIdList.add(rcNodeIdsSb.toString());
        }
        knowledgegraphResult.setRootcauseNodeIds(rcNodeIdList.toString());

        // 4. 根据上述rootcause_node_ids, 生成rootcause_relation_ids字段(并存入neo4j)
        List<String> rcRelationIdList = knowledgeGraphService.saveRelationshipsByNodeIdsStringList(rcNodeIdList);
        knowledgegraphResult.setRootcauseRelationIds(rcRelationIdList.toString());

        // 5. 根据rcNodeIdList得到去重的nodeIds
        // 所有去重的nodeIds都已在rcNodeIDList_rd中

        // 6. 根据rcNodeIDList_rd获取all_node_ids, 并生成表中all_node_ids字段
        List<Long> allNodeIdList = knowledgeGraphService.getRelevantNodesIdsByNodeIds(rcNodeIDList_rd);
        StringBuilder allNodeIdSb = new StringBuilder("");
        for (Long allNodeId : allNodeIdList) {
            allNodeIdSb.append(allNodeId);
            allNodeIdSb.append("|");
        }
        allNodeIdSb.deleteCharAt(allNodeIdSb.length() - 1); // 删除最后一个|
        knowledgegraphResult.setAllNodeIds(allNodeIdSb.toString());

        // 7. 调用方法, 根据all_node_ids先获取到Neo4jRelationshipDto, 再生成表中all_relation_ids字段
        List<Neo4jRelationshipDto> allRelaDTOList = knowledgeGraphService.getRelevantRelationshipsByNodeIds(allNodeIdList);
        StringBuilder allRelationIdSb = new StringBuilder("");
        for (Neo4jRelationshipDto neo4jRelationshipDto : allRelaDTOList) {
            allRelationIdSb.append(neo4jRelationshipDto.getRId());
            allRelationIdSb.append("|");
        }
        allRelationIdSb.deleteCharAt(allRelationIdSb.length() - 1); // 删除最后一个|
        knowledgegraphResult.setAllRelationIds(allRelationIdSb.toString());

        // 8. 完成genKG所有内容, 更新了一条knowledgegraph_result表的数据

        return true;

    }

    // 更新知识图谱
    @Override
    public boolean updateAllKGR() {
        // 因为生成了新的知识图谱, 所以相关Node和Relation的Id都会发生变化(但假定Name不会变), 所以要更新所有的knowledgegraph_result表
        List<KnowledgegraphResult> knowledgegraphResultList = knowledgegraphResultMapper.selectList(null);
        for (int i = 0; i < knowledgegraphResultList.size(); i++) {
            KnowledgegraphResult knowledgegraphResult = knowledgegraphResultList.get(i);
            boolean genResult = genKG(knowledgegraphResult);
            if (!genResult) {
                return false;
            }
            // 将更新后的结果重新插入表中
            int updateResult = knowledgegraphResultMapper.updateById(knowledgegraphResult);
            if (updateResult < 1) {
                return false;
            }
        }
        return true;
    }

    // 获取相关数据调度
    // result == 1表示返回的是报告, result == 0 表示是拿真实数据
    public List getInOutData(Integer dataTypeId, String dataIds, Integer result) {
        long startId = 0L;
        long endId = 0L;
        if (dataTypeId != 1) {
            String[] temp = dataIds.split("\\|");
            if (temp.length != 2) {
                System.out.println("dataIds有误");
                return null;
            }
            startId = Long.parseLong(temp[0]);
            endId = Long.parseLong(temp[1]);
        }
        switch (dataTypeId) {
            case 1: // 目前只考虑源日志
                return getInOutData_OriginalLog(dataIds, result);
            case 2: // 清洗后日志
                return getInOutData_CleanedLog(startId, endId, result);
            case 3: // 结构化日志
                return getInOutData_ParsedLog(startId, endId, result);
            case 4: // 向量化日志
                return getInOutData_VectorizedLog(startId, endId, result);
            case 5: // 异常检测结果
                return getInOutData_AnodetectResult(startId, endId, result);
            case 6: // 根因分析结果
                return getInOutData_RootCauseResult(startId, endId, result);
            case 7: // 知识图谱结果
                return getInOutData_KGResult(startId, endId, result);
            default:
                return null;
        }
//        return null;
    }

    // 获取源日志数据 (还需要和DQL整合)
    public List getInOutData_OriginalLog(String dataIds, Integer result) {
        // 取出相关信息
        String[] temp = dataIds.split("\\|");
        if (temp.length != 3) {
            System.out.println("dataIds有误");
            return null;
        }
        int batchId = Integer.parseInt(temp[0]);
        long startId = Long.parseLong(temp[1]);
        long endId = Long.parseLong(temp[2]);

        List<OriginalData> originalLogList = originalDataService.getRelativeRange(batchId, (int)startId, (int)endId);
        if (originalLogList == null) {
            return null;
        }
        // 封装List<String>
        List<String> originalLogStringList = new ArrayList<>();
        for (OriginalData originalData : originalLogList) {
            originalLogStringList.add(
                    "{\"OriginalDataId\": \"" + originalData.getCalcId()
                            + "\", \"BatchId\": \"" + originalData.getBatchId()
                            + "\", \"RelativeId\": \"" + originalData.getRelaId()
                            + "\", \"Content\": \"" + originalData.getContent()
                            + "\"}"
            );
        }
        if (result == 0) {
            return originalLogList;
        }
        else if (result == 1) {
            return originalLogStringList;
        }

        return originalLogStringList;
    }

    // 获取清洗后日志数据
    // 由于实验室算法中将清洗算法和解析算法合并, 所以模拟返回结果和日志解析相同, 只是返回显示的名字有所不同
    public List getInOutData_CleanedLog(Long startId, Long endId, Integer result) {
        QueryWrapper<ParsedLog> wrapper = new QueryWrapper<>();
        wrapper.between("parse_id", startId, endId);
        List<ParsedLog> parsedLogList = parsedLogMapper.selectList(wrapper);
        if (parsedLogList == null) {
            return null;
        }

        // 封装List<String>
        List<String> parsedLogResult = new ArrayList<>();
        for (ParsedLog parsedLog : parsedLogList) {
            parsedLogResult.add(
                    "{\"CleanedId\": \"" + parsedLog.getParseId()
                            + "\", \"LineId\": \"" + parsedLog.getLogLineid()
                            + "\", \"Datetime\": \"" + parsedLog.getLogDate()
                            + "\", \"Timestamp\": \"" + parsedLog.getLogTimestamp()
                            + "\", \"TraceId\": \"" + parsedLog.getLogTraceid()
                            + "\", \"SpanId\": \"" + parsedLog.getLogSpanid()
                            + "\", \"Unknown\": \"" + parsedLog.getLogUnknown()
                            + "\", \"Level\": \"" + parsedLog.getLogLevel()
                            + "\", \"Content\": \"" + parsedLog.getLogContent()
                            + "\", \"EventId\": \"" + parsedLog.getLogEventid()
                            + "\", \"EventTemplate\": \"" + parsedLog.getLogEventtemplate()
                            + "\"}"
            );
        }
        if (result == 0) {
            return parsedLogList;
        }
        else if (result == 1) {
            return parsedLogResult;
        }
        return parsedLogResult;
    }

    // 获取结构化日志数据
    public List getInOutData_ParsedLog(Long startId, Long endId, Integer result) {
        QueryWrapper<ParsedLog> wrapper = new QueryWrapper<>();
        wrapper.between("parse_id", startId, endId);
        List<ParsedLog> parsedLogList = parsedLogMapper.selectList(wrapper);
        if (parsedLogList == null) {
            return null;
        }

        // 封装List<String>
        List<String> parsedLogResult = new ArrayList<>();
        for (ParsedLog parsedLog : parsedLogList) {
            parsedLogResult.add(
                    "{\"ParsedId\": \"" + parsedLog.getParseId()
                            + "\", \"LineId\": \"" + parsedLog.getLogLineid()
                            + "\", \"Datetime\": \"" + parsedLog.getLogDate()
                            + "\", \"Timestamp\": \"" + parsedLog.getLogTimestamp()
                            + "\", \"TraceId\": \"" + parsedLog.getLogTraceid()
                            + "\", \"SpanId\": \"" + parsedLog.getLogSpanid()
                            + "\", \"Unknown\": \"" + parsedLog.getLogUnknown()
                            + "\", \"Level\": \"" + parsedLog.getLogLevel()
                            + "\", \"Content\": \"" + parsedLog.getLogContent()
                            + "\", \"EventId\": \"" + parsedLog.getLogEventid()
                            + "\", \"EventTemplate\": \"" + parsedLog.getLogEventtemplate()
                            + "\"}"
            );
        }
        if (result == 0) {
            return parsedLogList;
        }
        else if (result == 1) {
            return parsedLogResult;
        }
        return parsedLogResult;
    }

    // 获取向量化化日志数据
    public List getInOutData_VectorizedLog(Long startId, Long endId, Integer result) {
        QueryWrapper<VectorizedLog> wrapper = new QueryWrapper<>();
        wrapper.between("vector_id", startId, endId);
        List<VectorizedLog> vectorizedLogList = vectorizedLogMapper.selectList(wrapper);
        if (vectorizedLogList == null) {
            return null;
        }

        List<String> vectorizedLogResult = new ArrayList<>();
        // 封装List<String>
        for (VectorizedLog vectorizedLog : vectorizedLogList) {
            vectorizedLogResult.add(
                    "{\"VectorizedId\": \"" + vectorizedLog.getVectorId()
                            + "\", \"Embedding\": \"" + vectorizedLog.getEmbedding()
                            + "\"}"
            );
//            System.out.println("vectorizedLogResult: " + vectorizedLogResult);
        }
        if (result == 0) {
            return vectorizedLogList;
        }
        else if (result == 1) {
            return vectorizedLogResult;
        }
        return vectorizedLogResult;
    }

    // 获取故障检测结果相关数据
    public List getInOutData_AnodetectResult(Long startId, Long endId, Integer result) {
        QueryWrapper<AnodetectResult> wrapper = new QueryWrapper<>();
        wrapper.between("adr_id", startId, endId);
        List<AnodetectResult> anodetectResultList = anodetectResultMapper.selectList(wrapper);
        if (anodetectResultList == null) {
            return null;
        }

        // 封装List<String>
        List<String> anodetectStringList = new ArrayList<>();
        for (AnodetectResult anodetectResult : anodetectResultList) {
            anodetectStringList.add(
                    "{\"AnomalyDetectionId\": \"" + anodetectResult.getAdrId()
                            + "\", \"Source Data Id Section\": \"" + anodetectResult.getSourceDataSection()
                            + "\"}"
            );
        }
//        System.out.println("anodetectStringList: " + anodetectStringList);

        if (result == 0) {
            return anodetectResultList;
        }
        else if (result == 1) {
            return anodetectStringList;
        }
        return anodetectStringList;
    }

    // 获取根因分析结果相关数据
    public List getInOutData_RootCauseResult(Long startId, Long endId, Integer result) {
        QueryWrapper<RootcauseResult> wrapper = new QueryWrapper<>();
        wrapper.between("rcr_id", startId, endId);
        List<RootcauseResult> rootcauseResultList = rootcauseResultMapper.selectList(wrapper);
        if (rootcauseResultList == null) {
            return null;
        }

        // 封装List<String>
        List<String> rootcauseStringList = new ArrayList<>();
        for (RootcauseResult rootcauseResult : rootcauseResultList) {
            rootcauseStringList.add(
                    "{\"RootcauseId\": \"" + rootcauseResult.getRcrId()
                            + "\", \"Source Data Id Section\": \"" + rootcauseResult.getSourceDataSection()
                            + "\", \"Path\": \"" + rootcauseResult.getPath()
                            + "\"}"
            );
        }

        if (result == 0) {
            return rootcauseResultList;
        }
        else if (result == 1) {
            return rootcauseStringList;
        }
        return rootcauseStringList;
    }

    // 获取生成知识图谱结果结果相关数据
    public List getInOutData_KGResult(Long startId, Long endId, Integer result) {
        QueryWrapper<KnowledgegraphResult> wrapper = new QueryWrapper<>();
        wrapper.between("kgr_id", startId, endId);
        List<KnowledgegraphResult> kgResultList = knowledgegraphResultMapper.selectList(wrapper);
        if (kgResultList == null) {
            return null;
        }

        // 封装List<String>
        List<String> kgStringList = new ArrayList<>();
        for (KnowledgegraphResult knowledgegraphResult : kgResultList) {
            kgStringList.add(
                    "{\"KnowledgeGraphId\": \"" + knowledgegraphResult.getKgrId()
                            + "\", \"Source Data Id Section\": \"" + knowledgegraphResult.getSourceDataSection()
                            + "\", \"Rootcause Related Node Ids:\": \"" + knowledgegraphResult.getRootcauseNodeIds()
                            + "\", \"Rootcause Related Relation Ids:\": \"" + knowledgegraphResult.getRootcauseRelationIds()
                            + "\"}"
            );
        }

        if (result == 0) {
            return kgResultList;
        }
        else if (result == 1) {
            return kgStringList;
        }
        return kgStringList;
    }

    // 从生成知识图谱的表中获取所有根因路径，然后组成一个大的List<String>
    @Override
    public List<String> getAllRCIds() {
        List<String> allRCIdsList = new ArrayList<>();
        List<KnowledgegraphResult> knowledgegraphResultList = knowledgegraphResultMapper.selectList(null);
        for (KnowledgegraphResult knowledgegraphResult : knowledgegraphResultList) {
            String rootcauseIds = knowledgegraphResult.getRootcauseNodeIds();
            if (rootcauseIds.length() < 2) {
                System.out.println("rootcauseIds存在问题");
                return null;
            }
            String rootcauseIds_temp = rootcauseIds.substring(1, rootcauseIds.length() - 1);
            List<String> rcIdsList = Arrays.asList(rootcauseIds_temp.split(", "));
            System.out.println("rcIdsList: ");
            System.out.println(rcIdsList);
            System.out.println();
            System.out.println("rcIdsList: ");
            allRCIdsList.addAll(rcIdsList);
            System.out.println();
        }
        return allRCIdsList;
    }

    // 根据步骤id（step_id ）获取到对应的执行的outputids
    @Override
    public String getOutputId(Integer stepId) {
        // 先找到源日志的枚举id
        QueryWrapper<WorkflowExec> wrapper = new QueryWrapper<>();
        wrapper.eq("step_id",stepId);

        WorkflowExec workflowExec = workflowExecMapper.selectOne(wrapper);
        if (workflowExec == null) {
            return "";
        }
        else {
            return workflowExec.getOutputId();
        }
    }

    // 结束流程
    @Override
    public Boolean closeWorkflow(Integer wfId) {
        // 1. 先找执行表，找到对应的报告Id
        List<ExecStepDTO> execStepDTOList = workflowExecMapper.selectExecStepByWf(wfId);


        // 2. 然后再改流程表
        // 2.1 先找到要改的流程
        WorkflowConfig workflowConfig =workflowConfigMapper.selectById(wfId);
        // 2.2 找到结束的枚举id并进行更改
        QueryWrapper<WorkflowStatusEnum> wrapper = new QueryWrapper<>();
        wrapper.eq("name","已完成");
        int statusId = workflowStatusEnumMapper.selectOne(wrapper).getStatusId();
        workflowConfig.setStatusId(statusId);
        // 2.3 将current_step置为-1
        workflowConfig.setCurrentStep(-1);
        // 2.4 整合report_ids, 用List转成的String来表示
        StringBuffer reportIdsBuffer = new StringBuffer("");
        List<Integer> reportIdsList = new ArrayList<>();
        for (ExecStepDTO execStepDTO : execStepDTOList) {
            reportIdsList.add(execStepDTO.getReportId());
        }
        // 再转成String进行更新
        String reportIds = reportIdsList.toString();
        workflowConfig.setReportIds(reportIds);
        return workflowConfigMapper.updateById(workflowConfig) > 0;
    }

    // 根据流程编号获得执行步骤
    @Override
    public List<WorkflowExec> getExecsByWf(Integer wfId) {
        return workflowExecMapper.selectExecByWf(wfId);
    }

    // 步骤回退
    @Override
    public boolean withdrawExec(Integer execId) {
        // 1. 取出要回退的对应执行信息
        WorkflowExec workflowExec = workflowExecMapper.selectById(execId);
        if (workflowExec == null) {
            return false;
        }
        // 2. 报告是一定有的, 所以先删除报告
        boolean delReportResult = reportService.removeById(workflowExec.getReportId());
        if (!delReportResult) {
            return false;
        }
        // 3. 准备各个表要删除的id的区间
        long startId = 0L;
        long endId = 0L;
        if (workflowExec.getOutputTypeId() != 1) {
            String[] temp = workflowExec.getOutputId().split("\\|");
            if (temp.length != 2) {
                System.out.println("outputId有误");
                return false;
            }
            startId = Long.parseLong(temp[0]);
            endId = Long.parseLong(temp[1]);
        }
        // 4. 判断output_type_id进入对应执行方法, 联动删除相关数据
        boolean result;
        switch (workflowExec.getOutputTypeId()) {
            case 1: // 源日志
                System.out.println("Withdraw a Data Source Execution.");
                // 不用做任何操作
                break;
            case 2:
                System.out.println("Withdraw a Log Cleaning Execution.");
                // 不用做任何操作
                break;
            case 3:
                System.out.println("Withdraw a Log Parsing Execution.");
                // 不用做任何操作
                break;
            case 4:
                System.out.println("Withdraw a Log Vectorizing Execution.");
                // 不用做任何操作
                break;
            case 5:
                System.out.println("Withdraw a Log Anomaly Detecting Execution.");
                // 联动删除anomaly_info表和anodetect_result表
                result = withdrawExec_LogAnoDetect(startId, endId, workflowExec);
                if (!result) {
                    return false;
                }
                break;
            case 6:
                System.out.println("Withdraw a Root Cause Analyzing Execution.");
                // 联动删除rootcause_result表
                result = withdrawExec_RootCause(startId, endId);
                if (!result) {
                    return false;
                }
                break;
            case 7:
                System.out.println("Withdraw a Knowledge Graph Generating Execution.");
                // 联动删除knowledgegraph_result表
                result = withdrawExec_KG(startId, endId);
                if (!result) {
                    return false;
                }
                break;
            default:
                System.out.println("Withdraw an Unknown Execution.");
                return false;
        }
        // 最后把这个步骤删除
        int delExecResult = workflowExecMapper.deleteById(execId);
        if (delExecResult < 1) {
            return false;
        }
        return true;
    }

    // 回退故障检测
    public boolean withdrawExec_LogAnoDetect(Long startId, Long endId, WorkflowExec workflowExec) {
        // 先删除anomaly_info中的数据 (直接删除wf_id对应的记录即可)
        StepConfig stepConfig = stepConfigMapper.selectById(workflowExec.getStepId());
        QueryWrapper<AnomalyInfo> wrapper_1 = new QueryWrapper<>();
        wrapper_1.eq("wf_id",stepConfig.getWfId());
        boolean delAnoInfoResult = anomalyInfoService.remove(wrapper_1);
        if (!delAnoInfoResult) {
            return false;
        }

        // 再删除anodetect_result表里的数据
        QueryWrapper<AnodetectResult> wrapper_2 = new QueryWrapper<>();
        wrapper_2.between("adr_id",startId, endId);
        int delAnoDetectResult = anodetectResultMapper.delete(wrapper_2);
        if (delAnoDetectResult < 1) {
            return false;
        }

        return true;
    }

    // 回退根因分析
    public boolean withdrawExec_RootCause(Long startId, Long endId) {
        // 删除rootcause_result表里的数据
        QueryWrapper<RootcauseResult> wrapper = new QueryWrapper<>();
        wrapper.between("adr_id",startId, endId);
        int delResult = rootcauseResultMapper.delete(wrapper);
        if (delResult < 1) {
            return false;
        }
        return true;
    }

    // 回退生成知识图谱
    public boolean withdrawExec_KG(Long startId, Long endId) {
        // 删除rootcause_result表里的数据
        QueryWrapper<KnowledgegraphResult> wrapper = new QueryWrapper<>();
        wrapper.between("kgr_id",startId, endId);
        int delResult = knowledgegraphResultMapper.delete(wrapper);
        if (delResult < 1) {
            return false;
        }
        return true;
    }

}

//        workflowExec.setTstamp(utils.getCurrentTstamp());
//        workflowExec.setReportId(2);
//        workflowExec.setStepId(17);
//        workflowExec.setInputTypeId(2);
//        workflowExec.setInputId("201|300");
//        workflowExec.setOutputTypeId(3);
//        workflowExec.setOutputId("301|400");