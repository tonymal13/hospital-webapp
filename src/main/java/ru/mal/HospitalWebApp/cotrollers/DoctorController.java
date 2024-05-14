package ru.mal.HospitalWebApp.cotrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mal.HospitalWebApp.models.Doctor;
import ru.mal.HospitalWebApp.models.Visit;
import ru.mal.HospitalWebApp.services.DoctorService;
import ru.mal.HospitalWebApp.services.UserService;
import ru.mal.HospitalWebApp.services.VisitService;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    private final UserService userService;

    private final VisitService visitService;

    @Autowired
    public DoctorController(DoctorService doctorService, UserService userService, VisitService visitService) {
        this.doctorService = doctorService;
        this.userService = userService;
        this.visitService = visitService;
    }

    @PostMapping("/search")
    public String makeSearch(Model model,@RequestParam("query") String query) {
            model.addAttribute("doctors", doctorService.searchByFullName(query));
        return "user/doctorsPage";
    }


    @GetMapping("/doctorPage/{id}")
    public String doctorPage(@PathVariable("id") int id, Model model){
        Doctor doctor= (Doctor) doctorService.findOne(id);
        model.addAttribute("doctor", doctor);
        return "doctor/doctorPage";
    }

    @GetMapping("/clientsPage")
    public String clientsPage (Model model){
        model.addAttribute("clients",userService.findAll());
        return "doctor/clientsPage";
    }

    @GetMapping("/editVisit/{id}")
    public String editVisit(@PathVariable("id") int id ,Model model){
        model.addAttribute("visit",visitService.findOne(id));
        return "doctor/editVisit";
    }

    @PatchMapping("/visits/{id}")
    public String update(@ModelAttribute("treatmentInfo") String info,
                         @PathVariable("id") int id) {
        Visit visit= (Visit) visitService.findOne(id);
        visit.setTreatmentInfo(info);
        visitService.update(id, visit);
        return "redirect:/index";
    }

    @GetMapping("/visitsPage")
    public String visitsPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName=authentication.getName();
        Doctor doctor=doctorService.findByFullName(currentPrincipalName).get();
        model.addAttribute("visits",doctor.getVisits());
        return "doctor/visitsPage";
    }

}
