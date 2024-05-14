package ru.mal.HospitalWebApp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.mal.HospitalWebApp.models.Doctor;

import java.util.Collection;
import java.util.Collections;

public class DoctorDetails implements UserDetails {

    private Doctor doctor;

    @Autowired
    public DoctorDetails(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(doctor.getRole()));
    }

    @Override
    public String getPassword() {
        return this.doctor.getPassword();
    }

    @Override
    public String getUsername() {
        return this.doctor.getFullName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Doctor getDoctor(){
        return this.doctor;
    }

}
