package ru.mal.HospitalWebApp.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "outpatient_card")
public class OutpatientCard implements Serializable{

    @Id
    @OneToOne()
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private OurUser user;

    @OneToMany(mappedBy = "card")
    private List<Visit> visits;

    @Column(name = "id")
    private int id;

    public OutpatientCard() {
    }

    public OutpatientCard(OurUser user, int cardId) {
        this.user=user;
        this.id=cardId;
    }

    public OutpatientCard(OurUser user, List<Visit> visits, int id) {
        this.user = user;
        this.visits = visits;
        this.id = id;
    }

    public OurUser getUser() {
        return user;
    }

    public void setUser(OurUser user) {
        this.user = user;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
