package ru.mal.HospitalWebApp.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.mal.HospitalWebApp.models.OurUser;
import ru.mal.HospitalWebApp.services.UserService;

@Component
public class UserValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(OurUser.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OurUser person = (OurUser) target;

        if (userService.getPersonByPhoneNumber(person.getPhoneNumber()).isPresent())
            errors.rejectValue("phoneNumber", "", "Человек с таким номером уже существует");
    }
}
