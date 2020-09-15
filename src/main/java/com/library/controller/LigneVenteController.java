package com.library.controller;

import com.library.entities.LigneVente;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.LigneVenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/alAmine")
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
                .orElseThrow(()-> new ResourceNotFoundException("Ligne Vente N° " + id + "not found"));
        return ResponseEntity.ok().body(ligneVente);

    }

    @GetMapping("/searchListLigneVentestByProduitId")
    public List<LigneVente> getAllLigneVenteByProduitId(@RequestParam("prodId") Long prodId) {
        return ligneVenteService.findLigneVenteByProduitId(prodId);
    }

    @GetMapping("/searchListLigneVentesByProduitPageable")
    public Page<LigneVente> getAllProduitsByPageable(@RequestParam(name = "prod")Long prodId,
                                                         @RequestParam(name = "page") int page,
                                                         @RequestParam(name = "size") int size) {
        return ligneVenteService.findAllLigneVenteByProduit(prodId, PageRequest.of(page, size));
    }

    @GetMapping("/searchListLigneVentesByVenteId")
    public List<LigneVente> getAllLigneVenteByVenteId(@RequestParam("comId") Long venteId) {
        return ligneVenteService.findLigneVenteByVenteId(venteId);
    }

    @GetMapping("/searchListLigneVentesByVentePageable")
    public Page<LigneVente> getAllLigneVenteByPageable(@RequestParam(name = "prod")Long comId,
                                                         @RequestParam(name = "page") int page,
                                                         @RequestParam(name = "size") int size) {
        return ligneVenteService.findAllLigneVenteByVente(comId, PageRequest.of(page, size));
    }

    @PostMapping("/ligneVentes")
    public LigneVente createLigneVente(@RequestBody LigneVente ligneVente) {
        return ligneVenteService.saveLigneVente(ligneVente);
    }

    @PutMapping("/ligneVentes/{lcId}")
    public ResponseEntity<LigneVente>  updateLigneVente(@PathVariable(value = "lventeId") Long lventeId, @RequestBody LigneVente ligneVente) {
        ligneVente.setId(lventeId);
        return new ResponseEntity<>(ligneVenteService.saveLigneVente(ligneVente), HttpStatus.OK);

    }
    @DeleteMapping("/ligneVentes/{id}")
    public ResponseEntity<Object> deleteLigneVente(@PathVariable(value = "id") Long id) {
        return ligneVenteService.deleteLigneVente(id);

    }
}
