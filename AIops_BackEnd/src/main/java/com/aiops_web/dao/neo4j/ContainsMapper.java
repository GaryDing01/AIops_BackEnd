package com.aiops_web.dao.neo4j;

import com.aiops_web.entity.neo4j.Contains;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@DS("neo4j")
@Repository
public interface ContainsMapper {
    @Select("MATCH (m:System {name:\"AIops_Web\"}) -[r]- (n:Pod {name:\"Pod2\"}) " +
            "RETURN id(r) as id, r.start as parent, r.end as child")
    public List<Contains> findAll();
}
