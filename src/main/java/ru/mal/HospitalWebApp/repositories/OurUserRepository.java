package ru.mal.HospitalWebApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mal.HospitalWebApp.models.OurUser;

import java.util.Optional;

@Repository
public interface OurUserRepository extends JpaRepository<OurUser,Integer> {
    Optional<OurUser> findByFullName(String fullName);

    Optional<OurUser> findByPhoneNumber(String username);

}
