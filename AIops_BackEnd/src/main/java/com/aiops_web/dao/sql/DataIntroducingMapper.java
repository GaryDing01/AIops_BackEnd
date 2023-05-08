package com.aiops_web.dao.sql;

import com.aiops_web.dto.AlgUserDTO;
import com.aiops_web.dto.DataIntroUserDTO;
import com.aiops_web.entity.sql.DataIntroducing;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author
 * @since 2023-04-12
 */
public interface DataIntroducingMapper extends BaseMapper<DataIntroducing> {
    // DTO相关
    // 根据algId获取AlgUserDTO
    DataIntroUserDTO getDataIntroUserDTOById(Integer batchId);
    // 获取所有的AlgUserDTO
    List<DataIntroUserDTO> getAllDataIntroUserDTO();
}
