package com.aiops_web.dao.mysql;

import com.aiops_web.dto.AnomalyInfoUserDTO;
import com.aiops_web.entity.mysql.AnomalyInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author
 * @since 2023-04-12
 */
public interface AnomalyInfoMapper extends BaseMapper<AnomalyInfo> {
    List<AnomalyInfoUserDTO> getAnomalyInfos(@Param("start") int start, @Param("size") int pageSize, @Param("info") AnomalyInfoUserDTO info);

    AnomalyInfo getById(int anoId);

    int deleteByAnoId(int anoId);

    int updateStatusById(@Param("anoId") int anoId, @Param("statusId") int statusId);

    int updateAnoInfo(AnomalyInfo info);

    // DTO相关
    // 根据algId获取AlgUserDTO
    AnomalyInfoUserDTO getAnomalyInfoUserDTOById(Integer anoId);
    // 获取所有的AlgUserDTO
    List<AnomalyInfoUserDTO> getAllAnomalyInfoUserDTO();
}
