package com.aiops_web.service;

import com.aiops_web.controller.AiopsAlgController;
import com.aiops_web.entity.sql.AiopsAlg;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import scala.Int;

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

    List<AiopsAlg> getAlgByUserId(int algId);

    boolean deleteAlgById(int algId);

    int deleteAlgByIds(List<Integer> ids);

    boolean updateAlg(String param);

    boolean createAlg(String param) throws JsonProcessingException;
}
