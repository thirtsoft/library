package com.library.controller;

import com.library.entities.LigneVente;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.LigneVenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/apiSeller")
public class LigneVenteController {

    @Autowired
    private LigneVenteService ligneVenteService;

    @GetMapping("/ligneVentes")
    public List<LigneVente> getAllLigneVentes() {
        return ligneVenteService.findAllLigneVentes();

    }

    @GetMapping("/ligneVentes/{id}")
    public ResponseEntity<LigneVente> getLigneVenteById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        LigneVente ligneVente = ligneVenteService.findLigneVenteById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ligne Vente Not found"));
        return ResponseEntity.ok().body(ligneVente);

    }

    @GetMapping("/lventes/{id}")
    public List<LigneVente> getAllByNumero(@PathVariable(value = "id") Long numero) {
        System.out.println("Get all Lventes...");

        List<LigneVente> Lventes = new ArrayList<>();
        ligneVenteService.findAllLventeByNumero(numero).forEach(Lventes::add);

        return Lventes;
    }

    @GetMapping("/searchListLigneVentestByProduitId")
    public List<LigneVente> getAllLigneVenteByProduitId(@RequestParam("prodId") Long prodId) {
        return ligneVenteService.findLigneVenteByProduitId(prodId);
    }

    @GetMapping("/searchListLigneVentesByVenteId/{venteId}")
    public List<LigneVente> getAllLigneVenteByVenteId(@PathVariable("venteId") Long venteId) {
        return ligneVenteService.findLigneVenteByVenteId(venteId);
    }

    @PostMapping("/ligneVentes")
    public ResponseEntity<LigneVente> createLigneVente(@RequestBody LigneVente ligneVente) {
        //    return ligneVenteService.saveLigneVente(ligneVente);
        return new ResponseEntity<>(ligneVenteService.saveLigneVente(ligneVente), HttpStatus.CREATED);
    }

    @PutMapping("/ligneVentes/{lcId}")
    public ResponseEntity<LigneVente> updateLigneVente(@PathVariable(value = "lventeId") Long lventeId, @RequestBody LigneVente ligneVente) {
        ligneVente.setId(lventeId);
        return new ResponseEntity<>(ligneVenteService.saveLigneVente(ligneVente), HttpStatus.OK);

    }

    @DeleteMapping("/ligneVentes/{id}")
    public ResponseEntity<?> deleteLigneVente(@PathVariable(value = "id") Long id) {
        ligneVenteService.deleteLigneVente(id);
        return ResponseEntity.ok().build();

    }
}
