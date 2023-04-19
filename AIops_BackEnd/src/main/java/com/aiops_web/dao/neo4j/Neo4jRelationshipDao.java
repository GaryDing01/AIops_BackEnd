package com.aiops_web.dao.neo4j;

import com.aiops_web.dto.Neo4jRelationshipDto;
import com.aiops_web.entity.neo4j.Relationship;
import com.baomidou.dynamic.datasource.annotation.DS;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@DS("neo4j")
@Repository
public interface Neo4jRelationshipDao extends Neo4jRepository<Relationship, Long> {

    @Query("MATCH (m:Node)-[r:Relationship]->(n:Node) RETURN id(r) as rId, r.type as type, id(m) as startId, id(n) as endId ")
    List<Neo4jRelationshipDto> findAllRelationships();

    @Query("MATCH (m:Node)-[r:Relationship]->(n:Node) WHERE id(r) = $id RETURN id(r) as rId, r.type as type, id(m) as startId, id(n) as endId ")
    Neo4jRelationshipDto findRelationshipById(Long id);

    @Query("MATCH (m:Node)-[r:Relationship]->(n:Node) WHERE id(m) = $startId AND id(n) = $EndId RETURN id(r) as rId, r.type as type, id(m) as startId, id(n) as endId ")
    List<Neo4jRelationshipDto> findRelationshipByStartIdAndEndId(Long startId, Long EndId);

    @Query("MATCH (m:Node)-[r:Relationship]->(n:Node) WHERE id(r) = $id DELETE r ")
    Boolean deleteRelationshipById(Long id);

    @Query("MATCH (m:Node)-[r:Relationship]->(n:Node) WHERE id(r) in $ids DELETE r ")
    Boolean deleteRelationshipsByIds(List<Long> ids);

    @Query("MATCH (m:Node), (n:Node) WHERE id(m) = $startId AND id(n) = $endId CREATE (m)-[r:Relationship {type: $type, content: $content}]->(n) RETURN id(r) as rId ")
    Long addRelationship(String type, String content, Long startId, Long endId);

    @Query("MATCH (m:Node)-[r:Relationship]->(n:Node) WHERE id(r) = $rId SET r.type = $type, r.content = $content RETURN id(r) as rId, r.type as type, id(m) as startId, id(n) as endId ")
    Neo4jRelationshipDto updateRelationship(Long rId, String type, String content);
}