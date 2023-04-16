package com.aiops_web.entity.neo4j;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

import org.springframework.data.annotation.Id;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "Node")
@Data
@Builder
public class Node {

    @Id
    @GeneratedValue
    private String id;

    @Property(name = "type")
    private String type;

    @Property(name = "name")
    private String name;

    @Property(name = "content")
    private String content;

    @Property(name = "parentId")
    private String parentId;
}
