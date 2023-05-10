package com.aiops_web.controller;

import com.aiops_web.dto.TemplateDTO;
import com.aiops_web.entity.sql.*;
import com.aiops_web.service.*;
import com.aiops_web.std.ErrorCode;
import com.aiops_web.std.ResponseStd;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController(value = "workflowController")
@RequestMapping("/workflow")
public class WorkflowController {

    @Resource
    WorkflowConfigService workflowConfigService;

    @Resource
    StepConfigService stepConfigService;

    @Resource
    WorkflowExecService workflowExecService;

    @Resource
    ReportService reportService;

    @Resource
    AnodetectResultService anodetectResultService;

    @Resource
    ExecDataTypeEnumService execDataTypeEnumService;

    @Resource
    KnowledgegraphResultService knowledgegraphResultService;

    @Resource
    CleanedDataService cleanedDataService;

    @Resource
    ParsedLogService parsedLogService;

    @Resource
    RootcauseResultService rootcauseResultService;

    @Resource
    StepTypeEnumService stepTypeEnumService;

    @Resource
    VectorizedLogService vectorizedLogService;

    // 新增流程
    @PostMapping("")
    public ResponseStd<Integer> createWorkflows(@RequestBody WorkflowConfig workflowConfig) {
        boolean saveResult = workflowConfigService.save(workflowConfig);
        if (!saveResult) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<Integer>(workflowConfig.getWfId());
    }

    // 新增流程-强调用户
    @PostMapping("/user/{userId}")
    public ResponseStd<Integer> createWorkflowsByUser(@PathVariable Integer userId, @RequestParam(required = false, defaultValue = "") String name) {
        return new ResponseStd<Integer>(workflowConfigService.saveWorkflowsByUser(userId, name));
    }

    // 删除某一个流程
    @DeleteMapping("/{wfId}")
    public ResponseStd<Boolean> removeWorkflows(@PathVariable Integer wfId) {
        return new ResponseStd<Boolean>(workflowConfigService.removeById(wfId));
    }

    // 修改某一个流程
    @PutMapping("")
    public ResponseStd<Boolean> updateWorkflows(@RequestBody WorkflowConfig workflowConfig) {
        return new ResponseStd<Boolean>(workflowConfigService.updateById(workflowConfig));
    }

    // 结束流程
    @PutMapping("/{wfId}/close")
    public ResponseStd<Boolean> closeWorkflow(@PathVariable Integer wfId) {
        return new ResponseStd<Boolean>(workflowExecService.closeWorkflow(wfId));
    }

    // 查找全部流程
    @GetMapping("")
    public ResponseStd<List<WorkflowConfig>> selectAllWorkflows() {
        List<WorkflowConfig> workflowConfigList = workflowConfigService.list();
        if (workflowConfigList.isEmpty()) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<List<WorkflowConfig>>(workflowConfigList);
    }

    // 根据id查找某一个流程
    @GetMapping("/{wfId}")
    public ResponseStd<WorkflowConfig> selectOneWorkflow(@PathVariable Integer wfId) {
        return new ResponseStd<WorkflowConfig>(workflowConfigService.getById(wfId));
    }

    // 查找已结束的流程信息
    @GetMapping("/end")
    public ResponseStd<List<WorkflowConfig>> selectEndedWorkflows() {
        return new ResponseStd<List<WorkflowConfig>>(workflowConfigService.getEndedWorkflows());
    }

    // 增加步骤
    // 经过尝试，可以全传空
    @PostMapping("/step")
    public ResponseStd<Integer> addSteps(@RequestBody StepConfig stepConfig) {
        return new ResponseStd<Integer>(stepConfigService.saveSteps(stepConfig));
    }

    // 根据id删除步骤
    @DeleteMapping("/step/{stepId}")
    public ResponseStd<Boolean> deleteSteps(@PathVariable Integer stepId) {
        return new ResponseStd<Boolean>(stepConfigService.removeById(stepId));
    }

