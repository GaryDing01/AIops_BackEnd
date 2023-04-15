package com.aiops_web.service;

import com.aiops_web.controller.AiopsAlgController;
import com.aiops_web.entity.sql.AiopsAlg;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2023-04-12
 */
public interface AiopsAlgService extends IService<AiopsAlg> {

    AiopsAlg getAlgById(int algId);

    boolean deleteAlgById(int algId);

    boolean updateAlg(String param);

    boolean createAlg(String param);
}
