package com.aiops_web.service.mysql;

import com.aiops_web.entity.elasticsearch.OriginalData;

import java.util.List;

/**
 * @author DaiQL
 * @time 2023/4/17
 */
public interface OriginalDataService {
    List<OriginalData> getRange(long beginId, long endId);

    List<OriginalData> getRelativeRange(int batchId, long beginId, long endId);

    boolean deleteRange(long beginId, long endId);

    long addBatchDoc(int batchId, int objId, String filepath);

    void TransferDataToLake(int batchId);
}