    // 查看所有步骤
    @GetMapping("/step")
    public ResponseStd<List<StepConfig>> selectAllSteps() {
        return new ResponseStd<List<StepConfig>>(stepConfigService.list());
    }

    // 根据id查找某一个步骤
    @GetMapping("/step/{stepId}")
    public ResponseStd<StepConfig> selectOneStep(@PathVariable Integer stepId) {
        return new ResponseStd<StepConfig>(stepConfigService.getById(stepId));
    }

    // 批量id查询步骤
    @GetMapping("/step/batch")
    public ResponseStd<List<StepConfig>> selectBatchSteps(@RequestParam List<Integer> stepIdList) {
        return new ResponseStd<List<StepConfig>>(stepConfigService.listByIds(stepIdList));
    }

    // 根据流程id查询相应步骤
    @GetMapping("/{wfId}/step")
    public ResponseStd<List<StepConfig>> selectStepsByWf(@PathVariable Integer wfId) {
        return new ResponseStd<List<StepConfig>>(stepConfigService.getStepsByWf(wfId));
    }

    // 更改步骤
    // 可以空改数
    // 也可以数改空 (应该？之前记得测试过)
    @PutMapping("/step/{stepId}")
    public ResponseStd<Boolean> updateSteps(@RequestBody StepConfig stepConfig) {
        return new ResponseStd<Boolean>(stepConfigService.updateById(stepConfig));
    }

    // 将流程保存为模板(新增模板)
    @PostMapping("/{wfId}/template")
    public ResponseStd<Integer> addTemplates(@PathVariable Integer wfId) {
        return new ResponseStd<Integer>(workflowConfigService.saveTemplates(wfId));
    }

    // 查看所有模板
    @GetMapping("/template")
    public ResponseStd<List<TemplateDTO>> findAllTemplates() {
        return new ResponseStd<List<TemplateDTO>>(workflowConfigService.getAllTemplates());
    }

    // 根据id查看一个模板
    @GetMapping("/template/{wfId}")
    public ResponseStd<TemplateDTO> findOneTemplate(@PathVariable Integer wfId) {
        return new ResponseStd<TemplateDTO>(workflowConfigService.getOneTemplate(wfId));
    }

    // 根据模板创建流程
    @PostMapping("/template/{wfId}")
    public ResponseStd<Integer> addWFByT(@PathVariable Integer wfId, @RequestBody JSONObject jsonObject) {
        Integer userId = (Integer) jsonObject.get("userId");
        if (userId == null) {
            return new ResponseStd<>(ErrorCode.PARAMS_ERROR, null);
        }
        return new ResponseStd<Integer>(workflowConfigService.saveWfByT(wfId, userId));
    }

    // 更新模板
    /**
     *
     * @param wfId 要覆盖的模板编号
     * @param templateDTO 原始模板
     * @return
     */
    @PutMapping("/template/{wfId}")
    public ResponseStd<Boolean> updateOneTemplate(@PathVariable Integer wfId, @RequestBody TemplateDTO templateDTO) {
        // 取原模板编号
        int wfId_origin = templateDTO.getWfId();
        // TemplateDTO templateDTO_origin = workflowConfigService.getOneTemplate(wfId_origin);

        // 改模板(改流程 + 删步骤 + 加步骤)
        // 根据模板改流程
        boolean updateWf = workflowConfigService.updateWfByT(wfId, templateDTO);
        if (!updateWf) return new ResponseStd<Boolean>(false);

        // 删步骤
        boolean removeSteps = workflowConfigService.removeStepsByT(wfId);
        if (!removeSteps) return new ResponseStd<Boolean>(false);

        // 加步骤
        boolean saveSteps =workflowConfigService.saveStepsByT(wfId, templateDTO);
        if (!saveSteps) return new ResponseStd<Boolean>(false);

        return new ResponseStd<Boolean>(true);
    }

    // 开始执行!!!

