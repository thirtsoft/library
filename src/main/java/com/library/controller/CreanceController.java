package com.library.controller;

import com.library.entities.Creance;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.CreanceService;
import com.library.services.LigneCreanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
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
                .orElseThrow(() -> new ResourceNotFoundException("Commande that id is" + id + "not found"));
        return ResponseEntity.ok().body(creance);

    }

    @GetMapping("/searchCreanceByReference")
    public Creance getCreanceByReference(@RequestParam("num") int reference) {
        return creanceService.findByReference(reference);
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
        Creance Resultat = creanceService.saveCreance(creance);
        return ResponseEntity.ok(Resultat);
    }

    @PutMapping("/creances/{id}")
    public ResponseEntity<Creance> updateCreance(@PathVariable(value = "id") Long id, @RequestBody Creance creance) throws Exception {
        creance.setId(id);
        return new ResponseEntity<>(creanceService.saveCreance(creance), HttpStatus.OK);

    }

    @DeleteMapping("/creances/{id}")
    public void deleteCreance(@PathVariable(value = "id") Long id) {
        creanceService.deleteCreance(id);
    }
}
