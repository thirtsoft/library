package com.library.controller;

import com.library.entities.Devis;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.DevisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class DevisController {

    @Autowired
    private DevisService devisService;

    private Double total = 0.0;

    @GetMapping("/devis")
    public List<Devis> getAllDevis() {
        return devisService.findAllDevis();

    }

    @GetMapping("/devis/{id}")
    public ResponseEntity<Devis> getDevisById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Devis devis = devisService.findDevisById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Devis not found"));
        return ResponseEntity.ok().body(devis);

    }

    @GetMapping("/searchDevisByNumeroDevis")
    public Devis getDevisByNumeroDevis(@RequestParam("num") Long numeroDevis) {
        return devisService.findByNumeroDevis(numeroDevis);
    }

    @GetMapping("/NumberOfDevis")
    public int getNumberOfdevis() {
        return devisService.getNumberOfDevis();
    }

    @GetMapping("/NumbersOfdevis")
    public BigDecimal getNumbersOfdevis() {
        return devisService.countNumbersOfDevis();
    }


    @GetMapping("/searchListDevisByClientId")
    public List<Devis> getAllDevisByClientId(@RequestParam("clientId") Long clientId) {
        return devisService.findDevisByClientId(clientId);
    }

    @GetMapping("/getAllDeviswithdate")
    public List<Devis> getAlldevisWithDatet(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateCommande) {

        return devisService.findDevisByDate(dateCommande);
    }

    @PostMapping("/devis")
    public ResponseEntity<Devis> createDevis(@RequestBody Devis Devis) {

        return new ResponseEntity<>(devisService.saveDevis(Devis), HttpStatus.CREATED);
    }

    @PutMapping("/devis/{id}")
    public ResponseEntity<Devis> updateDevis(@PathVariable(value = "id") Long id, @RequestBody Devis devis) throws Exception {
        devis.setId(id);
        return new ResponseEntity<>(devisService.saveDevis(devis), HttpStatus.OK);

    }

    @DeleteMapping("/devis/{id}")
    public ResponseEntity<?> deleteDevis(@PathVariable(value = "id") Long id) {
        devisService.deleteDevis(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/searchNumberOfDevisByMonth")
    public List<?> getNumberTotalOfDevisByMonth() {
        return devisService.countNumberTotalOfDevisByMonth();
    }

    @GetMapping("/searchSumDevisByMonth")
    public List<?> getSumTotalOfDevisByMonth() {
        return devisService.sumTotalOfDevisByMonth();

    }

    @GetMapping("/generateNumeroDevis")
    public long generateNumeroDevis() {
        return devisService.generateNumeroDevis();
    }


}
