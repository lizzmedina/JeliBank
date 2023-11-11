package com.example.jeliBankBackend.service;

import com.example.jeliBankBackend.dtos.requests.PocketGetRequestDto;
import com.example.jeliBankBackend.dtos.requests.PocketRequestDto;
import com.example.jeliBankBackend.dtos.requests.PocketDepositeRequestDto;
import com.example.jeliBankBackend.dtos.responses.AccountGetResponseDto;
import com.example.jeliBankBackend.dtos.responses.PocketResponseDto;
import com.example.jeliBankBackend.dtos.responses.PocketGetResponseDto;
import com.example.jeliBankBackend.dtos.responses.PocketDepositeResponseDto;
import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.model.Account;
import com.example.jeliBankBackend.model.Pocket;
import com.example.jeliBankBackend.repository.AccountRepository;
import com.example.jeliBankBackend.repository.PocketRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PocketService {
    private final PocketRepository pocketRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    public PocketService(PocketRepository pocketRepository, AccountRepository accountRepository, AccountService accountService) {
        this.pocketRepository = pocketRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    // 1- crear bolsillo
    public PocketResponseDto createPocket(PocketRequestDto requestDto) throws ResourseNotFoundException {


        int accountNumber = requestDto.getAccountNumber();

        try {
            Optional<AccountGetResponseDto> accountOptional = accountService.getAccountDetails(accountNumber);

            if (accountOptional.isPresent()) {
                AccountGetResponseDto accountDto = accountOptional.get();
                Account account = accountService.AccountResponseGetDtotoEntity(accountDto);

                if (account.getAccountNumber() == 0) {
                    throw new ResourseNotFoundException("La cuenta asociada al bolsillo no existe");
                }

                double requestedBalance = requestDto.getBalance();

                if (accountDto.getBalance() >= requestedBalance) {

                    accountService.updateAccountBalance(accountNumber, accountDto.getBalance() - requestedBalance);

                    Pocket pocket = new Pocket();
                    pocket.setAccount(account);
                    pocket.setName(requestDto.getName());
                    pocket.setBalance(requestedBalance);

                    pocketRepository.save(pocket);

                    return new PocketResponseDto(accountNumber, pocket.getName(), pocket.getBalance());
                } else {
                    throw new ResourseNotFoundException("Saldo insuficiente en la cuenta principal");
                }
            } else {
                throw new ResourseNotFoundException("Cuenta no encontrada");
            }
        } catch (DataAccessException e) {
            throw new ResourseNotFoundException("Error al crear el bolsillo: " + e.getMessage());
        }
    }
    // 2 - transferir a bolsillos
    public PocketDepositeResponseDto transferToPocket(PocketDepositeRequestDto infoPocket) throws ResourseNotFoundException {
        try {
            Optional<Pocket> optionalPocket = pocketRepository.findById(infoPocket.getPocketNumber());
            Optional<Account> optionalAccount = accountRepository.getAccountByAccountNumber(infoPocket.getAccountNumber());

            Account account = optionalAccount.get();         // tengo la cuenta
            Pocket pocket = optionalPocket.get();            // tengo el bolsillo
            //-----
            double amountToTransfer = infoPocket.getAmount(); // valor a transferir -> 20
            //-----
            int accountNumber = account.getAccountNumber(); // numero de la cuenta
            //-----
            int pocketNumber = infoPocket.getPocketNumber(); // numero del bolsillo
            //-----
            double currentAccountBalance = account.getBalance();    // saldo cuenta actual -> 100
            //-----
            double currentPocketBalance = pocket.getBalance(); // saldo del bolsillo actual -> 50
            //-----
            double newPocketBalance = currentPocketBalance + amountToTransfer; // nuevo saldo bolsillo -> 50 + 20
            double newAccounBalance = currentAccountBalance - amountToTransfer;// nuevo saldo cuenta -> 100 - 20
            //-----
            if (currentAccountBalance >= amountToTransfer) {
                accountService.updateAccountBalance(accountNumber, newAccounBalance); // actualizo el saldo de la cuenta
                pocket.setBalance(newPocketBalance); // actualizo el saldo del bolsillo
                pocketRepository.save(pocket); // guardo el bolsillo
            }
            return new PocketDepositeResponseDto(accountNumber, pocketNumber, amountToTransfer);
        } catch (DataAccessException e) {
            throw new ResourseNotFoundException("Error al transferir el dinero al bolsillo: " + e.getMessage());
        }
    }

    // 3 - consultar bolsillos (traer lista de bolsillos por cuenta
    public List<PocketGetResponseDto> getPocketsByAccount(int accountNumber) throws ResourseNotFoundException {
        System.out.println("account number from  service" + accountNumber);
        try {
            Optional<Account> optionalAccount = accountRepository.getAccountByAccountNumber(accountNumber);

            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                List<Pocket> pockets = account.getPockets();
                System.out.println("pokets from service 1 " + pockets);
                List<PocketGetResponseDto> pocketGetResponseDtos = pockets.stream()
                        .map(pocket -> new PocketGetResponseDto(pocket.getName(), pocket.getPocketNumber(), pocket.getBalance()))
                        .collect(Collectors.toList());
                System.out.println("pokets from service 1 " + pocketGetResponseDtos);
                return pocketGetResponseDtos;

            } else {
                throw new ResourseNotFoundException("Cuenta no encontrada");
            }
        } catch (DataAccessException e) {
            throw new ResourseNotFoundException("Error al obtener los bolsillos de la cuenta: " + e.getMessage());
        }
    }
}

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

