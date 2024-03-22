package ru.mal.HospitalWebApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mal.HospitalWebApp.models.Admin;
import ru.mal.HospitalWebApp.models.Doctor;
import ru.mal.HospitalWebApp.models.OurUser;
import ru.mal.HospitalWebApp.repositories.AdminRepository;
import ru.mal.HospitalWebApp.repositories.DoctorRepository;
import ru.mal.HospitalWebApp.repositories.OurUserRepository;
import ru.mal.HospitalWebApp.security.AdminDetails;
import ru.mal.HospitalWebApp.security.DoctorDetails;
import ru.mal.HospitalWebApp.security.OurUserDetails;

import java.util.Optional;

@Service
public class OurUserDetailsService implements UserDetailsService {

    private final OurUserRepository userRepository;

    private final AdminRepository adminRepository;

    private final DoctorRepository doctorRepository;

    @Autowired
    public OurUserDetailsService(OurUserRepository userRepository, AdminRepository adminRepository, DoctorRepository doctorRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.doctorRepository = doctorRepository;
    }
    

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<OurUser> user= userRepository.findByFullName(username);
//        System.out.println(username);
//        if(user.isEmpty()){
//            Optional<Admin> admin = adminRepository.findByFullName(username);
//            if(admin.isEmpty()){
//                Optional<Doctor> doctor = doctorRepository.findByFullName(username);
//                if(doctor.isEmpty()){
//                    throw new UsernameNotFoundException("User not found");
//                }
//                else{
//                    return new DoctorDetails(doctor.get());
//                }
//            }
//            else{
//                return new AdminDetails(admin.get());
//            }
//        }
//        else{
//            return new OurUserDetails(user.get());
//        }
//
//    }

@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<OurUser> user= userRepository.findByPhoneNumber(username);
    if(user.isEmpty()){
        Optional<Admin> admin = adminRepository.findByPhoneNumber(username);
        if(admin.isEmpty()){
            Optional<Doctor> doctor = doctorRepository.findByPhoneNumber(username);
            if(doctor.isEmpty()){
                throw new UsernameNotFoundException("User not found");
            }
            else{
                return new DoctorDetails(doctor.get());
            }
        }
        else{
            return new AdminDetails(admin.get());
        }
    }
    else{
        return new OurUserDetails(user.get());
    }

}
}
