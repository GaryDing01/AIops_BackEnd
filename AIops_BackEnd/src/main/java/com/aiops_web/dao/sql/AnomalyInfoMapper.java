package com.aiops_web.dao.sql;

import com.aiops_web.dto.AnomalyInfoUserDTO;
import com.aiops_web.entity.sql.AnomalyInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2023-04-12
 */
public interface AnomalyInfoMapper extends BaseMapper<AnomalyInfo> {
    List<AnomalyInfoUserDTO> getAnomalyInfos(@Param("start") int start, @Param("size") int pageSize, @Param("info") AnomalyInfo info);

    AnomalyInfo getById(int anoId);

    int deleteByAnoId(int anoId);

    int updateStatusById(@Param("anoId") int anoId, @Param("statusId") int statusId);

    int updateAnoInfo(AnomalyInfo info);
}
