package com.aiops_web.controller;

import com.aiops_web.service.WorkflowConfigService;
import com.aiops_web.std.ResponseStd;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController(value = "workflowController")
@RequestMapping("/workflow")
public class WorkflowController {

    @Resource
    WorkflowConfigService workflowConfigService;

    @PostMapping("/{userId}")
    public ResponseStd<Integer> addWorkflows(@PathVariable Integer userId, @RequestParam("name") String name) {
        System.out.println("userId: " + userId);
        System.out.println("name: " + name);
//        return new ResponseStd<Integer>(1);
        return new ResponseStd<Integer>(workflowConfigService.saveWorkflows(userId, name));
    }

    @PostMapping("/{wfId}/step/{typeId}/alg/{algId}")
    public ResponseStd<Integer> addSteps(@PathVariable Integer wfId, @PathVariable Integer typeId, @PathVariable Integer algId, @RequestBody List<Map<String, Object>> param) {
        System.out.println("wfId: " + wfId);
        System.out.println("typeId: " + typeId);
        System.out.println("algId: " + algId);
        for (Map<String, Object> item : param) {
            System.out.println(item);
        }
        return new ResponseStd<Integer>(1);
    }
}
