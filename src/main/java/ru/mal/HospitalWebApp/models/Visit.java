package ru.mal.HospitalWebApp.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "visit")
public class Visit {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    private OutpatientCard card;
    @Column(name="date_of_visit")
    private LocalDate dateOfVisit;
    @Column(name = "time_of_visit")
    private String timeOfVisit;
    @Column(name = "treatment_info")
    private String treatmentInfo;
    @ManyToOne
    @JoinColumn(name = "doctor_id",referencedColumnName = "id")
    private Doctor doctor;
    @OneToOne(mappedBy = "visit",cascade = CascadeType.ALL)
    private Contract contract;

    public Visit(String treatmentInfo) {
        this.treatmentInfo = treatmentInfo;
    }


    public Visit() {
    }

    public Visit(OutpatientCard card, LocalDate dateOfVisit,String timeOfVisit, String treatmentInfo, Doctor doctor, Contract contract) {
        this.card = card;
        this.dateOfVisit = dateOfVisit;
        this.timeOfVisit = timeOfVisit;
        this.treatmentInfo = treatmentInfo;
        this.doctor = doctor;
        this.contract = contract;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OutpatientCard getCard() {
        return card;
    }

    public void setCard(OutpatientCard card) {
        this.card = card;
    }

    public String getTreatmentInfo() {
        return treatmentInfo;
    }

    public void setTreatmentInfo(String treatmentInfo) {
        this.treatmentInfo = treatmentInfo;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }


    public LocalDate getDateOfVisit() {
        return dateOfVisit;
    }

    public void setDateOfVisit(LocalDate dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }

    public String getTimeOfVisit() {
        return timeOfVisit;
    }

    public void setTimeOfVisit(String timeOfVisit) {
        this.timeOfVisit = timeOfVisit;
    }
}
