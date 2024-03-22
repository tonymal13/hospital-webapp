package ru.mal.HospitalWebApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity @Table(name = "doctor")
public class Doctor{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") private int id;
    @Column(name = "number_of_visit") private int numberOfVisit;
    @Column(name = "phone_number") @NotEmpty @Pattern(regexp = "^(8)+(\\d{10})",message = "Номер телефона должен быть в формате: 89876543210")
    private String phoneNumber;
    @Column(name = "full_name") @NotEmpty(message = "Имя не должно быть пустым") @Size(min=2,max=100,message = "Имя должно быть от 2 до 100 символов")
    @Pattern(regexp = "[А-Я]\\w+,[А-Я]\\w+",message = "Имя должно быть в следующем формате: Иванов Иван") private String fullName;
    @Column(name = "additional_info") @NotEmpty(message = "Информация не должна быть пустой") private String additionalInfo;
    @Column(name = "start_time") @Pattern(regexp = "(\\d{2})+:+(\\d{2})",message = "Время начала работы должно быть в формате: 09:00")
    private String startTime;
    @Column(name = "finish_time") @Pattern(regexp = "(\\d{2})+:+(\\d{2})",message = "Время окончания работы должно быть в формате: 09:00")
    private String finishTime;
    @OneToMany(mappedBy = "doctor") private List<Visit> visits;
    @Column(name = "specialization") @NotEmpty(message = "Специализация не должна быть пустой") private String specialization;
    @Column(name = "password") private String password;
    @Column(name="role") private String role;
    @Column(name = "url") private String url;

    public Doctor() {
    }

    public Doctor(String phoneNumber, String fullName, String password, String additionalInfo, String  startTime, String  finishTime, String specialization, String role, String url) {
        this.numberOfVisit = 0;
        this.phoneNumber = phoneNumber;
        this.fullName=fullName;
        this.password=password;
        this.additionalInfo = additionalInfo;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.specialization = specialization;
        this.role=role;
        this.url = url;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getNumberOfVisit() {
        return numberOfVisit;
    }

    public void setNumberOfVisit(int numberOfVisit) {
        this.numberOfVisit = numberOfVisit;
    }

    public void addNumberOfVisit(){
        numberOfVisit++;
    }
}
