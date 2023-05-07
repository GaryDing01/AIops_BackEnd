package com.aiops_web.controller;


<<<<<<< HEAD
import com.aiops_web.entity.sql.AiopsAlg;
import com.aiops_web.std.ErrorCode;
import com.aiops_web.std.ResponseStd;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import com.aiops_web.service.AiopsAlgService;
=======
import com.aiops_web.entity.sql.*;
import com.aiops_web.service.AlgTypeEnumService;
import com.aiops_web.std.ErrorCode;
import com.aiops_web.std.ResponseStd;
import com.baomidou.mybatisplus.annotation.IdType;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import com.aiops_web.service.AiopsAlgService;
import scala.Int;
>>>>>>> temp

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

<<<<<<< HEAD
=======
    @Resource
    AlgTypeEnumService algTypeEnumService;

>>>>>>> temp
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
    public ResponseStd<Boolean> updateAlg(@RequestBody AiopsAlg alg) throws JsonProcessingException {
        // 在 Service 中解析参数
        boolean res = aiopsAlgService.updateAlg(alg);
        return  new ResponseStd<>(res);
    }

    @PostMapping
<<<<<<< HEAD
    public ResponseStd<Boolean> createAlg(@RequestBody AiopsAlg alg) throws JsonProcessingException {
        // 在 Service 中解析参数
        boolean res = aiopsAlgService.createAlg(alg);
        return  new ResponseStd<>(res);
=======
    public ResponseStd<Integer> createAlg(@RequestBody AiopsAlg alg) throws JsonProcessingException {
        // 在 Service 中解析参数
//        boolean res = aiopsAlgService.createAlg(alg);
        int saveResult = aiopsAlgService.createAlg_new(alg);
        if (saveResult == 0) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<Integer>(saveResult);
>>>>>>> temp
    }

    @DeleteMapping("/{algId}")
    public ResponseStd<Boolean> deleteAlg(@PathVariable int algId) {
        boolean res = aiopsAlgService.deleteAlgById(algId);
        return  new ResponseStd<>(res);
    }

    @DeleteMapping()
<<<<<<< HEAD
    public ResponseStd<Integer> deleteAlgByIds(@RequestParam List<Integer> ids) {
=======
    public ResponseStd<Boolean> deleteAlgByIds(@RequestParam List<Integer> ids) {
>>>>>>> temp
        // test ids
        System.out.println();
        if (ids.isEmpty()) {
            return new ResponseStd<>(ErrorCode.PARAMS_ERROR);
        }

        // return number of deleted tuples
        int tupleNum = aiopsAlgService.deleteAlgByIds(ids);
<<<<<<< HEAD
        return new ResponseStd<>(tupleNum);
=======
        if (tupleNum < 1) {
            return new ResponseStd<Boolean>(false);
        }

        return new ResponseStd<Boolean>(true);
    }

    // 补
    // 根据id查找某一个算法
    @GetMapping("/{algId}")
    public ResponseStd<AiopsAlg> selectAlgById(@PathVariable Integer algId) {
        return new ResponseStd<AiopsAlg>(aiopsAlgService.getById(algId));
    }

    // 其他表基本增删改查

    // alg_type_enum表
    // 增加一个算法类型
    @PostMapping("/types")
    public ResponseStd<Integer> createAlgType(@RequestBody AlgTypeEnum algTypeEnum) {
        boolean saveResult = algTypeEnumService.save(algTypeEnum);
        if (!saveResult) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<Integer>(algTypeEnum.getTypeId());
    }

    // 根据id删除一个算法类型
    @DeleteMapping("/types/{typeId}")
    public ResponseStd<Boolean> deleteAlgType(@PathVariable Integer typeId) {
        return new ResponseStd<Boolean>(algTypeEnumService.removeById(typeId));
    }

    // 修改一个算法类型
    @PutMapping("/types")
    public ResponseStd<Boolean> updateAlgType(@RequestBody AlgTypeEnum algTypeEnum) {
        return new ResponseStd<Boolean>(algTypeEnumService.updateById(algTypeEnum));
    }

    // 查找全部算法类型
    @GetMapping("/types")
    public ResponseStd<List<AlgTypeEnum>> selectAllAlgTypes() {
        List<AlgTypeEnum> algTypeEnumList = algTypeEnumService.list();
        if (algTypeEnumList.isEmpty()) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<List<AlgTypeEnum>>(algTypeEnumList);
    }

    // 根据id查找某一个算法类型
    @GetMapping("/types/{typeId}")
    public ResponseStd<AlgTypeEnum> selectAlgTypeById(@PathVariable Integer typeId) {
        AlgTypeEnum algTypeEnum = algTypeEnumService.getById(typeId);
        if (algTypeEnum == null) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<AlgTypeEnum>(algTypeEnum);
>>>>>>> temp
    }

}

