package com.aiops_web.service;

import com.aiops_web.dto.AlgUserDTO;
import com.aiops_web.dto.DataIntroUserDTO;
import com.aiops_web.entity.sql.DataIntroducing;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author
 * @since 2023-04-12
 */
public interface DataIntroducingService extends IService<DataIntroducing> {
    // DTO相关

    // 新增
    DataIntroducing saveOne(DataIntroUserDTO dataIntroUserDTO);

    // 修改
    boolean updateOne(DataIntroUserDTO dataIntroUserDTO);

    // 根据batchId获取一个DataIntroUserDTO
    DataIntroUserDTO getDataIntroUserDTOById(Integer batchId);

    // 获取所有DataIntroUserDTO
    List<DataIntroUserDTO> getAllDataIntroUserDTO();
}
