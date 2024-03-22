package ru.mal.HospitalWebApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mal.HospitalWebApp.repositories.ServicesRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicesService {
    private final ServicesRepository servicesRepository;

    @Autowired
    public ServicesService(ServicesRepository servicesRepository) {
        this.servicesRepository = servicesRepository;
    }
    public List<ru.mal.HospitalWebApp.models.Service> findAll(){
        return servicesRepository.findAll();
    }

    public Object findOne(int id) {
        return servicesRepository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(int id) {
        servicesRepository.delete((ru.mal.HospitalWebApp.models.Service) findOne(id));
    }

    @Transactional
    public void save(ru.mal.HospitalWebApp.models.Service service) {
        servicesRepository.save(service);
    }

    public ru.mal.HospitalWebApp.models.Service findMostExpensive() {
        List<ru.mal.HospitalWebApp.models.Service> services=servicesRepository.findAll();
        ru.mal.HospitalWebApp.models.Service mostExpService=services.get(0);
        for(int i=0;i<services.size();i++){
            if (services.get(i).getPrice()>mostExpService.getPrice()){
                mostExpService=services.get(i);
            }
        }
        return mostExpService;
    }
}
