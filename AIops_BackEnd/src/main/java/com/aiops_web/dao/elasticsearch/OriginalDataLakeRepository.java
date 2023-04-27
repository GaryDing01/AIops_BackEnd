package com.aiops_web.dao.elasticsearch;

import com.aiops_web.entity.elasticsearch.OriginalDataLake;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author DQL
 * @time 2023/4/23
 */
public interface OriginalDataLakeRepository extends ElasticsearchRepository<OriginalDataLake, Long> {
}
