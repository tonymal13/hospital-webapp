package ru.mal.HospitalWebApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name="our_user")
public class OurUser{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "full_name")
    @NotEmpty
    @Size(min=2,max=100,message = "Имя должно быть от 2 до 100 символов")
    @Pattern(regexp = "[А-Я]\\w+,[А-Я]\\w+",message = "Имя должно быть в следующем формате: Иванов Иван")
    private String fullName;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "phone_number")
    @NotEmpty
    @Pattern(regexp = "^(8)+(\\d{10})",message = "Номер телефона должен быть в формате: 89876543210")
    private String phoneNumber;
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private OutpatientCard outpatientCard;
    @Column(name = "password")
    private String password;
    @Column(name="role")
    private String role;

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public OutpatientCard getOutpatientCard() {
        return outpatientCard;
    }

    public void setOutpatientCard(OutpatientCard outpatientCard) {
        this.outpatientCard = outpatientCard;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public OurUser(String fullName, String password, LocalDate dateOfBirth, String phoneNumber) {
        this.fullName=fullName;
        this.password=password;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }
    public OurUser() {
    }
}
