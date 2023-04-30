package com.aiops_web.service.impl;

import com.aiops_web.dao.sql.RootcauseResultMapper;
import com.aiops_web.entity.sql.RootcauseResult;
import com.aiops_web.service.RootcauseResultService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RootcauseResultServiceImpl extends ServiceImpl<RootcauseResultMapper, RootcauseResult> implements RootcauseResultService {
}
