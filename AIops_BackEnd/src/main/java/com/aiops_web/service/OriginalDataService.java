package com.aiops_web.service;

import com.aiops_web.entity.elasticsearch.OriginalData;

import java.io.IOException;
import java.util.List;

/**
 * @author DaiQL
 * @time 2023/4/17
 */
public interface OriginalDataService {
    public void createDocument(Integer batchId, String content, Integer objId) throws IOException;

    public void addBatchDocument(List<OriginalData> data) throws IOException;


}
