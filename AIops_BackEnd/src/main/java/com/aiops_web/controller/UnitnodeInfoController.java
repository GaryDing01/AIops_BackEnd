package com.aiops_web.controller;


import com.aiops_web.entity.mysql.UnitnodeInfo;
import com.aiops_web.entity.mysql.UnitnodeTypeEnum;
import com.aiops_web.service.mysql.UnitnodeInfoService;
import com.aiops_web.service.mysql.UnitnodeTypeEnumService;
import com.aiops_web.std.ErrorCode;
import com.aiops_web.std.ResponseStd;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/unitnode")
public class UnitnodeInfoController {

    @Resource
    UnitnodeInfoService unitnodeInfoService;

    @Resource
    UnitnodeTypeEnumService unitnodeTypeEnumService;

    // 其他表基本增删改查

    // unitnode_info表
    // 增加一个系统节点信息
    @PostMapping("")
    public ResponseStd<Integer> createUNInfo(@RequestBody UnitnodeInfo unitnodeInfo) {
        boolean saveResult = unitnodeInfoService.save(unitnodeInfo);
        if (!saveResult) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<Integer>(unitnodeInfo.getUnitId());
    }

    // 根据id删除一个系统节点信息
    @DeleteMapping("/{unitId}")
    public ResponseStd<Boolean> deleteUNInfo(@PathVariable Integer unitId) {
        return new ResponseStd<Boolean>(unitnodeInfoService.removeById(unitId));
    }

    // 修改一个系统节点信息
    @PutMapping("")
    public ResponseStd<Boolean> updateUNInfo(@RequestBody UnitnodeInfo unitnodeInfo) {
        return new ResponseStd<Boolean>(unitnodeInfoService.updateById(unitnodeInfo));
    }

    // 查找全部系统节点信息
    @GetMapping("")
    public ResponseStd<List<UnitnodeInfo>> selectUNInfo() {
        List<UnitnodeInfo> unitnodeInfoList = unitnodeInfoService.list();
        if (unitnodeInfoList.isEmpty()) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<List<UnitnodeInfo>>(unitnodeInfoList);
    }

    // 根据id查找某一个系统节点信息
    @GetMapping("/{unitId}")
    public ResponseStd<UnitnodeInfo> selectUNInfoById(@PathVariable Integer unitId) {
        return new ResponseStd<UnitnodeInfo>(unitnodeInfoService.getById(unitId));
    }

    // unitnode_type_enum表
    // 增加一个系统节点类型
    @PostMapping("/types")
    public ResponseStd<Integer> createUNType(@RequestBody UnitnodeTypeEnum unitnodeTypeEnum) {
        boolean saveResult = unitnodeTypeEnumService.save(unitnodeTypeEnum);
        if (!saveResult) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<Integer>(unitnodeTypeEnum.getTypeId());
    }

    // 根据id删除一个系统节点类型
    @DeleteMapping("/types/{typeId}")
    public ResponseStd<Boolean> deleteUNType(@PathVariable Integer typeId) {
        return new ResponseStd<Boolean>(unitnodeTypeEnumService.removeById(typeId));
    }

    // 修改一个系统节点类型
    @PutMapping("/types")
    public ResponseStd<Boolean> updateUNType(@RequestBody UnitnodeTypeEnum unitnodeTypeEnum) {
        return new ResponseStd<Boolean>(unitnodeTypeEnumService.updateById(unitnodeTypeEnum));
    }

    // 查找全部系统节点类型
    @GetMapping("/types")
    public ResponseStd<List<UnitnodeTypeEnum>> selectUNTypes() {
        List<UnitnodeTypeEnum> unitnodeTypeEnumList = unitnodeTypeEnumService.list();
        if (unitnodeTypeEnumList.isEmpty()) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<List<UnitnodeTypeEnum>>(unitnodeTypeEnumList);
    }

    // 根据id查找某一个系统节点类型
    @GetMapping("/types/{typeId}")
    public ResponseStd<UnitnodeTypeEnum> selectUNTypeById(@PathVariable Integer typeId) {
        return new ResponseStd<UnitnodeTypeEnum>(unitnodeTypeEnumService.getById(typeId));
    }
}

