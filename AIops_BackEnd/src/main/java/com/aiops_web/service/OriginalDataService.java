package com.aiops_web.service;

import com.aiops_web.entity.elasticsearch.OriginalData;

import java.util.List;

/**
 * @author DaiQL
 * @time 2023/4/17
 */
public interface OriginalDataService {
    List<OriginalData> getRange(int beginId, int endId);

    List<OriginalData> getRelativeRange(int batchId, int beginId, int endId);

    boolean deleteRange(int beginId, int endId);

    void addBatchDoc(int batchId, int objId, String filepath);
}
