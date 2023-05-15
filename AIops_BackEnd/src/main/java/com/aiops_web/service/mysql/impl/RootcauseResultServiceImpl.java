package com.aiops_web.service.mysql.impl;

import com.aiops_web.dao.mysql.RootcauseResultMapper;
import com.aiops_web.entity.mysql.RootcauseResult;
import com.aiops_web.service.mysql.RootcauseResultService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RootcauseResultServiceImpl extends ServiceImpl<RootcauseResultMapper, RootcauseResult> implements RootcauseResultService {
}
