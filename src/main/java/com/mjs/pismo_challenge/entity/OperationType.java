package com.mjs.pismo_challenge.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "`OperationsTypes`")
public class OperationType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OperationType_ID")
    private Long operationTypeId;

    @Column(name = "Description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "Direction", nullable = false)
    private OperationDirection direction;

    public enum OperationDirection {
        CREDIT,
        DEBIT
    }

    public OperationType() {
    }

    public OperationType(String description, OperationDirection direction) {
        this.description = description;
        this.direction = direction;
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

    public OperationDirection getDirection() {
        return direction;
    }

    public void setDirection(OperationDirection direction) {
        this.direction = direction;
    }
}