    // 简单的新增步骤, 如果是单步执行尽量调用下方的单步执行步骤Api
    @PostMapping("/exec")
    public ResponseStd<Integer> createOneExec(@RequestBody WorkflowExec workflowExec) {
        boolean saveResult = workflowExecService.save(workflowExec);
        if(!saveResult) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<Integer>(workflowExec.getExecId());
    }

    // 单步执行步骤
//    @PostMapping("/exec/step/{stepId}")
//    public ResponseStd<Integer> createOneExecByStep(@PathVariable Integer stepId, @RequestParam(required = false) Integer inputTypeId, @RequestParam(required = false) String inputId) {
//        return new ResponseStd<Integer>(workflowExecService.saveOneExecByStep(stepId, inputTypeId, inputId));
//    }

    // 单步执行封装JSONObject
    @PostMapping("/exec/step/{stepId}")
    public ResponseStd<Integer> createOneExecByStep(@PathVariable Integer stepId, @RequestBody(required = false) JSONObject jsonObject) {
        Integer inputTypeId;
        String inputId;
        if (jsonObject == null) {
            inputTypeId = null;
            inputId = "";
        }
        else {
            inputTypeId = (Integer) jsonObject.get("inputTypeId");
            inputId = jsonObject.getString("inputId");
        }
        System.out.println("测试: " + "inputTypeID: " +  inputTypeId + ", inputId: " + inputId);
        int oneExecId = workflowExecService.saveOneExecByStep(stepId, inputTypeId, inputId);
        if (oneExecId < 1) {
            return new ResponseStd<>(ErrorCode.PARAMS_ERROR, null);
        }
        return new ResponseStd<Integer>(oneExecId);
    }

    // 传入流程编号，一次性执行流程中的所有步骤
    @PostMapping("/{wfId}/exec")
    public ResponseStd<List<Integer>> createAllExecByWf(@PathVariable Integer wfId) {
        // 匹配step和wfId
        QueryWrapper<StepConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("wf_id",wfId);
        List<StepConfig> stepConfigList = stepConfigService.list(wrapper);
        List<Integer> wfExecIdList = new ArrayList<>();
        for (StepConfig stepConfig : stepConfigList) {
            int execResult = workflowExecService.saveOneExecByStep(stepConfig.getStepId(), null, null);
            if (execResult < 1) {
                return new ResponseStd<>(ErrorCode.PARAMS_ERROR, null);
            }
            wfExecIdList.add(execResult);
        }
        return new ResponseStd<List<Integer>>(wfExecIdList);
    }

    // 步骤回退
    @DeleteMapping("/exec/{execId}")
    public ResponseStd<Boolean> deleteOneExec(@PathVariable Integer execId) {
        return new ResponseStd<Boolean>(workflowExecService.withdrawExec(execId));
    }

    // 简单的修改步骤
    @PutMapping("/exec")
    public ResponseStd<Boolean> updateOneExec(@RequestBody WorkflowExec workflowExec) {
        return new ResponseStd<Boolean>(workflowExecService.updateById(workflowExec));
    }

    // 获取单步执行信息
    @GetMapping("/exec/{execId}")
    public ResponseStd<WorkflowExec> selectOneExec(@PathVariable Integer execId) {
        WorkflowExec workflowExec = workflowExecService.getById(execId);
        if (workflowExec == null) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        else {
            return new ResponseStd<WorkflowExec>(workflowExec);
        }
    }

    // 根据流程id获取流程对应的执行信息
    @GetMapping("/{wfId}/exec")
    public ResponseStd<List<WorkflowExec>> selectExecsByWf(@PathVariable Integer wfId) {
        List<WorkflowExec> workflowExecList = workflowExecService.getExecsByWf(wfId);
        if (workflowExecList == null) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        else {
            return new ResponseStd<List<WorkflowExec>>(workflowExecList);
        }
    }

