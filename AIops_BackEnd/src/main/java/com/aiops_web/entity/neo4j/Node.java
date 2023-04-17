package com.aiops_web.entity.neo4j;

import lombok.Data;

// import org.springframework.data.annotation.Id;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

@NodeEntity(label = "Node")
@Data
public class Node {

    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "type")
    private String type;

    @Property(name = "name")
    private String name;

    @Property(name = "content")
    private String content;

    @Property(name = "parentId")
    private Long parentId;
}
