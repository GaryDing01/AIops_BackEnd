package com.aiops_web.service.mysql.impl;

import com.aiops_web.dao.mysql.AnodetectResultMapper;
import com.aiops_web.entity.mysql.AnodetectResult;
import com.aiops_web.service.mysql.AnodetectResultService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AnodetectResultServiceImpl extends ServiceImpl<AnodetectResultMapper, AnodetectResult> implements AnodetectResultService {
}
