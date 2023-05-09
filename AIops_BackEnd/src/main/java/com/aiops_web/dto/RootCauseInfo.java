package com.aiops_web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class RootCauseInfo {

    private List<String> rootCauseNodeNames;

    private List<String> rootCauseRelationIds;

    @Override
    public String toString() {
        return "RootCauseInfo{" +
                "rootCauseNodeNames=" + rootCauseNodeNames +
                ", rootCauseRelationIds=" + rootCauseRelationIds +
                '}';
    }
}
