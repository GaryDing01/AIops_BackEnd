package com.aiops_web.service.mysql.impl;

import com.aiops_web.dao.mysql.*;
import com.aiops_web.dto.*;
import com.aiops_web.entity.elasticsearch.OriginalData;
import com.aiops_web.entity.neo4j.Node;
import com.aiops_web.entity.mysql.*;
import com.aiops_web.service.mysql.AnomalyInfoService;
import com.aiops_web.service.mysql.OriginalDataService;
import com.aiops_web.service.neo4j.KnowledgeGraphService;
import com.aiops_web.utils.Utils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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
public class AnomalyInfoServiceImpl extends ServiceImpl<AnomalyInfoMapper, AnomalyInfo> implements AnomalyInfoService {

    @Resource
    AnomalyInfoMapper anomalyInfoMapper;

    @Resource
    AiopsObjEnumMapper aiopsObjEnumMapper;

    @Resource
    AnomalyStatusEnumMapper anomalyStatusEnumMapper;

    @Resource
    StepConfigMapper stepConfigMapper;

    @Resource
    WorkflowConfigMapper workflowConfigMapper;

    @Resource
    AnodetectResultMapper anodetectResultMapper;

    @Resource
    UnitnodeTypeEnumMapper unitnodeTypeEnumMapper;

    @Resource
    OriginalDataService originalDataService;

    @Resource
    ExecDataTypeEnumMapper execDataTypeEnumMapper;

    @Resource
    WorkflowExecMapper workflowExecMapper;

    @Resource
    KnowledgegraphResultMapper knowledgegraphResultMapper;

    @Resource
    KnowledgeGraphService knowledgeGraphService;

    Utils utils = new Utils();

    // Yuran
    @Override
    public List<AnomalyInfoUserDTO> getAnomalyInfos(AnomalyInfoUserDTO info, int pageNum, int pageSize) {
        pageNum = pageNum > 1? pageNum : 1;
        pageSize = pageSize > 0? pageSize : 5;   // 默认5
        return anomalyInfoMapper.getAnomalyInfos((pageNum-1)*pageSize, pageSize, info);
    }

    @Override
    public boolean deleteByAnoId(int anoId) {
        return anomalyInfoMapper.deleteByAnoId(anoId) > 0;
    }

    @Override
    public AnomalyInfo updateStatusById(int anoId, int statusId) {
        // 更改状态并且返回新的故障信息
        if (statusId <= 0 || statusId > 5)
            return null;
        if (anomalyInfoMapper.updateStatusById(anoId,statusId) > 0) {
            return anomalyInfoMapper.getById(anoId);
        }
        return null;
    }

    @Override
    public boolean updateInfo(AnomalyInfo info) {
        return anomalyInfoMapper.updateAnoInfo(info) > 0;
    }

