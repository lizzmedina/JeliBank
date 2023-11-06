package com.example.jeliBankBackend.controller;

import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.model.Transfer;
import com.example.jeliBankBackend.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class TransferController {

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService){
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public Transfer createTransfer(Transfer transfer){
        return transferService.createTransfer(transfer);
    }

    @GetMapping("/transfers")
    public List<Transfer> getAllTransfers(){
        return transferService.getAllTransfers();
    }

    @GetMapping("/transfers/{documentClient}")
    public Optional<Transfer> getTransfersByDocumentClient(Long documentClient) throws ResourseNotFoundException {
        return this.transferService.getTransfersByDocumentClient(documentClient);
    }

    @PutMapping("/transfer/{transferToDelete}")
    public ResponseEntity<?> upDateTransfer(@RequestBody Transfer transferToDelete) throws ResourseNotFoundException {
        transferService.upDateTransfer(transferToDelete);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/transfer/{transferNumber}")
    public ResponseEntity<?> deletePocket(@PathVariable Long transferNumber) throws ResourseNotFoundException{
        transferService.deleteTranfer(transferNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
