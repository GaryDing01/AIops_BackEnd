package com.aiops_web.dao.neo4j;

import com.aiops_web.entity.neo4j.Node;
import com.baomidou.dynamic.datasource.annotation.DS;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@DS("neo4j")
@Repository
public interface Neo4jNodeDao extends Neo4jRepository<Node, Long> {

    @Query("MATCH (n:Node) WHERE n.type = $type RETURN n ")
    List<Node> findByType(String type);

    @Query("MATCH (n:Node) WHERE id(n) in $ids DETACH DELETE n ")
    void deleteByIds(List<Long> ids);

    @Query("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r ")
    void deleteAllData();
}