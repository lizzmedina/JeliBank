package com.example.jeliBankBackend.service;

import com.example.jeliBankBackend.dtos.requests.AccountRequestDto;
import com.example.jeliBankBackend.dtos.requests.AccountStatusRequestDto;
import com.example.jeliBankBackend.dtos.requests.AccountTransferRequestDto;
import com.example.jeliBankBackend.dtos.requests.AccountTransferToAccountRequestDto;
import com.example.jeliBankBackend.dtos.responses.AccountResponseDto;
import com.example.jeliBankBackend.dtos.responses.AccountResponseTransferDto;
import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.model.Account;
import com.example.jeliBankBackend.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountRequestDto toDto(Account createdAccount) {
        AccountRequestDto accountRequestDto = new AccountRequestDto(
                createdAccount.getOwnerName(), createdAccount.getBalance());
        return accountRequestDto;
    }

    public Account toEntity(AccountRequestDto accountRequestDto) {
        Account account = new Account(
                accountRequestDto.getOwnerName(),
                accountRequestDto.getBalance());
        return account;
    }

    private int generateAccountNumber() {
        Random random = new Random();
        int accountNumberInt = 1000000000 + random.nextInt(900000000);
        return accountNumberInt;
    }

    public AccountResponseDto createAccount(AccountRequestDto accountRequestDto) throws ResourseNotFoundException {
        try {
            int accountNumber = generateAccountNumber();
            if (accountRepository.getAccountByAccountNumber(accountNumber).isPresent()) {
                throw new ResourseNotFoundException("Ya existe una cuenta con el número de cuenta proporcionado.");
            }

            String ownerName = accountRequestDto.getOwnerName();
            double balance = accountRequestDto.getBalance();


            Account account = new Account(accountNumber, ownerName, balance);
            accountRepository.save(account);

            AccountResponseDto responseDto = new AccountResponseDto(ownerName, balance);

            return responseDto;
        } catch (DataAccessException e) {
            throw new ResourseNotFoundException("Error al crear la cuenta: " + e.getMessage());
        }
    }

    public Optional<AccountRequestDto> getAcountByNumber(int acountNumber) throws ResourseNotFoundException {
        try {
            Optional<Account> accountOptional = accountRepository.getAccountByAccountNumber(acountNumber);
            if (accountOptional.isPresent()) {
                Account account = accountOptional.get();
                AccountRequestDto accountRequestDto = new AccountRequestDto(
                        account.getOwnerName(),
                        account.getBalance()
                );
                return Optional.of(accountRequestDto);
            } else {
                return Optional.empty();
            }
        } catch (DataAccessException e) {
            throw new ResourseNotFoundException("Error al buscar la cuenta: " + e.getMessage());
        }
    }

    public double depositIntoAccount(int accountNumber, double amountToDeposit) throws ResourseNotFoundException {
        try {
            Optional<Account> optionalAccount = accountRepository.getAccountByAccountNumber(accountNumber);
            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                double currentBalance = account.getBalance();

                if (currentBalance >= amountToDeposit) {

                    double newBalance = currentBalance + amountToDeposit;

                    account.setBalance(newBalance);
                    accountRepository.save(account);

                    return newBalance;

                } else {
                    throw new ResourseNotFoundException("Saldo insuficiente en la cuenta");
                }
            } else {
                throw new ResourseNotFoundException("Cuenta no encontrada");
            }
        } catch (DataAccessException e) {
            throw new ResourseNotFoundException("Error al actualizar el saldo de la cuenta: " + e.getMessage());
        }
    }

    public AccountTransferToAccountRequestDto transferBetweenAccounts(AccountTransferToAccountRequestDto transferRequest) throws ResourseNotFoundException {
        int sourceAccountNumber = transferRequest.getSourceAccountNumber();
        int destinationAccountNumber = transferRequest.getDestinationAccountNumber();
        double amount = transferRequest.getAmount();

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

                return new AccountTransferToAccountRequestDto(sourceAccountNumber, destinationAccountNumber, amount);
            } else {
                throw new ResourseNotFoundException("Fondos insuficientes en la cuenta de origen");
            }
        } else {
            throw new ResourseNotFoundException("Cuenta de origen o destino no encontrada");
        }
    }

    public String toggleAccountStatus(AccountStatusRequestDto requestDto) throws ResourseNotFoundException {
        try {
            Optional<Account> optionalAccount = accountRepository.getAccountByAccountNumber(requestDto.getAccountNumber());

            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                account.setIsActive(!account.getIsActive());
                accountRepository.save(account);

                String statusMessage = account.getIsActive() ? "Cuenta activada" : "Cuenta desactivada";
                return statusMessage;
            } else {
                throw new ResourseNotFoundException("Cuenta no encontrada");
            }
        } catch (DataAccessException e) {
            throw new ResourseNotFoundException("Error al actualizar el estado de la cuenta: " + e.getMessage());
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
