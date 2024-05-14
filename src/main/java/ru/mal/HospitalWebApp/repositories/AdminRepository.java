package ru.mal.HospitalWebApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mal.HospitalWebApp.models.Admin;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer> {
    Optional<Admin> findByFullName(String fullName);

    Optional<Admin> findByPhoneNumber(String username);

}
