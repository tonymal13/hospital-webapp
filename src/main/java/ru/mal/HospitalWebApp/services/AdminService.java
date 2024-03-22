package ru.mal.HospitalWebApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mal.HospitalWebApp.models.Admin;
import ru.mal.HospitalWebApp.repositories.AdminRepository;

import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Transactional
    public Optional<Admin> findByFullName(String currentPrincipalName) {
        return adminRepository.findByFullName(currentPrincipalName);
    }
}
