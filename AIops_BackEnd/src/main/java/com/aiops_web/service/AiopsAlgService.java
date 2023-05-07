package com.aiops_web.service;

import com.aiops_web.dto.AlgUserDTO;
import com.aiops_web.entity.sql.AiopsAlg;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author
 * @since 2023-04-12
 */
public interface AiopsAlgService extends IService<AiopsAlg> {
    List<AiopsAlg> getAllAlgs();

    boolean deleteAlgById(int algId);

    int deleteAlgByIds(List<Integer> ids);

    boolean updateAlg(AlgUserDTO algUserDTO) throws JsonProcessingException;

    boolean createAlg(AiopsAlg alg) throws JsonProcessingException;

    // 新修改
    int createAlg_new(AlgUserDTO algUserDTO);

    // DTO相关
    // 根据algId获取一个AlgUserDTO
    AlgUserDTO getAlgUserDTOById(Integer algId);
    // 获取所有的AlgUserDTO
    List<AlgUserDTO> getAllAlgUserDTO();
}
