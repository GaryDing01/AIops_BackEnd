package com.aiops_web.entity.neo4j;

import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import java.io.Serializable;

@NodeEntity(label = "Pod")
@Data
//@Builder
public class Pod implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String name;
}