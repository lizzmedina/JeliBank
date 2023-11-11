package com.example.jeliBankBackend.service;

import com.example.jeliBankBackend.dtos.requests.AccountRequestDto;
import com.example.jeliBankBackend.dtos.requests.AccountBlockRequestDto;
import com.example.jeliBankBackend.dtos.requests.AccountDepositeRequestDto;
import com.example.jeliBankBackend.dtos.requests.AccountTransferRequestDto;
import com.example.jeliBankBackend.dtos.responses.*;
import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.model.Account;
import com.example.jeliBankBackend.repository.AccountRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.Random;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


// 1- apertura de  cuenta
    public AccountResponseDto createAccount( AccountRequestDto accountRequestDto) throws ResourseNotFoundException {
        try {
            int accountNumber = generateAccountNumber();
            if (accountRepository.getAccountByAccountNumber(accountNumber).isPresent()) {
                throw new ResourseNotFoundException("Ya existe una cuenta con el número de cuenta proporcionado.");
            }

            String ownerName = accountRequestDto.getOwnerName();
            double balance = accountRequestDto.getInitialAmount();


            Account account = new Account( accountNumber, ownerName, balance);

            accountRepository.save(account);

            AccountResponseDto responseDto = new AccountResponseDto( ownerName, balance);

            return responseDto;
        } catch (DataAccessException e) {
            throw new ResourseNotFoundException("Error al crear la cuenta: " + e.getMessage());
        }
    }

