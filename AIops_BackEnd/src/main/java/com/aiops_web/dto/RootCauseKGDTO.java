package com.aiops_web.dto;

import com.aiops_web.entity.neo4j.Node;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RootCauseKGDTO {

    private List<Node> nodeList;

    private List<Neo4jRelationshipDto> relationList;

    private RootCauseInfo rootCauseInfo;

    @Override
    public String toString() {
        return "RootCauseKGDTO{" +
                "nodeList=" + nodeList +
                ", relationList=" + relationList +
                ", rootCauseInfo=" + rootCauseInfo +
                '}';
    }
}
