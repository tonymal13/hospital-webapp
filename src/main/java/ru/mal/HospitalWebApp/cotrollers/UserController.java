package ru.mal.HospitalWebApp.cotrollers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mal.HospitalWebApp.models.*;
import ru.mal.HospitalWebApp.services.*;
import ru.mal.HospitalWebApp.util.UserValidator;
import ru.mal.HospitalWebApp.util.VisitValidator;

import java.time.LocalDate;
import java.util.*;


@Controller
@RequestMapping("/user")
public class UserController {


    private final ServicesService servicesService;


    private final UserService userService;

    private final DoctorService doctorService;

    private final VisitService visitService;

    private final AdminService adminService;

    private final RequestService requestService;

    private final UserValidator userValidator;

    private final VisitValidator visitValidator;

    @Autowired
    public UserController(ServicesService servicesService, DoctorService doctorService, UserService userService, VisitService visitService, AdminService adminService, RequestService requestService, UserValidator userValidator, VisitValidator visitValidator) {
        this.servicesService = servicesService;
        this.doctorService = doctorService;
        this.userService = userService;
        this.visitService = visitService;
        this.adminService = adminService;
        this.requestService = requestService;
        this.userValidator = userValidator;
        this.visitValidator = visitValidator;
    }

    @GetMapping("/login")
    public String login(@ModelAttribute("user") OurUser user){
        return "user/login";
    }

    public Authentication getAuth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getPrincipalName(){
        return getAuth().getName();
    }

    public void addShow(Model model){
        String currentPrincipalName=getPrincipalName();

        boolean show=false;

        Optional<OurUser> user= userService.findBuFullName(currentPrincipalName);

        if(user.isEmpty()){
            Optional<Doctor> d=doctorService.findByFullName(currentPrincipalName);
            if(d.isEmpty()){
                Optional<Admin> admin=adminService.findByFullName(currentPrincipalName);
                if(admin.isPresent()){
                    show=true;
                }

            }
        }
        model.addAttribute("show",show);
    }

    @GetMapping("/servicesPage")
    public String servicesPage(Model model,@ModelAttribute("service") Service service){
        addShow(model);

        model.addAttribute("services",servicesService.findAll());
        return "user/servicesPage";
    }

    @PostMapping("/registration")
    public String performRegistration (@ModelAttribute("user") @Valid OurUser user,BindingResult bindingResult){
        userValidator.validate(user, bindingResult);
        if(bindingResult.hasErrors()) {
            return "user/login";
        }
        userService.register(user);

        return "redirect:/index";
    }


    @GetMapping("/doctorsPage")
    public String doctorsPage(@ModelAttribute("doctor") Doctor doctor ,Model model){
        addShow(model);
        model.addAttribute("doctors",doctorService.findAll());
        return "user/doctorsPage";
    }


    @GetMapping("/createVisit")
    public String createVisit(@ModelAttribute("visit") Visit visit,
                              @ModelAttribute("doctor") Doctor doctor,
                              @ModelAttribute("date") Date date,
                              @ModelAttribute("time") String time,
                              Model model){
        boolean show=true;
        String currentPrincipalName=getPrincipalName();
        Optional<OurUser> user=userService.findBuFullName(currentPrincipalName);

        if(user.isPresent()){
            if(user.get().getOutpatientCard()==null){
                show=false;
            }
        }
        model.addAttribute("show",show);
        model.addAttribute("doctors",doctorService.findAll());
        ArrayList<String> schedule=new ArrayList<>();
        for(int i=9;i<17;i++){
            schedule.add(i+":00");
        }

        model.addAttribute("freeTime", schedule);
        return "user/createVisit";
    }

    @PostMapping("/createVisit")
    public String createVisit(@ModelAttribute("visit") @Valid Visit visit,
                              BindingResult bindingResult,
                              @ModelAttribute("doctor") Doctor doctor,
                              @ModelAttribute("date") LocalDate date,Model model){
        String currentPrincipalName=getPrincipalName();



        Optional<OurUser> user=userService.findBuFullName(currentPrincipalName);
        if(user.isPresent()){
            if(user.get().getOutpatientCard()!=null){
                visit.setCard(user.get().getOutpatientCard());
            }
        }

        visit.setDoctor(doctor);
        visit.setDateOfVisit(date);
        visitValidator.validate(visit, bindingResult);
        if(bindingResult.hasErrors()) {
            model.addAttribute("show",true);
            model.addAttribute("error",true);
            model.addAttribute("doctor",doctor);
            model.addAttribute("date",date);
            model.addAttribute("doctors",doctorService.findAll());
            ArrayList<String> schedule=new ArrayList<>();
            for(int i=9;i<16;i++){
                schedule.add(i+":00");
            }
            model.addAttribute("freeTime", schedule);
            return "user/createVisit";
        }
        visitService.save(visit);

        return "redirect:/index";

    }

