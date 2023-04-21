package com.aiops_web.entity.neo4j;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.springframework.data.annotation.Id;

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
