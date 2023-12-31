package com.example.jeliBankBackend.service;

import com.example.jeliBankBackend.dtos.requests.account.AccountGetRequestDto;
import com.example.jeliBankBackend.dtos.requests.pocket.PocketGetRequestDto;
import com.example.jeliBankBackend.dtos.requests.pocket.PocketRequestDto;
import com.example.jeliBankBackend.dtos.requests.pocket.PocketDepositeRequestDto;
import com.example.jeliBankBackend.dtos.responses.account.AccountGetResponseDto;
import com.example.jeliBankBackend.dtos.responses.pocket.PocketResponseDto;
import com.example.jeliBankBackend.dtos.responses.pocket.PocketGetResponseDto;
import com.example.jeliBankBackend.dtos.responses.pocket.PocketDepositeResponseDto;
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
            Optional<AccountGetResponseDto> accountOptional = accountService.getAccountDetails(new AccountGetRequestDto(accountNumber));

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
            if (optionalAccount.isPresent() && optionalPocket.isPresent()) {
            Account account = optionalAccount.get();
            Pocket pocket = optionalPocket.get();
            //-----
            double amountToTransfer = infoPocket.getAmount();
            //-----
            int accountNumber = account.getAccountNumber();
            //-----
            int pocketNumber = infoPocket.getPocketNumber();
            //-----
            double currentAccountBalance = account.getBalance();
            //-----
            double currentPocketBalance = pocket.getBalance();
            //-----
            double newPocketBalance = currentPocketBalance + amountToTransfer;
            double newAccounBalance = currentAccountBalance - amountToTransfer;
            //-----
            if (currentAccountBalance >= amountToTransfer) {
                accountService.updateAccountBalance(accountNumber, newAccounBalance);
                pocket.setBalance(newPocketBalance);
                pocketRepository.save(pocket);
                return new PocketDepositeResponseDto(accountNumber, pocketNumber, amountToTransfer);
            }else {
                throw new ResourseNotFoundException("el valor ingresado es mayor al saldo de la cuenta");
            }
            } else {
                throw new ResourseNotFoundException("Cuenta o bolsillo no encontrados");
            }

        } catch (DataAccessException e) {
            throw new ResourseNotFoundException("Error al transferir el dinero al bolsillo: " + e.getMessage());
        }
    }

    // 3 - consultar bolsillos (traer lista de bolsillos por cuenta
    public List<PocketGetResponseDto> getPocketsByAccount(PocketGetRequestDto accountNumber) throws ResourseNotFoundException {
        System.out.println("account number from  service" + accountNumber);

        try {
            int numberAccount = accountNumber.getAccountNumber();
            Optional<Account> optionalAccount = accountRepository.getAccountByAccountNumber(numberAccount);

            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                List<Pocket> pockets = account.getPockets();

                List<PocketGetResponseDto> pocketGetResponseDtos = pockets.stream()
                        .map(pocket -> new PocketGetResponseDto(pocket.getName(), pocket.getPocketNumber(), pocket.getBalance()))
                        .collect(Collectors.toList());

                return pocketGetResponseDtos;

            } else {
                throw new ResourseNotFoundException("Cuenta no encontrada");
            }
        } catch (DataAccessException e) {
            throw new ResourseNotFoundException("Error al obtener los bolsillos de la cuenta: " + e.getMessage());
        }
    }
}

