package com.example.jeliBankBackend.controller;

import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.model.Address;
import com.example.jeliBankBackend.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class AddressController {

        private AddressService addressService;

        @Autowired
        public AddressController(AddressService addressService){
            this.addressService = addressService;
        }

        @PostMapping("/address")
        public Address createTransfer(Address address){
            return addressService.createAddress(address);
        }

        @GetMapping("/address")
        public List<Address> getAllAddress(){
            return addressService.getAllAddress();
        }

        @GetMapping("/address/{documentClient}")
        public Optional<Address> getAddressByDocumentClient(Long documentClient) throws ResourseNotFoundException {
            return this.addressService.getAddressByClient(documentClient);
        }

        @PutMapping("/address/{addressToDelete}")
        public ResponseEntity<?> upDateAddress(@RequestBody Address addressToDelete) throws ResourseNotFoundException {
            addressService.upDateAddress(addressToDelete);
            return ResponseEntity.ok(HttpStatus.OK);
        }

        @DeleteMapping("/address/{addressNumber}")
        public ResponseEntity<?> deletePocket(@PathVariable Long addressNumber) throws ResourseNotFoundException{
            addressService.deleteAddress(addressNumber);
            return new ResponseEntity<>(HttpStatus.OK);
        }
}
