package com.example.jeliBankBackend.service;

import com.example.jeliBankBackend.model.Acount;
import com.example.jeliBankBackend.model.Pocket;
import com.example.jeliBankBackend.repository.PocketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PocketService {

    private PocketRepository pocketRepository;

    @Autowired
    public PocketService(PocketRepository pocketRepository){
        this.pocketRepository = pocketRepository;
    }

    public Pocket createPocket(Pocket pocket){
        return pocketRepository.save(pocket);
    }

    public List<Pocket> getAllPokets(){
        return pocketRepository.findAll();
    }

    public Optional<Pocket> getPocketById(Long id){
        return pocketRepository.findById(id);
    }

    public Pocket getPoketByNumber(Long pocketNumber){
        if (pocketNumber <= 0){
            throw new IllegalArgumentException("El número de bolsillo no es válido");
        }
        Optional<Pocket> pocketOptional = this.pocketRepository.findById(pocketNumber);
        if (pocketOptional.isPresent()){
            return pocketOptional.get();
        }
        throw new RuntimeException("No hay ningun bolsillo para el número ingresado");
    }
}
