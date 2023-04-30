package com.aiops_web.service.impl;

import com.aiops_web.dao.sql.AnodetectResultMapper;
import com.aiops_web.entity.sql.AnodetectResult;
import com.aiops_web.service.AnodetectResultService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AnodetectResultServiceImpl extends ServiceImpl<AnodetectResultMapper, AnodetectResult> implements AnodetectResultService {
}
