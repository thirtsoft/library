package com.library.controller;

import com.library.entities.LigneApprovisionnement;
import com.library.entities.LigneCmdClient;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.LigneApprovisionnementService;
import com.library.services.LigneCmdClientService;
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
public class LigneApprovisionnementController {

    @Autowired
    private LigneApprovisionnementService ligneApprovisionnementService;

    @GetMapping("/ligneApprovisionnement")
    public List<LigneApprovisionnement> getAllLigneApprovisionnements() {
        return ligneApprovisionnementService.findAllLigneApprovisionnements();

    }

    @GetMapping("/ligneApprovisionnement/{id}")
    public ResponseEntity<LigneApprovisionnement> getLigneApprovisionnementById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        LigneApprovisionnement ligneApprovisionnement = ligneApprovisionnementService.findLigneApprovisionnementById(id)
                .orElseThrow(()-> new ResourceNotFoundException("LigneCmdClient that id is" + id + "not found"));
        return ResponseEntity.ok().body(ligneApprovisionnement);

    }

    @GetMapping("/searchListLigneApprovisionnementByProduitId")
    public List<LigneApprovisionnement> getListLigneApprovisionnementByProduitId(@RequestParam("prodId") Long prodId) {
        return ligneApprovisionnementService.findListLigneApprovisionnementByProduitId(prodId);
    }

    @GetMapping("/searchListLigneApprovisionnementByProduitPageable")
    public Page<LigneApprovisionnement> getAllLigneApprovisionnementByProduitsByPageable(@RequestParam(name = "prod")Long prodId,
                                                         @RequestParam(name = "page") int page,
                                                         @RequestParam(name = "size") int size) {
        return ligneApprovisionnementService.findAllLigneApprovisionnementByProduit(prodId, PageRequest.of(page, size));
    }

    @GetMapping("/searchListLigneApprovisionnementByApprovisionnementId")
    public List<LigneApprovisionnement> getListLigneApprovisionnementByApprovisionnementId(@RequestParam("approId") Long approId) {
        return ligneApprovisionnementService.findListLigneApprovisionnementByApprovisionnementId(approId);
    }

    @GetMapping("/searchListLigneApprovisionnementByApprovisionnementPageable")
    public Page<LigneApprovisionnement> getAllLigneApprovisionnementByApprovisionnementByPageable(@RequestParam(name = "prod")Long approId,
                                                         @RequestParam(name = "page") int page,
                                                         @RequestParam(name = "size") int size) {
        return ligneApprovisionnementService.findAllLigneApprovisionnementByApproviosionnement(approId, PageRequest.of(page, size));
    }

    @PostMapping("/ligneApprovisionnements")
    public LigneApprovisionnement createLigneApprovisionnement(@RequestBody LigneApprovisionnement ligneApprovisionnement) {
        return ligneApprovisionnementService.saveLigneApprovisionnement(ligneApprovisionnement);
    }

    @PutMapping("/ligneApprovisionnements/{lApproId}")
    public ResponseEntity<LigneApprovisionnement>  updateLigneApprovisionnement(@PathVariable(value = "lApproId") Long lApproId, @RequestBody LigneApprovisionnement ligneApprovisionnement) {
       ligneApprovisionnement.setId(lApproId);
       return new ResponseEntity<>(ligneApprovisionnementService.saveLigneApprovisionnement(ligneApprovisionnement), HttpStatus.OK);

    }
    @DeleteMapping("/ligneApprovisionnements/{id}")
    public ResponseEntity<Object> deleteLigneApprovisionnement(@PathVariable(value = "id") Long id) {
        return ligneApprovisionnementService.deleteLigneApprovisionnement(id);

    }
}