    @GetMapping("/personalAccount")
    public String personalAccount(Model model){
        String choice="";

        String currentPrincipalName=getPrincipalName();

        Optional<OurUser> user= userService.findBuFullName(currentPrincipalName);

        if(user.isPresent()){
            choice="u";
            model.addAttribute("choice",choice);
            model.addAttribute("user",user);
            if (user.get().getOutpatientCard() != null)
                model.addAttribute("visits", user.get().getOutpatientCard().getVisits());

            return "user/personalAccount";
        }
        else{
            Optional<Doctor> d=doctorService.findByFullName(currentPrincipalName);
            if(d.isPresent()){
                choice="d";
                model.addAttribute("choice",choice);
                model.addAttribute("doctor",d);
                return "user/personalAccount";
            }
            else{
                Optional<Admin> admin=adminService.findByFullName(currentPrincipalName);
                if(admin.isPresent()){
                    choice="a";
                    model.addAttribute("choice",choice);
                    model.addAttribute("admin",admin);
                    return "user/personalAccount";
                }
            }
        }
        model.addAttribute("choice",choice);
        return "user/personalAccount";
    }

    @GetMapping("/visits/{id}")
    public String visitsPage(@PathVariable("id") int id, Model model){
        Visit visit= (Visit) visitService.findOne(id);
        boolean show=false;
        if(visit.getContract()!=null){
            show=true;
        }
        model.addAttribute("visit", visit);
        model.addAttribute("show",show);

        String choice="";

        String currentPrincipalName=getPrincipalName();

        Optional<OurUser> user= userService.findBuFullName(currentPrincipalName);

        if(user.isPresent()){
            choice="u";
            model.addAttribute("choice",choice);
            model.addAttribute("user",user);
            if (user.get().getOutpatientCard() != null)
                model.addAttribute("visits", user.get().getOutpatientCard().getVisits());

            return "user/visitPage";
        }
        else{
            Optional<Doctor> d=doctorService.findByFullName(currentPrincipalName);
            if(d.isPresent()){
                choice="d";
                model.addAttribute("choice",choice);
                model.addAttribute("doctor",d);
                return "user/visitPage";
            }
            else{
                Optional<Admin> admin=adminService.findByFullName(currentPrincipalName);
                if(admin.isPresent()){
                    choice="a";
                    model.addAttribute("choice",choice);
                    model.addAttribute("admin",admin);
                    return "user/visitPage";
                }
                else{
                    model.addAttribute("choice",choice);
                }
            }
        }
        return "user/visitPage";
    }


    @GetMapping("/contracts/{id}")
    public String contractPage(@PathVariable("id") int id, Model model){
        Visit visit= (Visit) visitService.findOne(id);
        Contract contract=visit.getContract();
        model.addAttribute("contract",contract);
        return "user/contractPage";
    }


    @GetMapping("/client/{id}")
    public String clientPage (@PathVariable("id") int id,Model model){
        boolean show=false;
        OurUser user = (OurUser) userService.findOne(id);

        String currentPrincipalName=getPrincipalName();

        Optional<Admin> currentUser= adminService.findByFullName(currentPrincipalName);

        if(currentUser.isPresent() && (user.getOutpatientCard()!=null)) {
            show = true;
            List<Visit> visits = user.getOutpatientCard().getVisits();
            model.addAttribute("visits", visits);
        }
        Optional<Doctor> doctor=doctorService.findByFullName(currentPrincipalName);
        if(doctor.isPresent() && (user.getOutpatientCard()!=null)) {
            show = true;
            List<Visit> visits = user.getOutpatientCard().getVisits();
            model.addAttribute("visits", visits);
        }
        model.addAttribute("client", user);

        model.addAttribute("show",show);
        return "doctor/clientPage";
    }

}

