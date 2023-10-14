package com.example.jeliBankBackend.service;

import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.model.Acount;
import com.example.jeliBankBackend.repository.AcountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AcountService {

    @Autowired
    private AcountRepository acountRepository;

    public Acount createAcount(Acount acount)throws ResourseNotFoundException{
        return acountRepository.save(acount);
    }

    public List<Acount> getAcounts(){
        return acountRepository.findAll();
    }

    public Optional<Acount> getAcountById(Long acountNumber) throws ResourseNotFoundException{
        return acountRepository.findById(acountNumber);
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

    public Acount upDateAcount(Acount acountToUpdate) throws ResourseNotFoundException {

        Optional<Acount> acount = acountRepository.findById(acountToUpdate.getAcountNumber());

        if (acountToUpdate != null || acount.isEmpty()){
            acount.get().setAcountType(Objects.isNull(acountToUpdate.getAcountType()) ?
                    acount.get().getAcountType() : acountToUpdate.getAcountType());

            acount.get().setBalance(Objects.isNull(acountToUpdate.getBalance()) ?
                    acount.get().getBalance() : acountToUpdate.getBalance());

            acount.get().setAcountNumber(Objects.isNull(acountToUpdate.getAcountNumber()) ?
                    acount.get().getAcountNumber() : acountToUpdate.getAcountNumber());

            acountRepository.save(acount.get());
        }else  {
            throw new ResourseNotFoundException("No existe o no fue posible actualizar la cuenta ingresada");
        }
        return acountRepository.save(acountToUpdate);
    }

    public String deleteAcount(Long acountNumber) throws ResourseNotFoundException {
        if (acountRepository.findById(acountNumber).isPresent()){
            acountRepository.deleteById(acountNumber);
            return "Cuenta eliminada exitosamente";
        }else  {
            throw new ResourseNotFoundException("No existe o no fue posible eliminar la cuenta, por favor revise los datos ingresados e intente nuevamnete");
        }
    }
}
