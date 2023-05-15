package com.aiops_web.service.mysql.impl;

import com.aiops_web.dao.mysql.CleanedDataMapper;
import com.aiops_web.entity.mysql.CleanedData;
import com.aiops_web.service.mysql.CleanedDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CleanedDataServiceImpl extends ServiceImpl<CleanedDataMapper, CleanedData> implements CleanedDataService {
}
