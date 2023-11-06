package com.example.jeliBankBackend.controller;

import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.model.Client;
import com.example.jeliBankBackend.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Client crateClient(Client client) throws ResourseNotFoundException {
        return this.clientService.createClient(client);
    }

    @GetMapping("/clients")
    public List<Client> getAllClients() {
        return this.clientService.getAllClients();
    }

    @GetMapping("/client/{idClient}")
    public Optional<Client> getClientById(@PathVariable Long idClient) throws ResourseNotFoundException {
        return this.clientService.getClientById(idClient);
    }
    @GetMapping("/client/{numberDocumentId}")
    public Optional<Client>  getClientByDocument(Long numberDocumentId){
        return Optional.ofNullable(this.clientService.getClientByDocument(numberDocumentId));
    }

    @PutMapping("/client/{clientNumberId}")
    public ResponseEntity<?> upDateAcount(@RequestBody Client clientNumberId) throws ResourseNotFoundException {
        clientService.upDateClient(clientNumberId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/acount/{clientNumberId}")
    public ResponseEntity<?> deleteClient(@PathVariable Long clientNumberId) throws ResourseNotFoundException{
        clientService.deleteClient(clientNumberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
