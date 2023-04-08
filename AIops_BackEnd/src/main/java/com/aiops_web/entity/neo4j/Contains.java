package com.aiops_web.entity.neo4j;

import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "contains")
@Data
@Builder
public class Contains {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private System parent;

    @EndNode
    private Pod child;
}
