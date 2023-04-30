package com.aiops_web.controller;


import com.aiops_web.entity.sql.AiopsAlg;
import com.aiops_web.std.ErrorCode;
import com.aiops_web.std.ResponseStd;
import com.baomidou.mybatisplus.annotation.IdType;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import com.aiops_web.service.AiopsAlgService;
import scala.Int;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author
 * @since 2023-04-12
 */
@RestController
@RequestMapping("/algorithm")
public class AiopsAlgController {

    @Resource
    AiopsAlgService aiopsAlgService;

    @GetMapping()
    public ResponseStd<List<AiopsAlg>> getAlg() {
        List<AiopsAlg> alg = aiopsAlgService.getAllAlgs();
        // 没有算法
        if (alg.isEmpty()) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return  new ResponseStd<>(alg);
    }

    @PutMapping
    public ResponseStd<Boolean> updateAlg(@RequestBody String param) throws JsonProcessingException {
        // 在 Service 中解析参数
        boolean res = aiopsAlgService.updateAlg(param);
        return  new ResponseStd<>(res);
    }

    @PostMapping
    public ResponseStd<Boolean> createAlg(@RequestBody String param) throws JsonProcessingException {
        // 在 Service 中解析参数
        boolean res = aiopsAlgService.createAlg(param);
        return  new ResponseStd<>(res);
    }

    @DeleteMapping("/{algId}")
    public ResponseStd<Boolean> deleteAlg(@PathVariable int algId) {
        boolean res = aiopsAlgService.deleteAlgById(algId);
        return  new ResponseStd<>(res);
    }

    @DeleteMapping()
    public ResponseStd<Integer> deleteAlgByIds(@RequestParam List<Integer> ids) {
        // test ids
        System.out.println();
        if (ids.isEmpty()) {
            return new ResponseStd<>(ErrorCode.PARAMS_ERROR);
        }

        // return number of deleted tuples
        int tupleNum = aiopsAlgService.deleteAlgByIds(ids);
        return new ResponseStd<>(tupleNum);
    }

}

