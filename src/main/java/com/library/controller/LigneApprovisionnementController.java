package com.library.controller;

import com.library.entities.LigneApprovisionnement;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.LigneApprovisionnementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class LigneApprovisionnementController {

    @Autowired
    private LigneApprovisionnementService ligneApprovisionnementService;

    @GetMapping("/ligneApprovisionnements")
    public List<LigneApprovisionnement> getAllLigneApprovisionnements() {
        return ligneApprovisionnementService.findAllLigneApprovisionnements();

    }

    @GetMapping("/ligneApprovisionnements/{id}")
    public ResponseEntity<LigneApprovisionnement> getLigneApprovisionnementById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        LigneApprovisionnement ligneApprovisionnement = ligneApprovisionnementService.findLigneApprovisionnementById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LigneCmdClient not found"));
        return ResponseEntity.ok().body(ligneApprovisionnement);

    }

    @GetMapping("/lappros/{id}")
    public List<LigneApprovisionnement> getAllByNumero(@PathVariable(value = "id") long numero) {
        System.out.println("Get all Lappros...");

        List<LigneApprovisionnement> lappros = new ArrayList<>();
        ligneApprovisionnementService.findAllLApproByNumero(numero).forEach(lappros::add);

        return lappros;
    }


    @GetMapping("/searchListLigneApprovisionnementByProduitId")
    public List<LigneApprovisionnement> getListLigneApprovisionnementByProduitId(@RequestParam("prodId") Long prodId) {
        return ligneApprovisionnementService.findListLigneApprovisionnementByProduitId(prodId);
    }

    @GetMapping("/searchListLigneApproByApprovisionnementId/{approId}")
    public List<LigneApprovisionnement> getListLigneApprovisionnementByApprovisionnementId(@PathVariable("approId") Long approId) {
        return ligneApprovisionnementService.findListLigneApprovisionnementByApprovisionnementId(approId);
    }

    @PostMapping("/ligneApprovisionnements")
    public ResponseEntity<LigneApprovisionnement> createLigneApprovisionnement(@RequestBody LigneApprovisionnement ligneApprovisionnement) {
        // return ligneApprovisionnementService.saveLigneApprovisionnement(ligneApprovisionnement);
        return new ResponseEntity<>(ligneApprovisionnementService.saveLigneApprovisionnement(ligneApprovisionnement), HttpStatus.CREATED);
    }

    @PutMapping("/ligneApprovisionnements/{lApproId}")
    public ResponseEntity<LigneApprovisionnement> updateLigneApprovisionnement(@PathVariable(value = "lApproId") Long lApproId, @RequestBody LigneApprovisionnement ligneApprovisionnement) {
        ligneApprovisionnement.setId(lApproId);
        return new ResponseEntity<>(ligneApprovisionnementService.saveLigneApprovisionnement(ligneApprovisionnement), HttpStatus.OK);

    }

}
