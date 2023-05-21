package com.aiops_web.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AnomalyInfoUserKGDTO extends AnomalyInfoUserDTO{
    private Integer withKG;

    public AnomalyInfoUserKGDTO(AnomalyInfoUserDTO anomalyInfoUserDTO, int withKG) {
        this.setAnoId(anomalyInfoUserDTO.getAnoId());
        this.setObjId(anomalyInfoUserDTO.getObjId());
        this.setStatusId(anomalyInfoUserDTO.getStatusId());
        this.setUnitnodeTypeId(anomalyInfoUserDTO.getUnitnodeTypeId());
        this.setUnitnodeName(anomalyInfoUserDTO.getUnitnodeName());
        this.setDetectTstamp(anomalyInfoUserDTO.getDetectTstamp());
        this.setPredictTstamp(anomalyInfoUserDTO.getPredictTstamp());
        this.setUpdateTstamp(anomalyInfoUserDTO.getUpdateTstamp());
        this.setDescription(anomalyInfoUserDTO.getDescription());
        this.setSourceDataId(anomalyInfoUserDTO.getSourceDataId());
        this.setDataSample(anomalyInfoUserDTO.getDataSample());
        this.setUserId(anomalyInfoUserDTO.getUserId());
        this.setWfId(anomalyInfoUserDTO.getWfId());
        this.setDeleted(anomalyInfoUserDTO.getDeleted());
        this.setUserName(anomalyInfoUserDTO.getUserName());
        this.setUnitnodeId(anomalyInfoUserDTO.getUnitnodeId());
        this.setWithKG(withKG);
    }
}
