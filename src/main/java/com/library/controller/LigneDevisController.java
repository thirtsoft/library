package com.library.controller;

import com.library.entities.LigneDevis;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.LigneDevisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class LigneDevisController {

    @Autowired
    private LigneDevisService ligneDevisService;

    @GetMapping("/ligneDevis")
    public List<LigneDevis> getAllLigneDeviss() {
        return ligneDevisService.findAllLigneDevis();

    }

    @GetMapping("/ligneDevis/{id}")
    public ResponseEntity<LigneDevis> getLigneDevisById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        LigneDevis LigneDevis = ligneDevisService.findLigneDevisById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LigneDevis not found"));
        return ResponseEntity.ok().body(LigneDevis);

    }

    @GetMapping("/ldeviss/{id}")
    public List<LigneDevis> getAllByNumero(@PathVariable(value = "id") long numero) {
        System.out.println("Get all Lcomms...");

        List<LigneDevis> Ldevis = new ArrayList<>();
        ligneDevisService.findAllLigneDevisByNumero(numero).forEach(Ldevis::add);

        return Ldevis;
    }

    @GetMapping("/searchListLigneDevisByProduitId")
    public List<LigneDevis> getAllLigneDevisByProduitId(@RequestParam("prodId") Long prodId) {
        return ligneDevisService.findLigneDevisByProduitId(prodId);
    }

    @GetMapping("/searchListLigneDevisByDevisId/{comId}")
    public List<LigneDevis> getAllLigneDevisByDevisId(@PathVariable("comId") Long comId) {
        return ligneDevisService.findLigneDevisByDevId(comId);
    }

    @PostMapping("/ligneDevis")
    public ResponseEntity<LigneDevis> createLigneDevis(@RequestBody LigneDevis LigneDevis) {
        //	return LigneDevisService.saveLigneDevis(LigneDevis);
        return new ResponseEntity<LigneDevis>(ligneDevisService.saveLigneDevis(LigneDevis), HttpStatus.CREATED);
    }

    @PutMapping("/ligneDevis/{lcId}")
    public ResponseEntity<LigneDevis> updateLigneDevis(@PathVariable(value = "lcId") Long lcId, @RequestBody LigneDevis LigneDevis) {
        LigneDevis.setId(lcId);
        return new ResponseEntity<>(ligneDevisService.saveLigneDevis(LigneDevis), HttpStatus.OK);

    }

    @DeleteMapping("/ligneDevis/{id}")
    public ResponseEntity<Object> deleteLigneDevis(@PathVariable(value = "id") Long id) {
        ligneDevisService.deleteLigneDevis(id);
        return ResponseEntity.ok().build();

    }

}