    // 获取全部执行信息
    @GetMapping("/exec")
    public ResponseStd<List<WorkflowExec>> findAllExecs() {
        List<WorkflowExec> workflowExecList = workflowExecService.list();
        if (workflowExecList == null) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        else {
            return new ResponseStd<List<WorkflowExec>>(workflowExecList);
        }
    }

    // 根据步骤id（step_id ）获取到对应的执行的outputids
    @GetMapping("/step/{stepId}/outputId")
    public ResponseStd<String> selectOutputId(@PathVariable Integer stepId) {
        String outputId = workflowExecService.getOutputId(stepId);
        if (Objects.equals(outputId, "")) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, "");
        }
        else {
            return new ResponseStd<String>(outputId);
        }
    }

    // 检查
    // 获取报告
    @GetMapping("/report/check")
    public ResponseStd<Boolean> checkOneReport(@RequestBody WorkflowExec workflowExec) {
        boolean result = reportService.saveOneReportByExec(workflowExec);
        if (!result) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, result);
        }
        else {
            return new ResponseStd<Boolean>(result);
        }
    }

    // 查看一个流程的所有报告
    @GetMapping("/{wfId}/report")
    public ResponseStd<List<Report>> selectReportByWf(@PathVariable Integer wfId) {
        List<Report> reportList = reportService.list();
        if (reportList.isEmpty()) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<List<Report>>(reportList);
    }

    // 其他相关
    // 后端生成知识图谱需要(自查)
    @GetMapping("/KG/check")
    public ResponseStd<List<String>> selectAllRCIds() {
        List<String> allRCIds = workflowExecService.getAllRCIds();
        if (allRCIds.isEmpty()) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<List<String>>(allRCIds);
    }

    // 其他表基本增删改查

    // anodetect_result表
    // 增加一个故障检测结果
    @PostMapping("/adr")
    public ResponseStd<Long> createAdr(@RequestBody AnodetectResult anodetectResult) {
        boolean saveResult = anodetectResultService.save(anodetectResult);
        if (!saveResult) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<Long>(anodetectResult.getAdrId());
    }

    // 根据id删除一个故障检测结果
    @DeleteMapping("/adr/{adrId}")
    public ResponseStd<Boolean> deleteAdr(@PathVariable Integer adrId) {
        return new ResponseStd<Boolean>(anodetectResultService.removeById(adrId));
    }

    // 修改一个故障检测结果
    @PutMapping("/adr")
    public ResponseStd<Boolean> updateAdr(@RequestBody AnodetectResult anodetectResult) {
        return new ResponseStd<Boolean>(anodetectResultService.updateById(anodetectResult));
    }

    // 查找全部故障检测结果
    @GetMapping("/adr")
    public ResponseStd<List<AnodetectResult>> selectAllAdr() {
        List<AnodetectResult> anodetectResultList = anodetectResultService.list();
        if (anodetectResultList.isEmpty()) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<List<AnodetectResult>>(anodetectResultList);
    }

    // 根据id查找某一个故障检测结果
    @GetMapping("/adr/{adrId}")
    public ResponseStd<AnodetectResult> selectAdrById(@PathVariable Integer adrId) {
        return new ResponseStd<AnodetectResult>(anodetectResultService.getById(adrId));
    }

    // cleaned_data表
    // 增加一个日志清洗结果
    @PostMapping("/cleaned")
    public ResponseStd<Long> createCleanedData(@RequestBody CleanedData cleanedData) {
        boolean saveResult = cleanedDataService.save(cleanedData);
        if (!saveResult) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<Long>(cleanedData.getCleanId());
    }

    // 根据id删除一个日志清洗结果
    @DeleteMapping("/cleaned/{cleanId}")
    public ResponseStd<Boolean> deleteCleanedData(@PathVariable Integer cleanId) {
        return new ResponseStd<Boolean>(cleanedDataService.removeById(cleanId));
    }

    // 修改一个日志清洗结果
    @PutMapping("/cleaned")
    public ResponseStd<Boolean> updateAdr(@RequestBody CleanedData cleanedData) {
        return new ResponseStd<Boolean>(cleanedDataService.updateById(cleanedData));
    }

    // 查找全部日志清洗结果
    @GetMapping("/cleaned")
    public ResponseStd<List<CleanedData>> selectAllCleanedData() {
        List<CleanedData> cleanedDataList = cleanedDataService.list();
        if (cleanedDataList.isEmpty()) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<List<CleanedData>>(cleanedDataList);
//        return new ResponseStd<List<CleanedData>>(cleanedDataService.list());
    }

    // 根据id查找某一个算法类型
    @GetMapping("/cleaned/{cleanId}")
    public ResponseStd<CleanedData> selectCleanedDataById(@PathVariable Integer cleanId) {
        return new ResponseStd<CleanedData>(cleanedDataService.getById(cleanId));
    }

    // exec_data_type_enum表
    // 增加一个执行数据类型
    @PostMapping("/execDataTypes")
    public ResponseStd<Integer> createExecDataType(@RequestBody ExecDataTypeEnum execDataTypeEnum) {
        boolean saveResult = execDataTypeEnumService.save(execDataTypeEnum);
        if (!saveResult) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<Integer>(execDataTypeEnum.getTypeId());
    }

    // 根据id删除一个执行数据类型
    @DeleteMapping("/execDataTypes/{typeId}")
    public ResponseStd<Boolean> deleteExecDataType(@PathVariable Integer typeId) {
        return new ResponseStd<Boolean>(execDataTypeEnumService.removeById(typeId));
    }

    // 修改一个执行数据类型
    @PutMapping("/execDataTypes")
    public ResponseStd<Boolean> updateExecDataType(@RequestBody ExecDataTypeEnum execDataTypeEnum) {
        return new ResponseStd<Boolean>(execDataTypeEnumService.updateById(execDataTypeEnum));
    }

    // 查找全部执行数据类型
    @GetMapping("/execDataTypes")
    public ResponseStd<List<ExecDataTypeEnum>> selectAllExecDataTypes() {
        List<ExecDataTypeEnum> execDataTypeEnumList = execDataTypeEnumService.list();
        if (execDataTypeEnumList.isEmpty()) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<List<ExecDataTypeEnum>>(execDataTypeEnumList);
    }

    // 根据id查找某一个执行数据类型
    @GetMapping("/execDataTypes/{typeId}")
    public ResponseStd<ExecDataTypeEnum> selectExecDataTypeById(@PathVariable Integer typeId) {
        return new ResponseStd<ExecDataTypeEnum>(execDataTypeEnumService.getById(typeId));
    }

    // knowledgegraph_result表
    // 增加一个知识图谱生成结果
    @PostMapping("/kgr")
    public ResponseStd<Integer> createKgr(@RequestBody KnowledgegraphResult knowledgegraphResult) {
        boolean saveResult = knowledgegraphResultService.save(knowledgegraphResult);
        if (!saveResult) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<Integer>(knowledgegraphResult.getKgrId());
    }

    // 根据id删除一个知识图谱生成结果
    @DeleteMapping("/kgr/{kgrId}")
    public ResponseStd<Boolean> deleteKgr(@PathVariable Integer kgrId) {
        return new ResponseStd<Boolean>(knowledgegraphResultService.removeById(kgrId));
    }

    // 修改一个知识图谱生成结果
    @PutMapping("/kgr")
    public ResponseStd<Boolean> updateKgr(@RequestBody KnowledgegraphResult knowledgegraphResult) {
        return new ResponseStd<Boolean>(knowledgegraphResultService.updateById(knowledgegraphResult));
    }

    // 查找全部知识图谱生成结果
    @GetMapping("/kgr")
    public ResponseStd<List<KnowledgegraphResult>> selectAllKgr() {
        List<KnowledgegraphResult> knowledgegraphResultList = knowledgegraphResultService.list();
        if (knowledgegraphResultList.isEmpty()) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<List<KnowledgegraphResult>>(knowledgegraphResultList);
    }

    // 根据id查找某一个故障检测结果
    @GetMapping("/kgr/{kgrId}")
    public ResponseStd<KnowledgegraphResult> selectKgrById(@PathVariable Integer kgrId) {
        return new ResponseStd<KnowledgegraphResult>(knowledgegraphResultService.getById(kgrId));
    }

    // parsed_log表
    // 增加一个日志解析结果
    @PostMapping("/parsed")
    public ResponseStd<Long> createParsedLog(@RequestBody ParsedLog parsedLog) {
        boolean saveResult = parsedLogService.save(parsedLog);
        if (!saveResult) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<Long>(parsedLog.getParseId());
    }

    // 根据id删除一个日志解析结果
    @DeleteMapping("/parsed/{parseId}")
    public ResponseStd<Boolean> deleteParsedLog(@PathVariable Integer parseId) {
        return new ResponseStd<Boolean>(parsedLogService.removeById(parseId));
    }

    // 修改一个日志解析结果
    @PutMapping("/parsed")
    public ResponseStd<Boolean> updateParsedLog(@RequestBody ParsedLog parsedLog) {
        return new ResponseStd<Boolean>(parsedLogService.updateById(parsedLog));
    }

    // 查找全部日志解析结果
    @GetMapping("/parsed")
    public ResponseStd<List<ParsedLog>> selectAllParsedLog() {
        List<ParsedLog> parsedLogList = parsedLogService.list();
        if (parsedLogList.isEmpty()) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<List<ParsedLog>>(parsedLogList);
    }

    // 根据id查找某一个日志解析结果
    @GetMapping("/parsed/{parseId}")
    public ResponseStd<ParsedLog> selectParsedLogById(@PathVariable Integer parseId) {
        return new ResponseStd<ParsedLog>(parsedLogService.getById(parseId));
    }

    // report表
    // 增加一个报告
    @PostMapping("/report")
    public ResponseStd<Integer> createReport(@RequestBody Report report) {
        boolean saveResult = reportService.save(report);
        if (!saveResult) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<Integer>(report.getReportId());
    }

    // 根据id删除一个报告
    @DeleteMapping("/report/{reportId}")
    public ResponseStd<Boolean> deleteReport(@PathVariable Integer reportId) {
        return new ResponseStd<Boolean>(reportService.removeById(reportId));
    }

    // 修改一个报告
    @PutMapping("/report")
    public ResponseStd<Boolean> updateReport(@RequestBody Report report) {
        return new ResponseStd<Boolean>(reportService.updateById(report));
    }

    // 查找全部报告
    @GetMapping("/report")
    public ResponseStd<List<Report>> selectAllReports() {
        List<Report> reportList = reportService.list();
        if (reportList.isEmpty()) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<List<Report>>(reportList);
    }

    // 根据id查找某一个报告
    @GetMapping("/report/{reportId}")
    public ResponseStd<Report> selectReportById(@PathVariable Integer reportId) {
        return new ResponseStd<Report>(reportService.getById(reportId));
    }

    // rootcause_result表
    // 增加一个根因分析结果
    @PostMapping("/rcr")
    public ResponseStd<Integer> createRcr(@RequestBody RootcauseResult rootcauseResult) {
        boolean saveResult = rootcauseResultService.save(rootcauseResult);
        if (!saveResult) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<Integer>(rootcauseResult.getRcrId());
    }

    // 根据id删除一个根因分析结果
    @DeleteMapping("/rcr/{rcrId}")
    public ResponseStd<Boolean> deleteRcr(@PathVariable Integer rcrId) {
        return new ResponseStd<Boolean>(rootcauseResultService.removeById(rcrId));
    }

    // 修改一个根因分析结果
    @PutMapping("/rcr")
    public ResponseStd<Boolean> updateRcr(@RequestBody RootcauseResult rootcauseResult) {
        return new ResponseStd<Boolean>(rootcauseResultService.updateById(rootcauseResult));
    }

    // 查找全部根因分析结果
    @GetMapping("/rcr")
    public ResponseStd<List<RootcauseResult>> selectAllRcr() {
        List<RootcauseResult> rootcauseResultList = rootcauseResultService.list();
        if (rootcauseResultList.isEmpty()) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<List<RootcauseResult>>(rootcauseResultList);
    }

    // 根据id查找某一个根因分析结果
    @GetMapping("/rcr/{rcrId}")
    public ResponseStd<RootcauseResult> selectRcrById(@PathVariable Integer rcrId) {
        return new ResponseStd<RootcauseResult>(rootcauseResultService.getById(rcrId));
    }

    // step_type_enum表
    // 增加一个步骤类型
    @PostMapping("/stepTypes")
    public ResponseStd<Integer> createStepType(@RequestBody StepTypeEnum stepTypeEnum) {
        boolean saveResult = stepTypeEnumService.save(stepTypeEnum);
        if (!saveResult) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<Integer>(stepTypeEnum.getTypeId());
    }

    // 根据id删除一个步骤类型
    @DeleteMapping("/stepTypes/{typeId}")
    public ResponseStd<Boolean> deleteStepType(@PathVariable Integer typeId) {
        return new ResponseStd<Boolean>(stepTypeEnumService.removeById(typeId));
    }

    // 修改一个步骤类型
    @PutMapping("/stepTypes")
    public ResponseStd<Boolean> updateStepType(@RequestBody StepTypeEnum stepTypeEnum) {
        return new ResponseStd<Boolean>(stepTypeEnumService.updateById(stepTypeEnum));
    }

    // 查找全部执行数据类型
    @GetMapping("/stepTypes")
    public ResponseStd<List<StepTypeEnum>> selectAllStepTypes() {
        List<StepTypeEnum> stepTypeEnumList = stepTypeEnumService.list();
        if (stepTypeEnumList.isEmpty()) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<List<StepTypeEnum>>(stepTypeEnumList);
    }

    // 根据id查找某一个执行数据类型
    @GetMapping("/stepTypes/{typeId}")
    public ResponseStd<StepTypeEnum> selectStepTypeById(@PathVariable Integer typeId) {
        return new ResponseStd<StepTypeEnum>(stepTypeEnumService.getById(typeId));
    }

    // vectorized_log表
    // 增加一个日志向量化结果
    @PostMapping("/vectorized")
    public ResponseStd<Long> createVectorizedLog(@RequestBody VectorizedLog vectorizedLog) {
        boolean saveResult = vectorizedLogService.save(vectorizedLog);
        if (!saveResult) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<Long>(vectorizedLog.getVectorId());
    }

    // 根据id删除一个日志向量化结果
    @DeleteMapping("/vectorized/{vectorId}")
    public ResponseStd<Boolean> deleteVectorizedLog(@PathVariable Integer vectorId) {
        return new ResponseStd<Boolean>(vectorizedLogService.removeById(vectorId));
    }

    // 修改一个日志向量化结果
    @PutMapping("/vectorized")
    public ResponseStd<Boolean> updateVectorizedLog(@RequestBody VectorizedLog vectorizedLog) {
        return new ResponseStd<Boolean>(vectorizedLogService.updateById(vectorizedLog));
    }

    // 查找全部日志向量化结果
    @GetMapping("/vectorized")
    public ResponseStd<List<VectorizedLog>> selectAllVectorizedLog() {
        List<VectorizedLog> vectorizedLogList = vectorizedLogService.list();
        if (vectorizedLogList.isEmpty()) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<List<VectorizedLog>>(vectorizedLogList);
    }

    // 根据id查找某一个日志向量化结果
    @GetMapping("/vectorized/{vectorId}")
    public ResponseStd<VectorizedLog> selectVectorizedLogById(@PathVariable Integer vectorId) {
        return new ResponseStd<VectorizedLog>(vectorizedLogService.getById(vectorId));
    }

}
