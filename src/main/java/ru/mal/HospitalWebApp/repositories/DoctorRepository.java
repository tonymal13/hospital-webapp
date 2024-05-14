package ru.mal.HospitalWebApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mal.HospitalWebApp.models.Doctor;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Integer> {
    Optional<Doctor> findByFullName(String fullName);

    List<Doctor> findByFullNameStartingWith(String query);

    Optional<Doctor> findByPhoneNumber(String username);
}
