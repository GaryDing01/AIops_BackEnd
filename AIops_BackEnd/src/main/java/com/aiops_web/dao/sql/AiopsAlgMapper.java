package com.aiops_web.dao.sql;

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

    List<AiopsAlg> getAlgByUserId(int userId);

    int deleteAlgById(int algId);

    int deleteAlgByIds(@Param("ids")List<Integer> ids);

    int createAlg(AiopsAlg alg);

    int updateAlg(AiopsAlg alg);


}
