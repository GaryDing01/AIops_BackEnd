package com.aiops_web.service.impl;

import com.aiops_web.entity.sql.AiopsAlg;
import com.aiops_web.dao.sql.AiopsAlgMapper;
import com.aiops_web.service.AiopsAlgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    public List<AiopsAlg> getAllAlgs() {
        //(int algId, int pageNum, int pageSize)
//        Map<String, Integer> map = new HashMap<>();
//        pageNum = pageNum > 1? pageNum : 1;
//        pageSize = pageSize > 0? pageSize : 5;   // 默认5
//        map.put("startIdx", (pageNum-1)*pageSize);
//        map.put("pageSize", pageSize);
//        map.put("userId", 2053677);
        List<AiopsAlg> algs= algMapper.getAllAlgs();
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
    public boolean updateAlg(String param) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(param, Map.class);
        // robustness

        AiopsAlg alg = new AiopsAlg(map);
        return algMapper.updateAlg(alg) > 0;
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