// 2- deposito en cuenta
    public AccountDepositeResponseDto depositIntoAccount(int accountNumber, AccountDepositeRequestDto amount) throws ResourseNotFoundException {

        try {
            validateDepositInput(accountNumber, amount.getAmountToDeposite());
            Optional<Account> optionalAccount = accountRepository.getAccountByAccountNumber(accountNumber);

            if (optionalAccount.isPresent()) {

                Account account = optionalAccount.get();
                double currentBalance = account.getBalance();

                double newBalance = currentBalance + amount.getAmountToDeposite();

                account.setBalance(newBalance);
                accountRepository.save(account);
                return new AccountDepositeResponseDto(newBalance);
            } else {
                throw new ResourseNotFoundException("Cuenta no encontrada");
            }
        } catch (DataAccessException e) {
            throw new ResourseNotFoundException("Error al actualizar el saldo de la cuenta: " + e.getMessage());
        }
    }


    // 3- transferencia entre cuentas
    public AccountTransferResponseDto transferBetweenAccounts(AccountTransferRequestDto transferRequest) throws ResourseNotFoundException {
        int sourceAccountNumber = transferRequest.getSourceAccountNumber();
        int destinationAccountNumber = transferRequest.getDestinationAccountNumber();
        double amount = transferRequest.getAmountToTransfer();

        Optional<Account> optionalSourceAccount = accountRepository.getAccountByAccountNumber(sourceAccountNumber);
        Optional<Account> optionalDestinationAccount = accountRepository.getAccountByAccountNumber(destinationAccountNumber);

        if (optionalSourceAccount.isPresent() && optionalDestinationAccount.isPresent()) {
            Account sourceAccount = optionalSourceAccount.get();
            Account destinationAccount = optionalDestinationAccount.get();

            if (sourceAccount.getBalance() >= amount) {
                sourceAccount.setBalance(sourceAccount.getBalance() - amount);
                destinationAccount.setBalance(destinationAccount.getBalance() + amount);

                accountRepository.save(sourceAccount);
                accountRepository.save(destinationAccount);

                return new AccountTransferResponseDto(sourceAccountNumber, destinationAccountNumber, amount);
            } else {
                throw new ResourseNotFoundException("Fondos insuficientes en la cuenta de origen");
            }
        } else {
            throw new ResourseNotFoundException("Cuenta de origen o destino no encontrada");
        }
    }

    // 4- consultar cuenta
    public Optional<AccountGetResponseDto> getAccountDetails(int accountNumber) throws ResourseNotFoundException {

        try {
            Optional<Account> optionalAccount = accountRepository.getAccountByAccountNumber(accountNumber);

            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                AccountRequestDto accountRequestDto = new AccountRequestDto(
                        account.getOwnerName(),
                        account.getBalance()
                );

                AccountGetResponseDto response = new AccountGetResponseDto(
                        accountNumber,
                        accountRequestDto.getOwnerName(),
                        accountRequestDto.getInitialAmount());

                return Optional.of(response);
            } else {
                return Optional.empty();
            }
        } catch (DataAccessException e) {
            throw new ResourseNotFoundException("Error al buscar la cuenta: " + e.getMessage());
        }
    }

    // 5- bloquear cuenta
    public AccountBlockResponseDto toggleAccountStatus(AccountBlockRequestDto requestDto) throws ResourseNotFoundException {
        try {
            Optional<Account> optionalAccount = accountRepository.getAccountByAccountNumber(requestDto.getAccountNumber());

            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                account.setIsActive(!account.getIsActive());
                accountRepository.save(account);
                String message = account.getIsActive() ? "Cuenta activada" : "Cuenta desactivada";
                AccountBlockResponseDto statusMessage = new AccountBlockResponseDto(message);
                return statusMessage;
            } else {
                throw new ResourseNotFoundException("Cuenta no encontrada");
            }
        } catch (DataAccessException e) {
            throw new ResourseNotFoundException("Error al actualizar el estado de la cuenta: " + e.getMessage());
        }
    }

    // otros metodos ------------------------------------------------------------------------------------------------

    // cambiar una cuenta de tipo AccountRequestDto a entidad cuenta
    public Account AccountRequestDtotoEntity(AccountRequestDto accountRequestDto) {
        Account account = new Account(
                accountRequestDto.getOwnerName(),
                accountRequestDto.getInitialAmount());
        return account;
    }

    //cambiar una cuenta de tipo AccountResponseGetDto a entidad cuenta
    public Account AccountResponseGetDtotoEntity(AccountGetResponseDto accountResponseDto) {
        Account account = new Account(
                accountResponseDto.getAccountNumber(),
                accountResponseDto.getOwnerName(),
                accountResponseDto.getBalance());
        return account;
    }

    // cambiar una entidad cuenta a dto del tipo AccountRequestDto
    public AccountRequestDto toDto(Account createdAccount) {
        AccountRequestDto accountRequestDto = new AccountRequestDto(
                 createdAccount.getOwnerName(), createdAccount.getBalance());
        return accountRequestDto;
    }

    // generar numero de cuenta aleatoriamente
    public int generateAccountNumber() {
        Random random = new Random();
        int accountNumberInt = 1000000000 + random.nextInt(900000000);
        return accountNumberInt;
    }

    // modificar el saldo de cuenta
    public void updateAccountBalance(int accountNumber, double newBalance) throws ResourseNotFoundException {
        try {
            Optional<Account> optionalAccount = accountRepository.getAccountByAccountNumber(accountNumber);

            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                account.setBalance(newBalance);
                accountRepository.save(account);
            } else {
                throw new ResourseNotFoundException("Cuenta no encontrada");
            }
        } catch (DataAccessException e) {
            throw new ResourseNotFoundException("Error al actualizar el saldo de la cuenta: " + e.getMessage());
        }
    }

    //validar valores del deposito
    private void validateDepositInput(int accountNumber, double amountToDeposit) {
        if (accountNumber <= 0 || amountToDeposit <= 0) {
            throw new IllegalArgumentException("Número de cuenta y monto a depositar deben ser mayores que cero.");
        }
    }


//    public String deleteAcount(Long acountNumber) throws ResourseNotFoundException {
//        if (acountRepository.findById(acountNumber).isPresent()){
//            try {
//                acountRepository.deleteById(acountNumber);
//                return "Cuenta eliminada exitosamente";
//            } catch (DataAccessException e) {
//                throw new ResourseNotFoundException("Error al eliminar la cuenta: " + e.getMessage());
//            }
//        }else  {
//            throw new ResourseNotFoundException("No existe o no fue posible eliminar la cuenta, por favor revise los datos ingresados e intente nuevamnete");
//        }
//    }
}
