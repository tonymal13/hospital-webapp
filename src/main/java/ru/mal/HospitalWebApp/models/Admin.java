package ru.mal.HospitalWebApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;


@Entity
@Table(name="admin")
public class Admin{
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="full_name")
    @NotEmpty(message = "Name should not be empty")
    private String fullName;
    @Column(name = "phone_number")
    @NotEmpty
    @Pattern(regexp = "^(8)+(\\d{10})",message = "Номер телефона должен быть в формате: 89876543210")
    private String phoneNumber;
    @Column(name = "password")
    private String password;
    @Column(name="role")
    private String role;

    public Admin() {
    }

    public Admin(String fullName, String phoneNumber, String password, String role) {
        this.fullName=fullName;
        this.phoneNumber = phoneNumber;
        this.password=password;
        this.role=role;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
