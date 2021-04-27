package com.library.controller;

import com.library.entities.LigneApprovisionnement;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.LigneApprovisionnementService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @GetMapping(value = "/ligneApprovisionnements", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneApprovisionnement",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneApprovisionnement", responseContainer = "List<LigneApprovisionnement>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneApprovisionnement / une liste vide")
    })
    public List<LigneApprovisionnement> getAllLigneApprovisionnements() {
        return ligneApprovisionnementService.findAllLigneApprovisionnements();

    }

    @GetMapping(value = "/ligneApprovisionnements/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un LigneApprovisionnement par ID",
            notes = "Cette méthode permet de chercher une Charge par son ID", response = LigneApprovisionnement.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La LigneApprovisionnement a été trouver"),
            @ApiResponse(code = 404, message = "Aucun LigneApprovisionnement n'existe avec cette ID pas dans la BD")

    })
    public ResponseEntity<LigneApprovisionnement> getLigneApprovisionnementById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        LigneApprovisionnement ligneApprovisionnement = ligneApprovisionnementService.findLigneApprovisionnementById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LigneCmdClient not found"));
        return ResponseEntity.ok().body(ligneApprovisionnement);

    }

    @GetMapping(value = "/lappros/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneApprovisionnement par numero",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneApprovisionnements par numero", responseContainer = "List<LigneApprovisionnement>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneApprovisionnement par numero / une liste vide")
    })
    public List<LigneApprovisionnement> getAllByNumero(@PathVariable(value = "id") long numero) {
        System.out.println("Get all Lappros...");

        List<LigneApprovisionnement> lappros = new ArrayList<>();
        ligneApprovisionnementService.findAllLApproByNumero(numero).forEach(lappros::add);

        return lappros;
    }


    @GetMapping(value = "/searchListLigneApprovisionnementByProduitId", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneApprovisionnement par Produit",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneApprovisionnements par Produit", responseContainer = "List<LigneApprovisionnement>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneApprovisionnement par Client / une liste vide")
    })
    public List<LigneApprovisionnement> getListLigneApprovisionnementByProduitId(@RequestParam("prodId") Long prodId) {
        return ligneApprovisionnementService.findListLigneApprovisionnementByProduitId(prodId);
    }

    @GetMapping(value = "/searchListLigneApproByApprovisionnementId/{approId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneApprovisionnement par Approvisionement",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneApprovisionnements par Approvisionement", responseContainer = "List<LigneApprovisionnement>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneApprovisionnement par Approvisionement / une liste vide")
    })
    public List<LigneApprovisionnement> getListLigneApprovisionnementByApprovisionnementId(@PathVariable("approId") Long approId) {
        return ligneApprovisionnementService.findListLigneApprovisionnementByApprovisionnementId(approId);
    }

    @PostMapping(value = "/ligneApprovisionnements", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un LigneApprovisionnement",
            notes = "Cette méthode permet d'enregistrer une LigneApprovisionnement", response = LigneApprovisionnement.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "La LigneApprovisionnement a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun LigneApprovisionnement  crée / modifié")

    })
    public ResponseEntity<LigneApprovisionnement> createLigneApprovisionnement(@RequestBody LigneApprovisionnement ligneApprovisionnement) {
        // return ligneApprovisionnementService.saveLigneApprovisionnement(ligneApprovisionnement);
        return new ResponseEntity<>(ligneApprovisionnementService.saveLigneApprovisionnement(ligneApprovisionnement), HttpStatus.CREATED);
    }

    @PutMapping(value = "/ligneApprovisionnements/{lApproId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier une LigneApprovisionnement par son ID",
            notes = "Cette méthode permet de modifier une LigneApprovisionnement par son ID", response = LigneApprovisionnement.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "La LigneApprovisionnement avec l'id ID a été modifié"),
            @ApiResponse(code = 400, message = "Aucun LigneApprovisionnement modifié avec l'id ID")

    })
    public ResponseEntity<LigneApprovisionnement> updateLigneApprovisionnement(@PathVariable(value = "lApproId") Long lApproId, @RequestBody LigneApprovisionnement ligneApprovisionnement) {
        ligneApprovisionnement.setId(lApproId);
        return new ResponseEntity<>(ligneApprovisionnementService.saveLigneApprovisionnement(ligneApprovisionnement), HttpStatus.OK);

    }

}
