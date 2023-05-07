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

    @Query("MATCH (n:Node) WHERE id(n) IN $ids RETURN n")
    List<Node> findAllByIds(List<Long> ids);

    @Query("MATCH (n:Node) WHERE id(n) in $ids DETACH DELETE n ")
    void deleteByIds(List<Long> ids);

    @Query("MATCH (n:Node) OPTIONAL MATCH (n)-[r]-() DELETE n,r ")
    void deleteAllData();

    @Query("MATCH (n:Node) WHERE n.name = $name AND n.type = 'Service' RETURN n")
    Node findNodeByNameInService(String name);

    // 获取ids的所有子节点ids
    @Query("MATCH (n:Node) WHERE id(n) IN $ids WITH collect(n) AS nodes MATCH (m:Node)-[r:Relationship* {type:'contains'}]->(n:Node) WHERE m in nodes RETURN id(n)")
    List<Long> findChildrenNodesIdsByNodesIds(List<Long> ids);

    // 获取ids的所有父节点ids
    @Query("MATCH (n:Node) WHERE id(n) IN $ids WITH collect(n) AS nodes MATCH (m:Node)-[r:Relationship* {type:'contains'}]->(n:Node) WHERE n in nodes RETURN id(m)")
    List<Long> findParentNodesIdsByNodesIds(List<Long> ids);

    // 获取Service ids的所有相关Node的ids
    @Query("MATCH (n:Node {type:'Service'}) WHERE id(n) IN $ids WITH collect(n) AS nodes MATCH (m:Node)-[:Relationship {type:'logical_abstracts'}]->(n:Node {type:'Pod'})-[:Relationship {type:'runs_in'}]->(k:Node {type:'Node'}) WHERE m IN nodes RETURN id(k)")
    List<Long> findServiceIdsRelevantNode(List<Long> ids);

    // 获取Service ids的所有相关Pod的ids
    @Query("MATCH (n:Node {type:'Service'}) WHERE id(n) IN $ids WITH collect(n) AS nodes MATCH (m:Node)-[:Relationship {type:'logical_abstracts'}]->(n:Node {type:'Pod'}) WHERE m IN nodes RETURN id(n)")
    List<Long> findServiceIdsRelevantPod(List<Long> ids);
}
