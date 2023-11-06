package com.example.jeliBankBackend.controller;

import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.model.Address;
import com.example.jeliBankBackend.model.Client;
import com.example.jeliBankBackend.service.AddressService;
import com.example.jeliBankBackend.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class AddressController {

    private final AddressService addressService;
    private final ClientService clientService;

    @Autowired
    public AddressController(AddressService addressService,  ClientService clientService){
        this.addressService = addressService;
        this.clientService = clientService;
    }

    @PostMapping("/address/{clientId}")
    public Address createAddress(@PathVariable Long clientId, @RequestBody Address address) throws ResourseNotFoundException {
        Optional<Client> clientOptional = clientService.getClientById(clientId);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            address.setClient(client);
            return addressService.createAddress(address);
        } else {
            throw new ResourseNotFoundException("El cliente con ID " + clientId + " no fue encontrado.");
        }
    }

    @GetMapping("/address")
    public List<Address> getAllAddress() throws ResourseNotFoundException {
            return addressService.getAllAddress();
        }

    @GetMapping("/address/{documentClient}")
    public List<Address> getAddressByDocumentClient(@PathVariable Long documentClient) throws ResourseNotFoundException {
        if (documentClient > 0 ){
            Client client = clientService.getClientByDocument(documentClient);
            return client.getAddresses();
        } else throw new ResourseNotFoundException("el documento no es correcto");

    }

    @PutMapping("/address/{addressId}")
    public ResponseEntity<?> upDateAddress(@PathVariable Long addressId, @RequestBody Address address) throws ResourseNotFoundException {
        addressService.upDateAddress(addressId, address);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @DeleteMapping("/address/{addressNumber}")
        public ResponseEntity<?> deletePocket(@PathVariable Long addressNumber) throws ResourseNotFoundException{
            addressService.deleteAddress(addressNumber);
            return new ResponseEntity<>(HttpStatus.OK);
        }
}
