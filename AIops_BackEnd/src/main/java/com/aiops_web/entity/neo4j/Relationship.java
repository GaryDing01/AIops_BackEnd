package com.aiops_web.entity.neo4j;

import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.*;
import org.springframework.data.annotation.Id;

@RelationshipEntity(type = "Relationship")
@Data
@Builder
public class Relationship {
    @Id
    @GeneratedValue
    private Long id;

////    @Property(name = "startNode")
//    @StartNode
//    private Node startNode;
//
////    @Property(name = "endNode")
//    @EndNode
//    private Node endNode;

    @Property(name = "type")
    private String type;
}
