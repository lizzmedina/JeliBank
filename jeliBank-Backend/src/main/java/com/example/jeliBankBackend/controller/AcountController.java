package com.example.jeliBankBackend.controller;

import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.model.Acount;
import com.example.jeliBankBackend.service.AcountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class AcountController {

    private AcountService acountService;

    @Autowired
    public  AcountController(AcountService acountService){
        this.acountService = acountService;
    }

    @PostMapping("/acounts")
    public Acount createAcount (@RequestBody Acount acount) throws ResourseNotFoundException {
        return this.acountService.createAcount(acount);
    }
    @GetMapping("/acounts")
    public List<Acount> getAcounts(){
        return this.acountService.getAcounts();
    }
    @GetMapping("/acounts/{acountId}")
    public Optional<Acount> getAcountById(@PathVariable("acountId") Long acountId) throws ResourseNotFoundException {
        return this.acountService.getAcountById(acountId);
    }
    @GetMapping("/acounts/{acountNumber}")
    public Optional<Acount> getAcountByNumber(@PathVariable("acountNumber") Long acountNumber) throws ResourseNotFoundException {
        return Optional.ofNullable(this.acountService.getAcountByNumber(acountNumber));
    }
    @PutMapping("/acount/{acountToDelete}")
    public ResponseEntity<?> upDateAcount(@RequestBody Acount acountToDelete) throws ResourseNotFoundException{
        acountService.upDateAcount(acountToDelete);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/acount/{acountNumber}")
    public ResponseEntity<?> deleteAcount(@PathVariable Long acountNumber) throws ResourseNotFoundException{
        acountService.deleteAcount(acountNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
