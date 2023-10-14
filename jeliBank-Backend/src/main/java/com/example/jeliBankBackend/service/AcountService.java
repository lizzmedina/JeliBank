package com.example.jeliBankBackend.service;

import com.example.jeliBankBackend.model.Acount;
import com.example.jeliBankBackend.repository.AcountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AcountService {

    @Autowired
    private AcountRepository acountRepository;

    public Acount createAcount(Acount acount){
        return acountRepository.save(acount);
    }

    public List<Acount> getAcounts(){
        return acountRepository.findAll();
    }

    public Optional<Acount> getAcountById(Long id){
        return acountRepository.findById(id);
    }

    public Acount getAcountByNumber(Long acountNumber){
        if (acountNumber <= 0){
            throw new IllegalArgumentException("El número de cuenta no es válido");
        }
        Optional<Acount> acountOptional = this.acountRepository.findById(acountNumber);
        if (acountOptional.isPresent()){
            return acountOptional.get();
        }
        throw new RuntimeException("No hay ninguna cuenta para el número ingresado");
    }
}
