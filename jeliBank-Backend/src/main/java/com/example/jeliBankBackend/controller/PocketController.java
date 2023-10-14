package com.example.jeliBankBackend.controller;

import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.model.Pocket;
import com.example.jeliBankBackend.service.PocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class PocketController {

    private PocketService pocketService;

    @Autowired
    public PocketController(PocketService pocketService){
        this.pocketService = pocketService;
    }

    @PostMapping("/pocket")
    public Pocket createPocket(Pocket pocket){
        return pocketService.createPocket(pocket);
    }

    @GetMapping("/pockets")
    public List<Pocket> getAllPockets(){
        return pocketService.getAllPokets();
    }
    @GetMapping("/pockets/{pocketNumber}")
    public Optional<Pocket> getPoketByNumber(Long pocketNumber) {
        return Optional.ofNullable(this.pocketService.getPoketByNumber(pocketNumber));
    }

    @PutMapping("/acount/{pocketToDelete}")
    public ResponseEntity<?> upDatePocket(@RequestBody Pocket pocketToDelete) throws ResourseNotFoundException {
        pocketService.upDatePocket(pocketToDelete);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/acount/{pocketNumber}")
    public ResponseEntity<?> deletePocket(@PathVariable Long pocketNumber) throws ResourseNotFoundException{
        pocketService.deletePocket(pocketNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
