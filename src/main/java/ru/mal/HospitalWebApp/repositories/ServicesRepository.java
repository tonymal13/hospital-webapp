package ru.mal.HospitalWebApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.mal.HospitalWebApp.models.Service;

@Repository
public interface ServicesRepository extends JpaRepository<Service,Integer> {
}
