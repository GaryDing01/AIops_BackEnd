package com.aiops_web.service.impl;

import com.aiops_web.dao.sql.*;
import com.aiops_web.dto.ExecStepDTO;
import com.aiops_web.entity.sql.*;
import com.aiops_web.service.WorkflowExecService;
import com.aiops_web.utils.Utils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    Utils utils = new Utils();

    // 单步执行
    @Override
    public Integer saveOneExec(Integer stepId, Integer inputTypeId, String inputId) {
        // 1. 先拿到步骤信息并完善信息
        System.out.println("1. 先拿到步骤信息并完善信息");
        StepConfig stepConfig = stepConfigMapper.selectById(stepId);
        System.out.println(stepConfig);

        if (inputTypeId == null || inputId == null) {
            if (stepConfig.getStepNum() == 1) { // 第一步
                // 先找到源日志的枚举id
                QueryWrapper<ExecDataTypeEnum> wrapper = new QueryWrapper<>();
                wrapper.eq("name","源日志");
                // 赋值
                inputTypeId = execDataTypeEnumMapper.selectOne(wrapper).getTypeId();
                inputId = "1|100";
            }
            else {
                // 先找到上一个流程步骤
                // 注意这里判断空指针
                ExecStepDTO execStepDTO = workflowExecMapper.selectOneExecStep(stepConfig.getWfId(), (stepConfig.getStepNum() - 1));
                // 赋值
                inputTypeId = execStepDTO.getOutputTypeId();
                inputId = execStepDTO.getOutputId();
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
        boolean execB = execAIops(stepConfig, workflowExec);
        if (!execB) {
            return 0;
        }
        System.out.println("2.2 调用函数");
        workflowExec.setTstamp(utils.getCurrentTstamp());
        System.out.println(workflowExec);
//        workflowExecMapper.insert(workflowExec);

        // 3. 结束执行，根据workflowExec的情况生成报告


        // 4. 最后再修改流程表
        QueryWrapper<WorkflowStatusEnum> wrapper = new QueryWrapper<>();
        wrapper.eq("name","已完成");
        int statusId = workflowStatusEnumMapper.selectOne(wrapper).getStatusId();

        WorkflowConfig workflowConfig = workflowConfigMapper.selectById(stepConfig.getWfId());
        if (workflowConfig.getStatusId() < statusId) { // 小于"执行中"的只能是未执行
            workflowConfig.setStatusId(statusId);
        }
        workflowConfig.setCurrentStep(stepConfig.getStepNum());

        return 1;
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
                break;
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
                break;
            case 6:
                System.out.println("Execute a Root Cause Analyzing Algorithm.");
                break;
            case 7:
                System.out.println("Execute a Knowledge Graph Generating Algorithm.");
                break;
            default:
                System.out.println("Execute an Unknown Algorithm.");
                return false;
        }
        return false;
    }

    // 日志解析
    public boolean execAIops_LogParsing(AiopsAlg aiopsAlg, String params, WorkflowExec workflowExec) {
        // 先获取输入值
        if (workflowExec.getInputTypeId() == 1) { // 源日志
            ; // 暂存
        }
        else if (workflowExec.getInputTypeId() == 2) { // 清洗后的日志
            ;
        }
        else { // 其他都不可能了
            return false;
        }

        // 2. 调用算法
        // 此处应该用到aiopsAlg, params等, 目前进攻模拟

        // 3. 生成输出结果
        // 只剩下outputId需要更新，看下和inputId的对应关系
        workflowExec.setOutputId("201|300");

        return true;
    }

    // 日志向量化
    public boolean execAIops_LogVectorizing(AiopsAlg aiopsAlg, String params, WorkflowExec workflowExec) {
        // 先获取输入值
        if (workflowExec.getInputTypeId() == 1) { // 源日志
            ; // 暂存
        }
        else if (workflowExec.getInputTypeId() == 2) { // 清洗后的日志
            ;
        }
        else if (workflowExec.getInputTypeId() == 3) { // 结构化的日志
            ;
        }
        else { // 其他都不可能了
            return false;
        }

        // 2. 调用算法
        // 此处应该用到aiopsAlg, params等, 目前进攻模拟

        // 3. 生成输出结果
        // 只剩下outputId需要更新，看下和inputId的对应关系
        workflowExec.setOutputId("301|400");

        return true;
    }

    // 获取相关数据调度
    // result == 1表示返回的是报告, result == 0 表示是拿真实数据
    public List<?> getInOutData(Integer dataTypeId, String dataIds, Integer result) {
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
                break;
            case 2: // 清洗后日志
                break;
            case 3: // 结构化日志
                return getInOutData_ParsedLog(startId, endId, result);
            case 4: // 向量化日志
                return getInOutData_VectorizedLog(startId, endId, result);
            case 5: // 异常检测结果
                break;
            case 6: // 根因分析结果
                break;
            case 7: // 知识图谱结果
                break;
            default:
                return null;
        }
        return null;
    }

    // 获取结构化日志数据
    public List<?> getInOutData_ParsedLog(Long startId, Long endId, Integer result) {
        QueryWrapper<ParsedLog> wrapper = new QueryWrapper<>();
        wrapper.between("parse_id", startId, endId);
        List<ParsedLog> parsedLogList = parsedLogMapper.selectList(wrapper);
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
    public List<?> getInOutData_VectorizedLog(Long startId, Long endId, Integer result) {
        QueryWrapper<VectorizedLog> wrapper = new QueryWrapper<>();
        wrapper.between("vector_id", startId, endId);
        List<VectorizedLog> vectorizedLogList = vectorizedLogMapper.selectList(wrapper);
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
}

//        workflowExec.setTstamp(utils.getCurrentTstamp());
//        workflowExec.setReportId(2);
//        workflowExec.setStepId(17);
//        workflowExec.setInputTypeId(2);
//        workflowExec.setInputId("201|300");
//        workflowExec.setOutputTypeId(3);
//        workflowExec.setOutputId("301|400");