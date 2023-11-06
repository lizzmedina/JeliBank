package com.example.jeliBankBackend.service;

import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.model.Acount;
import com.example.jeliBankBackend.model.Client;
import com.example.jeliBankBackend.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

    public Client createClient(Client client) throws ResourseNotFoundException{
        try {
            return clientRepository.save(client);
        } catch (DataAccessException e) {
            throw new ResourseNotFoundException("Error al crear el cliente: " + e.getMessage());
        }
    }

    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long idClient) throws ResourseNotFoundException{
        try {
            return clientRepository.findById(idClient);
        } catch (DataAccessException e) {
            throw new ResourseNotFoundException("Error al buscar el cliente: " + e.getMessage());
        }
    }

    public Client getClientByDocument(Long numberDocumentId){
        if (numberDocumentId <= 0){
            throw new IllegalArgumentException("El número de documento no es válido");
        }
        Optional<Client> clientOptional = this.clientRepository.findById(numberDocumentId);
        if (clientOptional.isPresent()){
            return clientOptional.get();
        }
        throw new RuntimeException("No hay ningún cliente para el número ingresado");
    }

    public Client upDateClient(Client clientToUpdate) throws ResourseNotFoundException {

        Optional<Client> client = clientRepository.findById(clientToUpdate.getNumberDocumentId());

        if (client.isPresent()) {
            try {
                client.get().setName(Objects.isNull(clientToUpdate.getName()) ?
                        client.get().getName() : clientToUpdate.getName());
                client.get().setLastName(Objects.isNull(clientToUpdate.getLastName()) ?
                        client.get().getLastName() : clientToUpdate.getLastName());
                client.get().setNumberDocumentId(Objects.isNull(clientToUpdate.getNumberDocumentId()) ?
                        client.get().getNumberDocumentId() : clientToUpdate.getNumberDocumentId());

                return clientRepository.save(client.get());
            } catch (DataAccessException e) {
                throw new ResourseNotFoundException("Error al actualizar el cliente: " + e.getMessage());
            }
        } else {
            throw new ResourseNotFoundException("No existe o no fue posible actualizar el cliente ingresado");
        }
    }

    public String deleteClient(Long clientNumberId) throws ResourseNotFoundException {
        if (clientRepository.findById(clientNumberId).isPresent()) {
            try {
                clientRepository.deleteById(clientNumberId);
                return "Cliente eliminado exitosamente";
            } catch (DataAccessException e) {
                throw new ResourseNotFoundException("Error al eliminar el cliente: " + e.getMessage());
            }
        } else {
            throw new ResourseNotFoundException("No existe o no fue posible eliminar el cliente, por favor revise los datos ingresados e intente nuevamente");
        }
    }
}
