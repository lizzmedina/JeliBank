package com.example.jeliBankBackend.service;

import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.model.Address;
import com.example.jeliBankBackend.model.Client;
import com.example.jeliBankBackend.repository.AddressRepository;
import com.example.jeliBankBackend.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    //private final AddressRepository addressRepository;

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

    public List<Client> getAllClients() throws ResourseNotFoundException {
        try {
            return clientRepository.findAll();
        }catch (DataAccessException e){
            throw new ResourseNotFoundException("no hay clientes en la lista aún");
        }

    }

    public Optional<Client> getClientById(Long idClient) throws ResourseNotFoundException{
        if (idClient !=null && idClient >0){
            try {
                return clientRepository.findById(idClient);
            } catch (DataAccessException e) {
                throw new ResourseNotFoundException("Error al buscar el cliente: " + e.getMessage());
            }
        }else throw new  IllegalArgumentException("no es valido el id ingresadod");

    }

    public Client getClientByDocument(Long numberDocumentId){
        if (numberDocumentId <= 0){
            throw new IllegalArgumentException("El número de documento no es válido");
        }
        Optional<Client> clientOptional = this.clientRepository.findByNumberDocumentId(numberDocumentId);
        if (clientOptional.isPresent()){
            return clientOptional.get();
        }
        throw new RuntimeException("No hay ningún cliente para el número ingresado");
    }

    public Client upDateClient(Client clientToUpdate) throws ResourseNotFoundException {

        Optional<Client> client = getClientById(clientToUpdate.getClient_id());

        if (client.isPresent()) {
            try {
                client.get().setName(Objects.isNull(clientToUpdate.getName()) ?
                        client.get().getName() : clientToUpdate.getName());
                client.get().setLastName(Objects.isNull(clientToUpdate.getLastName()) ?
                        client.get().getLastName() : clientToUpdate.getLastName());
                client.get().setNumberDocumentId(Objects.isNull(clientToUpdate.getNumberDocumentId()) ?
                        client.get().getNumberDocumentId() : clientToUpdate.getNumberDocumentId());
                client.get().setClient_id(Objects.isNull(clientToUpdate.getClient_id()) ?
                        client.get().getClient_id() : clientToUpdate.getClient_id());

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
