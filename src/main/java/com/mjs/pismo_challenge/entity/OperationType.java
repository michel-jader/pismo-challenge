package com.mjs.pismo_challenge.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "OperationsTypes")
public class OperationType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OperationType_ID")
    private Long operationTypeId;

    @Column(name = "Description")
    private String description;

    public OperationType() {
    }

    public OperationType(String description) {
        this.description = description;
    }

    public Long getOperationTypeId() {
        return operationTypeId;
    }

    public void setOperationTypeId(Long operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
