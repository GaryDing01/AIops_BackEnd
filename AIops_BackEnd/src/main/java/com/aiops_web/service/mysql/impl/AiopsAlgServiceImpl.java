package com.aiops_web.service.mysql.impl;

import com.aiops_web.dto.AlgUserDTO;
import com.aiops_web.entity.mysql.AiopsAlg;
import com.aiops_web.dao.mysql.AiopsAlgMapper;
import com.aiops_web.service.mysql.AiopsAlgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
    public boolean updateAlg(AlgUserDTO algUserDTO) throws JsonProcessingException {
//        ObjectMapper mapper = new ObjectMapper();
//        Map<String, Object> map = mapper.readValue(param, Map.class);
//        // robustness
        AiopsAlg alg = new AiopsAlg();
        alg.setAlgId(algUserDTO.getAlgId());
        alg.setTypeId(algUserDTO.getTypeId());
        alg.setName(algUserDTO.getName());
        alg.setIntro(algUserDTO.getIntro());
        alg.setSource(algUserDTO.getSource());
        alg.setFilePath(algUserDTO.getFilePath());
        alg.setUpdateNum(algUserDTO.getUpdateNum());
        alg.setUserId(algUserDTO.getUserId());
        alg.setParam(algUserDTO.getParam());
        alg.setContent(algUserDTO.getContent());
        alg.setUpdateTstamp(new Date(System.currentTimeMillis()));
        return algMapper.updateAlg(alg) > 0;
    }

    @Override
    public boolean createAlg(AiopsAlg alg) throws JsonProcessingException {
//        ObjectMapper mapper = new ObjectMapper();
//        Map<String, Object> map = mapper.readValue(param, Map.class);
//        // robustness
        alg.setUpdateTstamp(new Date(System.currentTimeMillis()));
        return algMapper.createAlg(alg) > 0;
    }

    @Override
    public int createAlg_new(AlgUserDTO algUserDTO) {
        AiopsAlg alg = new AiopsAlg();
        alg.setTypeId(algUserDTO.getTypeId());
        alg.setName(algUserDTO.getName());
        alg.setIntro(algUserDTO.getIntro());
        alg.setSource(algUserDTO.getSource());
        alg.setFilePath(algUserDTO.getFilePath());
        alg.setUserId(algUserDTO.getUserId());
        alg.setParam(algUserDTO.getParam());
        alg.setContent(algUserDTO.getContent());
        alg.setUpdateTstamp(new Date(System.currentTimeMillis()));
        alg.setUpdateNum(0);
        int saveResult = algMapper.insert(alg);
        if (saveResult == 0) {
            return 0;
        }
        return alg.getAlgId();
    }

    // DTO相关
    // 根据algId获取一个AlgUserDTO
    @Override
    public AlgUserDTO getAlgUserDTOById(Integer algId) {
        return algMapper.getAlgUserDTOById(algId);
    }
    // 获取所有的AlgUserDTO
    @Override
    public List<AlgUserDTO> getAllAlgUserDTO() {
        return algMapper.getAllAlgUserDTO();
    }
}
