package com.example.rest.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ComplexProcess implements Serializable {

    private Integer processId;

    private String processName;

    private String processStatus;

    private String overallTime;

    private Integer threshold;

    private Integer finalValue;

    private Integer equipmentNumber;

    private Boolean thresholdSatisfied;

    public ComplexProcess(Integer processId, String processName, String processStatus, String overallTime, Integer threshold,
                          Integer finalValue, Integer equipmentNumber, Boolean thresholdSatisfied){
        this.processId = processId;
        this.processName = processName;
        this.processStatus = processStatus;
        this.overallTime = overallTime;
        this.threshold = threshold;
        this.finalValue = finalValue;
        this.equipmentNumber = equipmentNumber;
        this.thresholdSatisfied = thresholdSatisfied;
    }
}
