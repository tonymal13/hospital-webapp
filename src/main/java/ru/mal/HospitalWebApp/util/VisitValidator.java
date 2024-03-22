package ru.mal.HospitalWebApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.mal.HospitalWebApp.models.Doctor;
import ru.mal.HospitalWebApp.models.Visit;
import ru.mal.HospitalWebApp.services.VisitService;

@Component
public class VisitValidator implements Validator {

    private final VisitService visitService;

    @Autowired
    public VisitValidator(VisitService visitService) {
        this.visitService = visitService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Doctor.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Visit visit = (Visit) target;

        if (visitService.findByDoctorAndDateOfVisitAndTimeOfVisit(visit.getDoctor(),visit.getDateOfVisit(),visit.getTimeOfVisit()).isPresent())
            errors.rejectValue("timeOfVisit", "", "Это время уже занято");
    }
}
