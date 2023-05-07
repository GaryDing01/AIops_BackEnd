package com.aiops_web.service.impl;

import com.aiops_web.dao.sql.CleanedDataMapper;
import com.aiops_web.entity.sql.CleanedData;
import com.aiops_web.service.CleanedDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CleanedDataServiceImpl extends ServiceImpl<CleanedDataMapper, CleanedData> implements CleanedDataService {
}
