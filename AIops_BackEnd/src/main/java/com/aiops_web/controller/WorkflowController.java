package com.aiops_web.controller;

import com.aiops_web.dto.TemplateDTO;
import com.aiops_web.entity.sql.StepConfig;
import com.aiops_web.entity.sql.WorkflowConfig;
import com.aiops_web.service.StepConfigService;
import com.aiops_web.service.WorkflowConfigService;
import com.aiops_web.std.ResponseStd;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController(value = "workflowController")
@RequestMapping("/workflow")
public class WorkflowController {

    @Resource
    WorkflowConfigService workflowConfigService;

    @Resource
    StepConfigService stepConfigService;

    // 新增流程
    @PostMapping("/{userId}")
    public ResponseStd<Integer> addWorkflows(@PathVariable Integer userId, @RequestParam(value = "name", defaultValue = "") String name) {
        System.out.println("userId: " + userId);
        System.out.println("name: " + name);
//        return new ResponseStd<Integer>(1);
        return new ResponseStd<Integer>(workflowConfigService.saveWorkflows(userId, name));
    }

    // 查找全部流程
    @GetMapping("")
    public ResponseStd<List<WorkflowConfig>> selectAllWorkflows() {
        return new ResponseStd<List<WorkflowConfig>>(workflowConfigService.list());
    }

    // 根据id查找某一个流程
    @GetMapping("/{wfId}")
    public ResponseStd<WorkflowConfig> selectOneWorkflow(@PathVariable Integer wfId) {
        return new ResponseStd<WorkflowConfig>(workflowConfigService.getById(wfId));
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

    // 更改步骤
    // 可以空改数
    // 不能数改空
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
    public ResponseStd<Integer> addWFByT(@PathVariable Integer wfId) {
        return new ResponseStd<Integer>(workflowConfigService.saveWfByT(wfId));
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

}
