package com.aiops_web.entity.neo4j;

import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "relationship")
@Data
@Builder
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
