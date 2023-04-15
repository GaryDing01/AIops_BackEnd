package com.aiops_web.controller;


import com.aiops_web.entity.sql.AiopsAlg;
import com.aiops_web.std.ResponseStd;
import org.springframework.web.bind.annotation.*;
import com.aiops_web.service.AiopsAlgService;

import javax.annotation.Resource;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2023-04-12
 */
@RestController
@RequestMapping("/aiops-alg")
public class AiopsAlgController {

    @Resource
    AiopsAlgService aiopsAlgService;

    @GetMapping("/{algId}")
    public ResponseStd<AiopsAlg> getAlg(@PathVariable int algId) {
        AiopsAlg alg = aiopsAlgService.getAlgById(algId);
        return  new ResponseStd<>(alg);
    }

    @PutMapping
    public ResponseStd<Boolean> updateAlg(@RequestBody String param) {
        // 在 Service 中解析参数
        boolean res = aiopsAlgService.updateAlg(param);
        return  new ResponseStd<>(res);
    }

    @PostMapping
    public ResponseStd<Boolean> createAlg(@RequestBody String param) {
        // 在 Service 中解析参数
        boolean res = aiopsAlgService.createAlg(param);
        return  new ResponseStd<>(res);
    }


    @DeleteMapping("/{algId}")
    public ResponseStd<Boolean> deleteAlg(@PathVariable int algId) {
        boolean res = aiopsAlgService.deleteAlgById(algId);
        return  new ResponseStd<>(res);
    }



}