    // 根据故障检测(本次只涉及故障检测)的执行结果(此时workflowExec信息应该完成)来保存故障信息
    @Override
    public boolean saveAnoInfoByExec(WorkflowExec workflowExec) {
        if (workflowExec.getOutputTypeId() != 5) { // 本项目只涉及故障检测
            return false;
        }

        // 先准备该故障检测的基本信息
        QueryWrapper<AiopsObjEnum> wrapper_1 = new QueryWrapper<>();
        wrapper_1.eq("name", "log");
        int objId = aiopsObjEnumMapper.selectOne(wrapper_1).getObjId();

        QueryWrapper<AnomalyStatusEnum> wrapper_2 = new QueryWrapper<>();
        wrapper_2.eq("name", "未确认");
        int statusId = anomalyStatusEnumMapper.selectOne(wrapper_2).getStatusId();

        QueryWrapper<UnitnodeTypeEnum> wrapper_3 = new QueryWrapper<>();
        wrapper_3.eq("name", "Service");
        int unitnodeTypeId = unitnodeTypeEnumMapper.selectOne(wrapper_3).getTypeId();

        // 找到该执行对应的流程与步骤
        StepConfig stepConfig = stepConfigMapper.selectById(workflowExec.getStepId());
        WorkflowConfig workflowConfig = workflowConfigMapper.selectById(stepConfig.getWfId());

//        long adrId = Long.parseLong(workflowExec.getOutputId());
//        String[] temp = anodetectResultMapper.selectById(adrId).getSourceDataSections().split("\\|");
//        int anoNum = temp.length; // 该次执行发现了多少个故障

        // 找一下该执行对应的是哪一个批次的数据
        ExecStepDTO execStepDTO = workflowExecMapper.selectOneExecStep(workflowConfig.getWfId(), 1); // 找到数据源执行信息
        String[] inputInfo_temp = execStepDTO.getInputId().split("\\|");
        int batchId = Integer.parseInt(inputInfo_temp[0]); // 找到批次信息

        String[] anodetectOutputArray = workflowExec.getOutputId().split("\\|");
        if (anodetectOutputArray.length != 2) {
            return false;
        }
        long startId = Long.parseLong(anodetectOutputArray[0]);
        long endId = Long.parseLong(anodetectOutputArray[1]);
        QueryWrapper<AnodetectResult> wrapper_4 = new QueryWrapper<>();
        wrapper_4.between("adr_id", startId, endId);
        List<AnodetectResult> anodetectResultList = anodetectResultMapper.selectList(wrapper_4);

        int anoNum = anodetectResultList.size(); // 该次执行发现了多少个故障

        for (int i = 0; i < anoNum; i++) {

            AnomalyInfo anomalyInfo = new AnomalyInfo();

            // 先解析故障对应的源日志id
            String sourceDataId = anodetectResultList.get(i).getSourceDataSection();

            // 填写故障信息记录的基本信息
            anomalyInfo.setObjId(objId);
            anomalyInfo.setStatusId(statusId);
            anomalyInfo.setUnitnodeTypeId(unitnodeTypeId);
            anomalyInfo.setUnitnodeName("cart" + i);

            // 解析出故障指向的数据相对位次序号
            String[] dataSampleArray = sourceDataId.split("-");
            if (dataSampleArray.length != 2) {
                return false;
            }
            long startId_dataSample = Long.parseLong(dataSampleArray[0]);
            long endId_dataSample = Long.parseLong(dataSampleArray[1]);

            // sourceDataId要变一下, 加上批次信息
            anomalyInfo.setSourceDataId(batchId + "|" + startId_dataSample + "|" + endId_dataSample);

            String dataSample = genDataSample(batchId, startId_dataSample, endId_dataSample);
            anomalyInfo.setDataSample(dataSample);

            anomalyInfo.setDescription("批次为" + batchId + ", 相对位次序号为" + sourceDataId + "的数据出现故障");
            anomalyInfo.setUserId(workflowConfig.getUserId());
            anomalyInfo.setWfId(workflowConfig.getWfId());
            anomalyInfo.setDeleted(0); // 没被删除
//            Timestamp currentTimestamp = utils.getCurrentTstamp();
            Date currentDate = new Date(System.currentTimeMillis());
            anomalyInfo.setDetectTstamp(currentDate);
            anomalyInfo.setUpdateTstamp(currentDate);

            // 插入数据
//            System.out.println("Anomaly Info: ");
//            System.out.println(anomalyInfo);
            int insertResult = anomalyInfoMapper.insert(anomalyInfo);
            if (insertResult < 0) {
                return false;
            }
        }

        return true;
    }

    public String genDataSample(int batchId, long startId, long endId) {
//        System.out.println("genDataSample(" + startId + ", " + endId + ")");
        List<OriginalData> originalDataList = originalDataService.getRelativeRange(batchId, startId, endId);
        System.out.println(originalDataList.size());
        // 只取Sample
        if (originalDataList.size() > 5) {
            originalDataList = originalDataList.subList(0, 5);
        }
        // 封装List<String>
        List<String> originalLogStringList = new ArrayList<>();
        for (OriginalData originalData : originalDataList) {
            originalLogStringList.add(
                    "{\"OriginalDataId\": \"" + originalData.getCalcId()
                            + "\", \"BatchId\": \"" + originalData.getBatchId()
                            + "\", \"RelativeId\": \"" + originalData.getRelaId()
                            + "\", \"Content\": \"" + originalData.getContent()
                            + "\"}"
            );
        }
        System.out.println("originalLogStringList.size()" + originalLogStringList.size());
        System.out.println(originalLogStringList);
        return originalLogStringList.toString();
    }

