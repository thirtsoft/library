package com.library.controller;

import com.library.entities.LigneCmdClient;
import com.library.entities.LigneCreance;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.LigneCreanceService;
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

import static com.library.utils.Constants.APP_ROOT;

@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class LigneCreanceController {

    @Autowired
    private LigneCreanceService ligneCreanceService;

    @GetMapping(value = "/ligneCreances", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneCreance",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneCreance", responseContainer = "List<LigneCreance>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneCreance / une liste vide")
    })
    public List<LigneCreance> getAllLigneCreances() {
        return ligneCreanceService.findAllLigneCreances();
    }

    @GetMapping(value = APP_ROOT + "/ligneCreances/allLigneCreanceOrderDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneCreance",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneCreance",
            responseContainer = "List<LigneCreance>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneCmdClient / une liste vide")
    })
    ResponseEntity<List<LigneCreance>> getAllLigneCreanceOrderDesc() {
        List<LigneCreance> ligneCreanceList = ligneCreanceService.findAllLigneCreancesOrderDesc();
        return new ResponseEntity<>(ligneCreanceList, HttpStatus.OK);
    }

    @GetMapping(value = "/ligneCreances/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une LigneCreance par ID",
            notes = "Cette méthode permet de chercher une LigneCreance par son ID", response = LigneCreance.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La LigneCreance a été trouver"),
            @ApiResponse(code = 404, message = "Aucun LigneCreance n'existe avec cette ID pas dans la BD")

    })
    public ResponseEntity<LigneCreance> getLigneCreanceById(@PathVariable(value = "id") Long lCreanceId)
            throws ResourceNotFoundException {
        LigneCreance ligneCreance = ligneCreanceService.findLigneCreanceById(lCreanceId)
                .orElseThrow(() -> new ResourceNotFoundException("LigneCreance that id is" + lCreanceId + "not found"));
        return ResponseEntity.ok().body(ligneCreance);

    }

    @GetMapping(value = "/lcreances/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneCreance par numero",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneCreance par numero", responseContainer = "List<LigneCreance>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneCreance par numero / une liste vide")
    })
    public List<LigneCreance> getAllByNumero(@PathVariable(value = "id") long numero) {
        System.out.println("Get all Lcreances...");

        List<LigneCreance> Lcreances = new ArrayList<>();
        ligneCreanceService.findAllLcreanceByNumero(numero).forEach(Lcreances::add);

        return Lcreances;
    }

    @GetMapping(value = "/searchListLigneCreanceByProduitId", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneCreance par Produit",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneCreance par Produit", responseContainer = "List<LigneCreance>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneCreance par Produit / une liste vide")
    })
    public List<LigneCreance> getAllLigneCreanceByProduitId(@RequestParam("prodId") Long prodId) {
        return ligneCreanceService.findLigneCreanceByProduitId(prodId);
    }

    @GetMapping(value = "/searchListLigneCreanceByCreanceId/{creanceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneCreance par Creance",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneCreance par Creance", responseContainer = "List<LigneCreance>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneCreance par Creance / une liste vide")
    })
    public List<LigneCreance> getAllLigneCreanceByCreanceId(@PathVariable("creanceId") Long creanceId) {
        return ligneCreanceService.findLigneCreanceByCreanceId(creanceId);
    }

    @PostMapping(value = "/ligneCreances", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une LigneCreance",
            notes = "Cette méthode permet d'enregistrer et modifier une LigneCreance", response = LigneCreance.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "La LigneCreance a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun LigneCreance  crée / modifié")

    })
    public ResponseEntity<LigneCreance> createLigneCreance(@RequestBody LigneCreance ligneCreance) {
        //	return ligneCreanceService.saveLigneCreance(ligneCreance);
        return new ResponseEntity<>(ligneCreanceService.saveLigneCreance(ligneCreance), HttpStatus.CREATED);
    }

    @PutMapping(value = "/ligneCreance/{lcId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier une LigneCreance par son ID",
            notes = "Cette méthode permet de modifier une LigneCreance par son ID", response = LigneCreance.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "La LigneCreance avec l'id  ID a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun LigneCreance a été modifié avec cet ID")

    })
    public ResponseEntity<LigneCreance> updateLigneCreance(@PathVariable(value = "lcId") Long lcId, @RequestBody LigneCreance ligneCreance) {
        ligneCreance.setId(lcId);
        return new ResponseEntity<>(ligneCreanceService.saveLigneCreance(ligneCreance), HttpStatus.OK);

    }

    @DeleteMapping(value = "/ligneCreance/{id}")
    @ApiOperation(value = "Supprimer une LigneCreance par son ID",
            notes = "Cette méthode permet de supprimer une LigneCreance par son ID", response = LigneCreance.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La LigneCreance a été supprimé")
    })
    public ResponseEntity<?> deleteLigneCreance(@PathVariable(value = "id") Long id) {
        ligneCreanceService.deleteLigneCreance(id);
        return ResponseEntity.ok().build();

    }

}
