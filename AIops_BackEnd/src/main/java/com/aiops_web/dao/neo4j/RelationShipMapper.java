package com.aiops_web.dao.neo4j;

import com.aiops_web.entity.neo4j.Relationship;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@DS("neo4j")
@Repository
public interface RelationShipMapper {
//    @Select("MATCH (m)-[r:Relationship]->(n) RETURN id(r) as id, m as startNode, n as endNode, r.type as type")
    @Select("MATCH (m)-[r:Relationship]->(n) RETURN id(r) as id, r.type as type")
    List<Relationship> findAllRelationship();

    @Select("MATCH (m)-[r:Relationship]->(n) WHERE r.id = #{id} RETURN id(r) as id, r.type as type")
    Relationship findRelationshipById(Long id);
}
