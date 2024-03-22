package ru.mal.HospitalWebApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mal.HospitalWebApp.models.Doctor;
import ru.mal.HospitalWebApp.models.OurUser;
import ru.mal.HospitalWebApp.repositories.OurUserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final OurUserRepository ourUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(OurUserRepository ourUserRepository, PasswordEncoder passwordEncoder) {
        this.ourUserRepository = ourUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(OurUser user){
        enrich(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        ourUserRepository.save(user);
    }

    public Optional<OurUser> findBuFullName(String fullName){
        return ourUserRepository.findByFullName(fullName);
    }

    public void enrich(OurUser user){
        user.setRole("ROLE_USER");
    }

    public List<OurUser> findAll(){
        return ourUserRepository.findAll();
    }

    public Object findOne(int id) {
        return ourUserRepository.findById(id).orElse(null);
    }

    public void save(OurUser user) {
        ourUserRepository.save(user);
    }

    public Optional<OurUser> getPersonByPhoneNumber(String fullName) {
        return ourUserRepository.findByPhoneNumber(fullName);
    }
}
