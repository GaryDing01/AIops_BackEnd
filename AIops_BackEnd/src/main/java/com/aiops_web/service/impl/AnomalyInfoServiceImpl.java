package com.aiops_web.service.impl;

import com.aiops_web.entity.sql.AnomalyInfo;
import com.aiops_web.dao.sql.AnomalyInfoMapper;
import com.aiops_web.service.AnomalyInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
public class AnomalyInfoServiceImpl extends ServiceImpl<AnomalyInfoMapper, AnomalyInfo> implements AnomalyInfoService {

    @Autowired
    private AnomalyInfoMapper anomalyInfoMapper;

    @Override
    public List<AnomalyInfo> getAnomalyInfos(AnomalyInfo info, int pageNum, int pageSize) {
        pageNum = pageNum > 1? pageNum : 1;
        pageSize = pageSize > 0? pageSize : 5;   // 默认5
        // 参数类型 不用map了
//        Map<String, Object> map = new HashMap<>();
//        map.put("startIdx", (pageNum-1)*pageSize);
//        map.put("pageSize", pageSize);
//        map.put("info", info);
        return anomalyInfoMapper.getAnomalyInfos((pageNum-1)*pageSize, pageSize, info);
    }

    @Override
    public boolean deleteByAnoId(int anoId) {
        return anomalyInfoMapper.deleteByAnoId(anoId) > 0;
    }

    @Override
    public AnomalyInfo updateStatusById(int anoId, int statusId) {
        // 更改状态并且返回新的故障信息
        if (statusId <= 0 || statusId > 5)
            return null;
        if (anomalyInfoMapper.updateStatusById(anoId,statusId) > 0) {
            return anomalyInfoMapper.getById(anoId);
        }
        return null;
    }

    @Override
    public boolean updateInfo(AnomalyInfo info) {
        return anomalyInfoMapper.updateAnoInfo(info) > 0;
    }


}
