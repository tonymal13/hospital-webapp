package ru.mal.HospitalWebApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mal.HospitalWebApp.models.Doctor;
import ru.mal.HospitalWebApp.models.Visit;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface VisitRepository extends JpaRepository<Visit,Integer> {
    Optional <Visit> findByDoctorAndDateOfVisitAndTimeOfVisit(Doctor doctor, LocalDate dateOfVisit, String timeOfVisit);
}
