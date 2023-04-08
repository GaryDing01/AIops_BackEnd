package com.aiops_web.entity.neo4j;

import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "System")
@Data
@Builder
public class System {

    @Id
    @GeneratedValue
    private long id;

    @Property(name = "name")
    private String name;
}
