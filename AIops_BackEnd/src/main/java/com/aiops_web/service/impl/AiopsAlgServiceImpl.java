package com.aiops_web.service.impl;

import com.aiops_web.entity.sql.AiopsAlg;
import com.aiops_web.dao.sql.AiopsAlgMapper;
import com.aiops_web.service.AiopsAlgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2023-04-12
 */
@Service
public class AiopsAlgServiceImpl extends ServiceImpl<AiopsAlgMapper, AiopsAlg> implements AiopsAlgService {

    @Resource
    private AiopsAlgMapper algMapper;

    @Override
    public AiopsAlg getAlgById(int algId) {
        AiopsAlg alg = algMapper.getAlgById(algId);
        return alg;
    }

    @Override
    public boolean deleteAlgById(int algId) {
        return algMapper.deleteAlgById(algId) > 0;
    }

    @Override
    public boolean updateAlg(String param) {
        AiopsAlg alg = new AiopsAlg();
        return algMapper.updateAlg(alg) > 0;
    }

    @Override
    public boolean createAlg(String param) {
        AiopsAlg alg = new AiopsAlg();
        return algMapper.createAlg(alg) > 0;
    }
}
