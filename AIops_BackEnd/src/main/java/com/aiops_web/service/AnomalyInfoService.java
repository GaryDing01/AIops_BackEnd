package com.aiops_web.service;

import com.aiops_web.entity.sql.AnomalyInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2023-04-12
 */
public interface AnomalyInfoService extends IService<AnomalyInfo> {

    List<AnomalyInfo> getAnomalyInfos(AnomalyInfo info, int pageNum, int pageSize);

    boolean deleteByAnoId(int anoId);

    AnomalyInfo updateStatusById(int anoId, int statusId);

    boolean updateInfo(AnomalyInfo info);
}
