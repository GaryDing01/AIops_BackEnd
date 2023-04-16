package com.aiops_web.service.impl;

import com.aiops_web.entity.sql.AiopsAlg;
import com.aiops_web.dao.sql.AiopsAlgMapper;
import com.aiops_web.service.AiopsAlgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    public List<AiopsAlg> getAlgByUserId(int userId) {
        List<AiopsAlg> algs= algMapper.getAlgByUserId(userId);
        return algs;
    }

    @Override
    public boolean deleteAlgById(int algId) {
        return algMapper.deleteAlgById(algId) > 0;
    }

    @Override
    public int deleteAlgByIds(List<Integer> ids) {
        // 返回修改的行数  即被删除了的行数
        return algMapper.deleteAlgByIds(ids);
    }

    @Override
    public boolean updateAlg(String param) {
        AiopsAlg alg = new AiopsAlg();
//        return algMapper.updateAlg(alg) > 0;
        return true;
    }

    @Override
    public boolean createAlg(String param) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(param, Map.class);
        // robustness

        AiopsAlg alg = new AiopsAlg(map);
        return algMapper.createAlg(alg) > 0;
    }
}
