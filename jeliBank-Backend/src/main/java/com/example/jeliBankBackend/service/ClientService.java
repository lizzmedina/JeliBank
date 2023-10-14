package com.example.jeliBankBackend.service;

import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.model.Acount;
import com.example.jeliBankBackend.model.Client;
import com.example.jeliBankBackend.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

    public Optional<Client> getClientById(Long idClient){
        return clientRepository.findById(idClient);
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

    public Client upDateClient(Client clientToUpdate) throws ResourseNotFoundException {

        Optional<Client> client = clientRepository.findById(clientToUpdate.getNumberDocumentId());

        if (clientToUpdate != null || client.isEmpty()){
            client.get().setName(Objects.isNull(clientToUpdate.getName()) ?
                    client.get().getName() : clientToUpdate.getName());

            client.get().setLastName(Objects.isNull(clientToUpdate.getLastName()) ?
                    client.get().getLastName() : clientToUpdate.getLastName());

            client.get().setNumberDocumentId(Objects.isNull(clientToUpdate.getNumberDocumentId()) ?
                    client.get().getNumberDocumentId() : clientToUpdate.getNumberDocumentId());

            clientRepository.save(client.get());
        }else  {
            throw new ResourseNotFoundException("No existe o no fue posible actualizar la cuenta ingresada");
        }
        return clientRepository.save(clientToUpdate);
    }

    public String deleteClient(Long clientNumberId) throws ResourseNotFoundException {
        if (clientRepository.findById(clientNumberId).isPresent()){
            clientRepository.deleteById(clientNumberId);
            return "Cliente eliminado exitosamente";
        }else  {
            throw new ResourseNotFoundException("No existe o no fue posible eliminar el cliente, por favor revise los datos ingresados e intente nuevamnete");
        }
    }
}
