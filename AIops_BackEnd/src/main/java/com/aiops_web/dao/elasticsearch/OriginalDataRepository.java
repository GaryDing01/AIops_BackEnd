package com.aiops_web.dao.elasticsearch;

import com.aiops_web.entity.elasticsearch.OriginalData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author DaiQL
 * @time 2023/4/20
 */
public interface OriginalDataRepository extends ElasticsearchRepository<OriginalData, Long> {
}
