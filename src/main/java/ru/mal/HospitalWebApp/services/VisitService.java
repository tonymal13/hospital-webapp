package ru.mal.HospitalWebApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mal.HospitalWebApp.models.Visit;
import ru.mal.HospitalWebApp.repositories.VisitRepository;
import ru.mal.HospitalWebApp.models.Doctor;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class VisitService {

    private final VisitRepository visitRepository;

    @Autowired
    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public Object findOne(int id) {
        return visitRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(int id, Visit visit) {
        visit.setId(id);
        visitRepository.save(visit);
    }

    @Transactional
    public void save(Visit visit) {
        visitRepository.save(visit);
    }

    public Optional<Visit> findByDoctorAndDateOfVisitAndTimeOfVisit(Doctor doctor, LocalDate date, String time) {
        return visitRepository.findByDoctorAndDateOfVisitAndTimeOfVisit(doctor,date,time);
    }

}
