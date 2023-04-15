package com.aiops_web.dao.sql;

import com.aiops_web.entity.sql.AiopsAlg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2023-04-12
 */
public interface AiopsAlgMapper extends BaseMapper<AiopsAlg> {

    AiopsAlg getAlgById(int algId);

    int deleteAlgById(int algId);

    int createAlg(AiopsAlg alg);

    int updateAlg(AiopsAlg alg);


}
