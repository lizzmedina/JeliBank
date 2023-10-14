package com.example.jeliBankBackend.service;

import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.model.Acount;
import com.example.jeliBankBackend.model.Pocket;
import com.example.jeliBankBackend.repository.PocketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

//    public Optional<Pocket> getPocketById(Long id){
//        return pocketRepository.findById(id);
//    }

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
    public Pocket upDatePocket(Pocket pocketToUpdate) throws ResourseNotFoundException {

        Optional<Pocket> pocket = pocketRepository.findById(pocketToUpdate.getPocketNumber());

        if (pocketToUpdate != null || pocket.isEmpty()){
            pocket.get().setPocketNumber(Objects.isNull(pocketToUpdate.getPocketNumber()) ?
                    pocket.get().getPocketNumber() : pocketToUpdate.getPocketNumber());

            pocket.get().setBalance(Objects.isNull(pocketToUpdate.getBalance()) ?
                    pocket.get().getBalance() : pocketToUpdate.getBalance());

            pocket.get().setBalance(Objects.isNull(pocketToUpdate.getBalance()) ?
                    pocket.get().getBalance() : pocketToUpdate.getBalance());

            pocket.get().setPoketName(Objects.isNull(pocketToUpdate.getPoketName()) ?
                    pocket.get().getPoketName() : pocketToUpdate.getPoketName());

            pocketRepository.save(pocket.get());
        }else  {
            throw new ResourseNotFoundException("No existe o no fue posible actualizar el bolsillo ingresado");
        }
        return pocketRepository.save(pocketToUpdate);
    }

    public String deletePocket(Long PocketNumber) throws ResourseNotFoundException {
        if (pocketRepository.findById(PocketNumber).isPresent()){
            pocketRepository.deleteById(PocketNumber);
            return "Bolsillo eliminado exitosamente";
        }else  {
            throw new ResourseNotFoundException("No existe o no fue posible eliminar el bolsillo, por favor revise los datos ingresados e intente nuevamnete");
        }
    }
}
