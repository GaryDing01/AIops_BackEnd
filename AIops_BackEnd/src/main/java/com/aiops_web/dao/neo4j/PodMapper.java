package com.aiops_web.dao.neo4j;

import com.aiops_web.entity.neo4j.Pod;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@DS("neo4j")
@Repository
public interface PodMapper {

    @Select("match (n:Pod) return id(n) as id, n.name as name")
    public List<Pod> findAll();
}
