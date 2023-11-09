package com.example.jeliBankBackend.service;

import com.example.jeliBankBackend.dtos.requests.AccountRequestDto;
import com.example.jeliBankBackend.dtos.requests.PocketRequestDto;
import com.example.jeliBankBackend.dtos.responses.AccountResponseGetDto;
import com.example.jeliBankBackend.dtos.responses.PocketResponseDto;
import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.model.Account;
import com.example.jeliBankBackend.model.Pocket;
import com.example.jeliBankBackend.repository.AccountRepository;
import com.example.jeliBankBackend.repository.PocketRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PocketService {


    private final PocketRepository pocketRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    public PocketService(PocketRepository pocketRepository, AccountRepository accountRepository, AccountService accountService){
        this.pocketRepository = pocketRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    public PocketResponseDto createPocket( PocketRequestDto requestDto) throws ResourseNotFoundException {
        int accountNumber = requestDto.getAccountNumber();
        try {
            // Obtener la cuenta asociada al número de cuenta proporcionado
            Optional<AccountResponseGetDto> accountOptional = accountService.getAccountDetails(accountNumber);

            if (accountOptional.isPresent()) {
                AccountResponseGetDto accountDto = accountOptional.get();

                // Crear y guardar la cuenta (si no existe)
                Account account = accountService.AccountResponseGetDtotoEntity(accountDto);

                // Verificar si la cuenta ya está en la base de datos
                if (account.getAccountNumber() == 0) {
                    account.setAccountNumber(accountService.generateAccountNumber()); // Generar un número de cuenta único
                    accountRepository.save(account); // Guardar la cuenta en la base de datos
                }

                // Crear el bolsillo y asociarlo a la cuenta
                Pocket pocket = new Pocket();
                pocket.setAccount(account);
                pocket.setName(requestDto.getName());
                pocket.setBalance(requestDto.getBalance());

                // Guardar el bolsillo en la base de datos
                pocketRepository.save(pocket);

                // Descontar el saldo del bolsillo del saldo de la cuenta principal
                accountService.updateAccountBalance(accountNumber, accountDto.getBalance() - requestDto.getBalance());

                return new PocketResponseDto(accountNumber, pocket.getName(), pocket.getBalance());
            } else {
                throw new ResourseNotFoundException("Cuenta no encontrada");
            }
        } catch (DataAccessException e) {
            throw new ResourseNotFoundException("Error al crear el bolsillo: " + e.getMessage());
        }
    }



//    public List<Pocket> getAllPokets(){
//        return pocketRepository.findAll();
//    }
//
//    public Optional<Pocket> getPocketById(Long id) throws ResourseNotFoundException {
//        try {
//            return pocketRepository.findById(id);
//        } catch (DataAccessException e) {
//            throw new ResourseNotFoundException("Error al buscar el bolsillo: " + e.getMessage());
//        }
//    }
//
//    public Pocket getPoketByNumber(Long pocketNumber){
//        if (pocketNumber <= 0){
//            throw new IllegalArgumentException("El número de bolsillo no es válido");
//        }
//        Optional<Pocket> pocketOptional = this.pocketRepository.findById(pocketNumber);
//        if (pocketOptional.isPresent()){
//            return pocketOptional.get();
//        }
//        throw new RuntimeException("No hay ningun bolsillo para el número ingresado");
//    }
//    public Pocket upDatePocket(Pocket pocketToUpdate) throws ResourseNotFoundException {
//
//        Optional<Pocket> pocket = pocketRepository.findById(pocketToUpdate.getPocketNumber());
//
//        if (pocket.isPresent()) {
//            try {
//                pocket.get().setPocketNumber(Objects.isNull(pocketToUpdate.getPocketNumber()) ?
//                        pocket.get().getPocketNumber() : pocketToUpdate.getPocketNumber());
//
//                pocket.get().setBalance(pocketToUpdate.getBalance());
//
//                pocket.get().setBalance(pocketToUpdate.getBalance());
//
//                pocket.get().setPoketName(Objects.isNull(pocketToUpdate.getPoketName()) ?
//                        pocket.get().getPoketName() : pocketToUpdate.getPoketName());
//
//                return pocketRepository.save(pocket.get());
//
//            } catch (DataAccessException e) {
//                throw new ResourseNotFoundException("Error al actualizar el bolsillo: " + e.getMessage());
//            }
//        } else {
//            throw new ResourseNotFoundException("No existe o no fue posible actualizar el bolsillo ingresado");
//        }
//    }
//
//    public String deletePocket(Long PocketNumber) throws ResourseNotFoundException {
//        if (pocketRepository.findById(PocketNumber).isPresent()) {
//            try {
//                pocketRepository.deleteById(PocketNumber);
//                return "Bolsillo eliminado exitosamente";
//            } catch (DataAccessException e) {
//                throw new ResourseNotFoundException("Error al eliminar el bolsillo: " + e.getMessage());
//            }
//        } else {
//            throw new ResourseNotFoundException("No existe o no fue posible eliminar el bolsillo, por favor revise los datos ingresados e intente nuevamente");
//        }
//    }
}
