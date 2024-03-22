package ru.mal.HospitalWebApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mal.HospitalWebApp.models.Doctor;
import ru.mal.HospitalWebApp.repositories.DoctorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }
    public List<Doctor> findAll(){
        return doctorRepository.findAll();
    }

    public List<Doctor> searchByFullName(String query) {
        return doctorRepository.findByFullNameStartingWith(query);
    }

    public Object findOne(int id) {
        return doctorRepository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(Doctor doctor) {
        doctorRepository.delete(doctor);
    }

    @Transactional
    public void update(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    @Transactional
    public void save(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    public Optional<Doctor> findByFullName(String fullName) {
        return doctorRepository.findByFullName(fullName);
    }

    public Optional<Doctor> getDoctorByPhoneNumber(String phoneNumber) {
        return doctorRepository.findByPhoneNumber(phoneNumber);
    }
}
