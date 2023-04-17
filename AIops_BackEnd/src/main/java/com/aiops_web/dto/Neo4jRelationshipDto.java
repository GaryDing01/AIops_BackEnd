package com.aiops_web.dto;

import lombok.Data;

@Data
public class Neo4jRelationshipDto {
    private Long rId;

    private String type;

    private Long startId;

    private Long endId;
}

// @RelationshipEntity(type = "Relationship")
// @Data
// public class Neo4jRelationshipDto extends Relationship{

//     private Long startId;

//     private Long endId;

//     // public Long getStartId() {
//     //     return startId;
//     // }

//     // public void setStartId(Long startId) {
//     //     this.startId = startId;
//     // }

//     // public Long getEndId() {
//     //     return endId;
//     // }

//     // public void setEndId(Long endId) {
//     //     this.endId = endId;
//     // }
// }
