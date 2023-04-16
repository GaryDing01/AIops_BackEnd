package com.aiops_web.dao.neo4j;

import com.aiops_web.entity.neo4j.Relationship;
import com.baomidou.dynamic.datasource.annotation.DS;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@DS("neo4j")
@Repository
public interface Neo4jRelationshipDao extends Neo4jRepository<Relationship, Long> {

    @Query("MATCH ()-[r:Relationship]->() RETURN r")
    List<Relationship> findAllRelationship();

    @Query("MATCH ()-[r:Relationship]->() WHERE r.id = $id RETURN r")
    Relationship findRelationshipById(Long id);
}