    // 根据故障id获取对应的KG
    @Override
    public RootCauseKGDTO getKGByAno(Integer anoId) {

        // 准备查找
        AnomalyInfo anomalyInfo = anomalyInfoMapper.selectById(anoId);
        int wfId = anomalyInfo.getWfId();

        // 获取输出数据是KG的对应枚举
        QueryWrapper<ExecDataTypeEnum> wrapper_1 = new QueryWrapper<>();
        wrapper_1.eq("name", "知识图谱结果");
        int execTypeId = execDataTypeEnumMapper.selectOne(wrapper_1).getTypeId();

        // 重新封装source_data_id, 去除batch信息
        String[] sdi_ano = anomalyInfo.getSourceDataId().split("\\|");
        if (sdi_ano.length != 3) {
            return null;
        }
        String sourceDataSection = sdi_ano[1] + "-" + sdi_ano[2];
        System.out.println("sourceDataSection: " + sourceDataSection);

        // 查找knowledgegraph_result中的数据
        ExecStepDTO execStepDTO = workflowExecMapper.selectOneExecStepByOutType(wfId, execTypeId); // 先找到生成知识图谱的执行步骤
        if (execStepDTO == null) {
            return null;
        }
        System.out.println("execStepDTO: " + execStepDTO);
        String[] outputIdList = execStepDTO.getOutputId().split("\\|");
        if (outputIdList.length != 2) {
            return null;
        }
        long startId_kg = Long.parseLong(outputIdList[0]);
        long endId_kg = Long.parseLong(outputIdList[1]);

        // 包装查询条件: 应该只能查到一个!
        QueryWrapper<KnowledgegraphResult> wrapper_2 = new QueryWrapper<>();
        wrapper_2.between("kgr_id", startId_kg, endId_kg);
        wrapper_2.eq("source_data_section", sourceDataSection);

        // 查找到一条knowledgegraph_result数据
        KnowledgegraphResult knowledgegraphResult = knowledgegraphResultMapper.selectOne(wrapper_2);
        if (knowledgegraphResult == null) {
            return null;
        }
        System.out.println("knowledgegraphResult: ");
        System.out.println(knowledgegraphResult);

        // 获取相关数据, 先封装到RootCauseInfo中
        RootCauseInfo rootCauseInfo = new RootCauseInfo();
        String rcNodeNames = knowledgegraphResult.getRootcauseNodeNames();
        String rcRelaIds = knowledgegraphResult.getRootcauseRelationIds();
        rootCauseInfo.setRootCauseNodeNames(utils.String2List(rcNodeNames));
        rootCauseInfo.setRootCauseRelationIds(utils.String2List(rcRelaIds));

        // 再封装到RootCauseKGDTO中
        RootCauseKGDTO rootCauseKGDTO = new RootCauseKGDTO();
        rootCauseKGDTO.setRootCauseInfo(rootCauseInfo);

        String allNodeIds = knowledgegraphResult.getAllNodeIds();
        String[] allNodeIdStrArray = allNodeIds.split("\\|");
        List<Long> allNodeIdList = new ArrayList<>();
        for (String str : allNodeIdStrArray) {
            allNodeIdList.add(Long.parseLong(str));
        }
        List<Node> allNodeList = knowledgeGraphService.getNodeListByNodeIds(allNodeIdList);
        rootCauseKGDTO.setNodeList(allNodeList);

        String allRelationIds  = knowledgegraphResult.getAllRelationIds();
        String[] allRelationIdStrArray = allRelationIds.split("\\|");
        List<Long> allRelationIdList = new ArrayList<>();
        for (String str : allRelationIdStrArray) {
            allRelationIdList.add(Long.parseLong(str));
        }
        List<Neo4jRelationshipDto> allRelationList = knowledgeGraphService.getAllRelationshipByIds(allRelationIdList);
        rootCauseKGDTO.setRelationList(allRelationList);

        System.out.println("rootCauseKGDTO: ");
        System.out.println(rootCauseKGDTO);

        return rootCauseKGDTO;

    }

    // 检查该故障是否有知识图谱可以查看
    @Override
    public int checkAnoKG(Integer anoId) {
        AnomalyInfo anomalyInfo = anomalyInfoMapper.selectById(anoId);

        // 获取输出数据是KG的对应枚举
        QueryWrapper<ExecDataTypeEnum> wrapper_1 = new QueryWrapper<>();
        wrapper_1.eq("name", "知识图谱结果");
        int execTypeId = execDataTypeEnumMapper.selectOne(wrapper_1).getTypeId();

        ExecStepDTO execStepDTO = workflowExecMapper.selectOneExecStepByOutType(anomalyInfo.getWfId(), execTypeId);
        if (execStepDTO == null) {
            return 0;
        }
        return 1;
    }

    @Override
    public AnomalyInfoUserKGDTO getAnomalyInfoUserDTOById(Integer anoId) {
        AnomalyInfoUserDTO anomalyInfoUserDTO = anomalyInfoMapper.getAnomalyInfoUserDTOById(anoId);
        if (anomalyInfoUserDTO == null) {
            return null;
        }
        return new AnomalyInfoUserKGDTO(anomalyInfoUserDTO, checkAnoKG(anomalyInfoUserDTO.getAnoId()));
    }

    @Override
    public List<AnomalyInfoUserKGDTO> getAllAnomalyInfoUserDTO() {
        List<AnomalyInfoUserDTO> anomalyInfoUserDTOList = anomalyInfoMapper.getAllAnomalyInfoUserDTO();
        if (anomalyInfoUserDTOList.isEmpty()) {
            return null;
        }
        // 再次封装成AnomalyInfoUserKGDTO
        List<AnomalyInfoUserKGDTO> anomalyInfoUserKGDTOList = new ArrayList<>();
        for (AnomalyInfoUserDTO anomalyInfoUserDTO : anomalyInfoUserDTOList) {
            AnomalyInfoUserKGDTO anomalyInfoUserKGDTO = new AnomalyInfoUserKGDTO(anomalyInfoUserDTO, checkAnoKG(anomalyInfoUserDTO.getAnoId()));
            anomalyInfoUserKGDTOList.add(anomalyInfoUserKGDTO);
        }
        return anomalyInfoUserKGDTOList;
    }
}
