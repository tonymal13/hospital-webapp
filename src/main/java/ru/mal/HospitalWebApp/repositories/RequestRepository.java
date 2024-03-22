package ru.mal.HospitalWebApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mal.HospitalWebApp.models.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request,Integer> {

}
