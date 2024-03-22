package ru.mal.HospitalWebApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.mal.HospitalWebApp.models.Doctor;
import ru.mal.HospitalWebApp.services.DoctorService;

@Component
public class DoctorValidator implements Validator {

    private final DoctorService doctorService;

    @Autowired
    public DoctorValidator(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Doctor.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Doctor doctor = (Doctor) target;

        if (doctorService.getDoctorByPhoneNumber(doctor.getPhoneNumber()).isPresent())
            errors.rejectValue("phoneNumber", "", "Человек с таким номером уже существует");
    }
}
