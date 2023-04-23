package com.aiops_web.service;

import com.aiops_web.entity.elasticsearch.OriginalData;

import java.io.IOException;
import java.util.List;

/**
 * @author DaiQL
 * @time 2023/4/17
 */
public interface OriginalDataService {
    List<OriginalData> getRange(int beginId, int endId) throws IOException;

    List<OriginalData> getAll();

    boolean deleteRange(int beginId, int endId) throws IOException;

    void addBatchDoc(int batchId, int objId, String filepath);
}
