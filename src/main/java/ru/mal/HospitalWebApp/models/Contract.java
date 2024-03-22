package ru.mal.HospitalWebApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.io.Serializable;

@Entity
@Table(name="contract")
public class Contract implements Serializable {
    @Id
    @OneToOne()
    @JoinColumn(name = "visit_id",referencedColumnName = "id" )
    private Visit visit;
    @Column(name = "contract_number")
    @Min(value = 0,message = "Id should be greater than zero")
    private int ContractNumber;

    @Column(name = "text")
    private String text;

    public Contract() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public int getContractNumber() {
        return ContractNumber;
    }

    public void setContractNumber(int contractNumber) {
        ContractNumber = contractNumber;
    }

}
