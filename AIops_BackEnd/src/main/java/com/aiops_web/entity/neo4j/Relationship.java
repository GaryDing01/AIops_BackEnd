package com.aiops_web.entity.neo4j;

import lombok.Builder;
import lombok.Data;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
// import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Id;

@RelationshipEntity(type = "Relationship")
@Data
public class Relationship {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Node startNode;

    @EndNode
    private Node endNode;

    @Property(name = "type")
    private String type;

}
