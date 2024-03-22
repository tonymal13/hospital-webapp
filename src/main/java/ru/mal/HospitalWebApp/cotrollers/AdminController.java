package ru.mal.HospitalWebApp.cotrollers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mal.HospitalWebApp.models.*;
import ru.mal.HospitalWebApp.services.*;
import ru.mal.HospitalWebApp.util.DoctorValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    private final VisitService visitService;

    private final ServicesService servicesService;

    private final DoctorService doctorService;

    private final RequestService requestService;

    private final DoctorValidator doctorValidator;

    @Autowired
    public AdminController(UserService userService, VisitService visitService, ServicesService servicesService, DoctorService doctorService, RequestService requestService, DoctorValidator doctorValidator) {
        this.userService = userService;
        this.visitService = visitService;
        this.servicesService = servicesService;
        this.doctorService = doctorService;
        this.requestService = requestService;
        this.doctorValidator = doctorValidator;
    }

    @GetMapping("/createCard/{id}")
    public String  cardPage(@PathVariable("id") int id, Model model){
        model.addAttribute("userId",id);
        return "admin/createOutpatientCard";
    }

    @PostMapping("/createCard/{id}")
    public String createCard(@PathVariable("id") int id,@ModelAttribute("cardId") int cardId){

        OurUser user= (OurUser) userService.findOne(id);

        List<Visit> visits=new ArrayList<>();

        OutpatientCard card=new OutpatientCard(user, visits, cardId);

        user.setOutpatientCard(card);

        userService.save(user);

        return "redirect:/index";
    }

    @GetMapping("/createContract/{id}")
    public String contractPage(@PathVariable("id") int id, Model model,
                               @ModelAttribute("contract")Contract contract){
        model.addAttribute("visit",visitService.findOne(id));
        return "admin/createContract";
    }

    @PostMapping("/createContract/{id}")
    public String createContract(@PathVariable("id") int id,@ModelAttribute("contract") Contract contract){

        Visit visit= ((Visit) visitService.findOne(id));

        contract.setVisit(visit);

        visit.setContract(contract);

        visitService.save(visit);

        return "redirect:/index";
    }

    @DeleteMapping("/deleteDoctor")
    public String deleteDoctor(@ModelAttribute("doctor") Doctor doctor){
        doctorService.delete(doctor);
        return "redirect:/index";
    }

    @GetMapping("/editDoctor")
    public String editService(@ModelAttribute("doctor") Doctor doctor,Model model){
        model.addAttribute("doctor",doctor);
        return "admin/doctor/editDoctor";
    }

    @PatchMapping("/editDoctor")
    public String editDoctor(@ModelAttribute("doctor") @Valid Doctor doctor,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "admin/doctor/editDoctor";
        }
        doctor.setRole("ROLE_DOCTOR");
        doctorService.update(doctor);
        return "redirect:/index";
    }

    @GetMapping("/createDoctor")
    public String createDoctorPage(@ModelAttribute("doctor") Doctor doctor){
        return "admin/doctor/createDoctor";
    }

    @PostMapping("/createDoctor")
    public String createDoctor(@ModelAttribute("doctor") @Valid Doctor doctor, BindingResult bindingResult){
        doctorValidator.validate(doctor, bindingResult);
        if(bindingResult.hasErrors()) {
            return "admin/doctor/createDoctor";
        }
        doctor.setRole("ROLE_DOCTOR");
        doctorService.save(doctor);
        return "redirect:/index";
    }

    @DeleteMapping("/deleteService")
    public String deleteService(@ModelAttribute("service") Service service){
        servicesService.delete(service.getId());
        return "redirect:/index";
    }

    @GetMapping("/editService")
    public String editService(@ModelAttribute("service") Service service,Model model){
        model.addAttribute("service",service);
        return "admin/service/editService";
    }

    @PatchMapping("/editService")
    public String editService(@ModelAttribute("service") Service service){
        servicesService.save(service);
        return "redirect:/index";
    }

    @GetMapping("/createService")
    public String createServicePage(@ModelAttribute("service") Service service){
        return "admin/service/createService";
    }

    @PostMapping("/createService")
    public String createService(@ModelAttribute("service") Service service){
        servicesService.save(service);
        return "redirect:/index";
    }

    @GetMapping("/showStat")
    public String showStat(Model model){
        List<Doctor> doctors= doctorService.findAll();
        Doctor doctor=findMostPopular(doctors);
        model.addAttribute("doctor",doctor);
        model.addAttribute("numberOfUsers",userService.findAll().size());
        model.addAttribute("expensiveService",servicesService.findMostExpensive().getTitle());
        return "admin/showStat";
    }

    private Doctor findMostPopular(List<Doctor> doctors) {
        Doctor doctor=doctors.get(0);
        for (int i=0;i<doctors.size();i++){
            if (doctors.get(i).getNumberOfVisit()>doctor.getNumberOfVisit()){
                doctor=doctors.get(i);
            }
        }
        return doctor;
    }

}
