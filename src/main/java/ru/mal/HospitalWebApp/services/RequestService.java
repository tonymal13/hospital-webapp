package ru.mal.HospitalWebApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mal.HospitalWebApp.models.Request;
import ru.mal.HospitalWebApp.repositories.RequestRepository;

import java.util.List;

@Service
public class RequestService {
    private final RequestRepository requestRepository;
    @Autowired
    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public List<Request> findAll(){
        return requestRepository.findAll();
    }

    public void save(Request request){
        requestRepository.save(request);
    }

    public Request findOne(int id) {
        return requestRepository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(Request request) {
        requestRepository.delete(request);
    }
}
