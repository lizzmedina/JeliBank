//package com.example.jeliBankBackend.controller;
//
//import com.example.jeliBankBackend.dtos.requests.ClientRequestDto;
//import com.example.jeliBankBackend.dtos.responses.ClientResponseDto;
//import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
//import com.example.jeliBankBackend.model.Client;
//import com.example.jeliBankBackend.service.ClientService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataAccessException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("api/v1")
//public class ClientController {
//    private final ClientService clientService;
//
//    public ClientController(ClientService clientService) {
//        this.clientService = clientService;
//    }
//
//    @PostMapping("/client")
//    public ClientResponseDto crateClient(@RequestBody ClientRequestDto client) throws ResourseNotFoundException {
//        ClientResponseDto response;
//        try {
//            response = this.clientService.createClient(client);
//            return response;
//        }catch (DataAccessException e){
//            throw new ResourseNotFoundException("no se pudo crear correctamente");
//        }
//    }
//
//    @GetMapping("/clients")
//    public List<ClientRequestDto> getAllClients() throws ResourseNotFoundException {
//        return this.clientService.getAllClients();
//    }
//
//    @GetMapping("/clientId/{idClient}")
//    public ResponseEntity<ClientRequestDto> getClientById(@PathVariable Long idClient) throws ResourseNotFoundException {
//        Optional<ClientRequestDto> clientDtoOptional = this.clientService.getClientById(idClient);
//        if (clientDtoOptional.isPresent()) {
//            return ResponseEntity.ok(clientDtoOptional.get());
//        } else {
//            throw new ResourseNotFoundException("Cliente no encontrado");
//        }
//    }
//
//    @GetMapping("/client/{numberDocumentId}")
//    public Optional<Client>  getClientByDocument(@PathVariable Long numberDocumentId){
//        return Optional.ofNullable(this.clientService.getClientByDocument(numberDocumentId));
//    }
//
//    @PutMapping("/client/")
//    public ResponseEntity<ClientResponseDto> upDateAcount(@RequestBody ClientRequestDto clientRequestDtoToUpdate) throws ResourseNotFoundException {
//        ResponseEntity<ClientResponseDto> response;
//        response = ResponseEntity.ok(clientService.upDateClient(clientRequestDtoToUpdate));
//        return response;
//    }
//
//    @DeleteMapping("/client/{clientNumberId}")
//    public ResponseEntity<?> deleteClient(@PathVariable Long clientNumberId) throws ResourseNotFoundException{
//        clientService.deleteClient(clientNumberId);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//
//}
