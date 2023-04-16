package com.aiops_web.dao.neo4j;

import com.aiops_web.entity.neo4j.Node;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@DS("neo4j")
@Repository
public interface Neo4jNodeDao extends Neo4jRepository<Node, String> {
    
}
