package com.library.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.library.entities.Creance;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.CreanceService;
import com.library.services.LigneCreanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/alAmine")
public class CreanceController {

    @Autowired
    private CreanceService creanceService;

    @Autowired
    private LigneCreanceService ligneCreanceService;


    private Double total = 0.0;

    @GetMapping("/creances")
    public List<Creance> getAllCreances() {
        return creanceService.findAllCreances();

    }

    @GetMapping("/creances/{id}")
    public ResponseEntity<Creance> getCreanceById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Creance creance = creanceService.findCreanceById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande not found"));
        return ResponseEntity.ok().body(creance);

    }

    @GetMapping("/searchCreanceByReference")
    public Optional<Creance> getCreanceByReference(@RequestParam("num") long reference) {
        return creanceService.findByReference(reference);
    }

    @PostMapping(path = "/updateStatus")
    public ResponseEntity<Boolean> updateStatus(@RequestBody ObjectNode json) {
        int reference;
        String ref;
        String status;
        try {
            ref = new ObjectMapper().treeToValue(json.get("ref"), String.class);
            reference = Integer.parseInt(ref);
            status = new ObjectMapper().treeToValue(json.get("status"), String.class);
            boolean test = this.creanceService.updateStatus(reference, status);
            if (test)
                return new ResponseEntity<Boolean>(test, HttpStatus.OK);

        } catch (JsonProcessingException e) {
            System.out.println("Parsing Exception!!");
            e.printStackTrace();
            return new ResponseEntity<Boolean>(false, HttpStatus.NOT_ACCEPTABLE);

        }

        return new ResponseEntity<Boolean>(false, HttpStatus.NOT_ACCEPTABLE);

    }

    @PostMapping(path = "/updateStatusCreance")
    public ResponseEntity<Boolean> updateStatusCreance(@RequestBody ObjectNode json) {
        String codeCreance;
        String status;
        try {
            codeCreance = new ObjectMapper().treeToValue(json.get("codeCreance"), String.class);
            status = new ObjectMapper().treeToValue(json.get("status"), String.class);
            boolean test = this.creanceService.updateStatusCreance(codeCreance, status);
            if (test)
                return new ResponseEntity<Boolean>(test, HttpStatus.OK);

        } catch (JsonProcessingException e) {
            System.out.println("Parsing Exception!!");
            e.printStackTrace();
            return new ResponseEntity<Boolean>(false, HttpStatus.NOT_ACCEPTABLE);

        }

        return new ResponseEntity<Boolean>(false, HttpStatus.NOT_ACCEPTABLE);

    }

    @GetMapping("/NumberOfCreances")
    public int getNumberOfCreances() {
        return creanceService.getNumberOfCreances();
    }

    @GetMapping("/SumNumbersOfCreances")
    public BigDecimal getNumbersOfCreances() {
        return creanceService.countNumbersOfCreances();
    }


    @GetMapping("/searchCreanceByStatus")
    public Creance getCreanceByStatus(@RequestParam("status") String status) {
        return creanceService.findByStatus(status);
    }

    @GetMapping("/searchListCreanceByStatus")
    public List<Creance> getAllCreanceByStatus(@RequestParam("status") String status) {
        return creanceService.findListCreanceByStatus(status);
    }

    @GetMapping("/searchListCreanceByClientId")
    public List<Creance> getAllCreanceByClientId(@RequestParam("clientId") Long clientId) {
        return creanceService.findCreanceByClientId(clientId);
    }

    @PostMapping("/creances")
    public ResponseEntity<Creance> createCreance(@RequestBody Creance creance) {
        return new ResponseEntity<Creance>(creanceService.saveCreance(creance), HttpStatus.CREATED);
    }

    @PutMapping("/creances/{id}")
    public ResponseEntity<Creance> updateCreance(@PathVariable(value = "id") Long id, @RequestBody Creance creance) throws Exception {
        creance.setId(id);
        return new ResponseEntity<>(creanceService.saveCreance(creance), HttpStatus.OK);

    }

    @PatchMapping("/setCreanceStatusById/{id}")
    public ResponseEntity<Creance> setCreanceStatusById(@PathVariable("id") Long id, @RequestBody Creance creance) {
        creance.setId(id);
        return new ResponseEntity<>(creanceService.saveCreance(creance), HttpStatus.OK);
    }

    @PatchMapping("/setCreanceOnlyStatus/{id}")
    public ResponseEntity<?> setCreanceOnlyStatus(@RequestParam("status") String status, @PathVariable("id") String id) {
        Creance newCreance = creanceService.setCreanceOnlyStatus(status, id);
        return new ResponseEntity<>(newCreance, HttpStatus.OK);
    }

    @PatchMapping("/setCreanceOnlySolde/{id}")
    public ResponseEntity<?> setCreanceOnlySolde(@RequestParam("soldeCreance") double soldeCreance, @PathVariable("id") String id) {
        Creance newCreance = creanceService.setCreanceOnlySolde(soldeCreance, id);
        return new ResponseEntity<>(newCreance, HttpStatus.OK);
    }

    @DeleteMapping("/creances/{id}")
    public void deleteCreance(@PathVariable(value = "id") Long id) {
        creanceService.deleteCreance(id);
    }

    @GetMapping("/generateReferenceCreance")
    public long generateReferenceCreance() {
        return creanceService.generateReferenceCreance();
    }
}
