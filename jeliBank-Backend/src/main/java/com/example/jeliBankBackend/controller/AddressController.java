//package com.example.jeliBankBackend.controller;
//
//import com.example.jeliBankBackend.dtos.requests.AddressRequestDto;
//import com.example.jeliBankBackend.dtos.requests.ClientRequestDto;
//import com.example.jeliBankBackend.dtos.responses.AddressResponseDto;
//import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
//import com.example.jeliBankBackend.model.Address;
//import com.example.jeliBankBackend.service.AddressService;
//import com.example.jeliBankBackend.service.ClientService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/v1")
//public class AddressController {
//
//    private final AddressService addressService;
//    private final ClientService clientService;
//
//    @Autowired
//    public AddressController(AddressService addressService,  ClientService clientService){
//        this.addressService = addressService;
//        this.clientService = clientService;
//    }
//
//    @GetMapping("/address")
//    public List<Address> getAllAddress() throws ResourseNotFoundException {
//            return addressService.getAllAddress();
//        }
//
//    @PutMapping("/address/{addressId}")
//    public ResponseEntity<?> upDateAddress(@PathVariable Long addressId, @RequestBody Address address) throws ResourseNotFoundException {
//        addressService.upDateAddress(addressId, address);
//        return ResponseEntity.ok(HttpStatus.OK);
//    }
//
//    @DeleteMapping("/address/{addressNumber}")
//        public ResponseEntity<?> deletePocket(@PathVariable Long addressNumber) throws ResourseNotFoundException{
//            addressService.deleteAddress(addressNumber);
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
//}
