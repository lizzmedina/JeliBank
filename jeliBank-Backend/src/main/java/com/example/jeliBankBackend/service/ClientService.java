package com.example.jeliBankBackend.service;

import com.example.jeliBankBackend.model.Acount;
import com.example.jeliBankBackend.model.Client;
import com.example.jeliBankBackend.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public Client createClient(Client client){
        return clientRepository.save(client);
    }

    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id){
        return clientRepository.findById(id);
    }

    public Client getClientByDocument(Long numberDocumentId){
        if (numberDocumentId <= 0){
            throw new IllegalArgumentException("El número de documento no es válido");
        }
        Optional<Client> clientOptional = this.clientRepository.findById(numberDocumentId);
        if (clientOptional.isPresent()){
            return clientOptional.get();
        }
        throw new RuntimeException("No hay ningun cliente para el número ingresado");
    }
}
