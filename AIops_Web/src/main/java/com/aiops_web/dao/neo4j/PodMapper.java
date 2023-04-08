package com.aiops_web.dao.neo4j;

import com.aiops_web.entity.neo4j.Pod;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@DS("neo4j")
@Repository
public interface PodMapper {

//    @Query("match (n:Pod) return n")
    public List<Pod> findAll();
}
