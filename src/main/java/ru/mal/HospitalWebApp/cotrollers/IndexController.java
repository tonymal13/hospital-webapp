package ru.mal.HospitalWebApp.cotrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.mal.HospitalWebApp.models.Admin;
import ru.mal.HospitalWebApp.models.Doctor;
import ru.mal.HospitalWebApp.models.OurUser;
import ru.mal.HospitalWebApp.services.AdminService;
import ru.mal.HospitalWebApp.services.DoctorService;
import ru.mal.HospitalWebApp.services.UserService;

import java.util.Optional;

@Controller
public class IndexController {
    
    private final UserService userService;

    private final DoctorService doctorService;

    private final AdminService adminService;

    @Autowired
    public IndexController(UserService userService, DoctorService doctorService, AdminService adminService) {
        this.userService = userService;
        this.doctorService = doctorService;
        this.adminService = adminService;
    }

    @GetMapping("/index")
    public String indexPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName=authentication.getName();

        Optional<OurUser> user= userService.findBuFullName(currentPrincipalName);

        if(user.isPresent()){
            model.addAttribute("user",user.get());
        }
        else{
            Optional<Doctor> d=doctorService.findByFullName(currentPrincipalName);
            if(d.isPresent()){
                model.addAttribute("user",d.get());
            }
            else{
                Optional<Admin> admin=adminService.findByFullName(currentPrincipalName);
                if(admin.isPresent()){
                    model.addAttribute("user",admin.get());
                }
                else{
                    model.addAttribute("user",new OurUser());
                }
            }
        }
        return "/index";
    }
}
