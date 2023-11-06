package com.example.jeliBankBackend.controller;

import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.model.Client;
import com.example.jeliBankBackend.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class ClientController {
    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/client")
    public Client crateClient(@RequestBody Client client) throws ResourseNotFoundException {
        try {
            return this.clientService.createClient(client);
        }catch (DataAccessException e){
            throw new ResourseNotFoundException("no se pudo crear correctamente");
        }
    }

    @GetMapping("/clients")
    public List<Client> getAllClients() throws ResourseNotFoundException {
        return this.clientService.getAllClients();
    }

    @GetMapping("/clientId/{idClient}")
    public Optional<Client> getClientById(@PathVariable Long idClient) throws ResourseNotFoundException {
        return this.clientService.getClientById(idClient);
    }
    @GetMapping("/client/{numberDocumentId}")
    public Optional<Client>  getClientByDocument(@PathVariable Long numberDocumentId){
        return Optional.ofNullable(this.clientService.getClientByDocument(numberDocumentId));
    }

    @PutMapping("/client/")
    public ResponseEntity<?> upDateAcount(@RequestBody Client clientToUpdate) throws ResourseNotFoundException {
        clientService.upDateClient(clientToUpdate);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/client/{clientNumberId}")
    public ResponseEntity<?> deleteClient(@PathVariable Long clientNumberId) throws ResourseNotFoundException{
        clientService.deleteClient(clientNumberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
