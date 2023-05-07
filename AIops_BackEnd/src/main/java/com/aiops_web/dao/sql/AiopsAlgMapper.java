package com.aiops_web.dao.sql;

import com.aiops_web.dto.AlgUser;
import com.aiops_web.dto.AlgUserDTO;
import com.aiops_web.entity.sql.AiopsAlg;
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
public interface AiopsAlgMapper extends BaseMapper<AiopsAlg> {
    List<AiopsAlg> getAllAlgs();

    int deleteAlgById(int algId);

    int deleteAlgByIds(@Param("ids")List<Integer> ids);

    int createAlg(AiopsAlg alg);

    int updateAlg(AiopsAlg alg);

    // 新增
//    String getUserName(Integer userId); // 可以不用写, 多表查询时在xml中定义语句即可

    // 根据algId获取AlgUserDTO
    AlgUserDTO getAlgUserDTOById(Integer algId);
    // 获取所有的AlgUserDTO
    List<AlgUserDTO> getAllAlgUserDTO();
}
