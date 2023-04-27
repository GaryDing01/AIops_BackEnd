package com.aiops_web.service.neo4j;

import com.aiops_web.entity.elasticsearch.OriginalData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;
import java.util.List;

public interface OriginalDataService {
    public void createDocument(Integer batchId, String content, Integer objId) throws IOException;

    public void addBatchDocument(List<OriginalData> data) throws IOException;
}
