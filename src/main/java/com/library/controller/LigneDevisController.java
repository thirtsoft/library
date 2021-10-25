package com.library.controller;

import com.library.entities.LigneCreance;
import com.library.entities.LigneDevis;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.LigneDevisService;
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
//@RequestMapping("/alAmine")
public class LigneDevisController {

    @Autowired
    private LigneDevisService ligneDevisService;

    @GetMapping(value = APP_ROOT + "/ligneDevis/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneDevis",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneDevis", responseContainer = "List<LigneDevis>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneDevis / une liste vide")
    })
    public List<LigneDevis> getAllLigneDeviss() {
        return ligneDevisService.findAllLigneDevis();
    }


    @GetMapping(value = APP_ROOT + "/ligneDevis/allLigneDevisOrderDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneDevis",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneDevis",
            responseContainer = "List<LigneDevis>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneDevis / une liste vide")
    })
    ResponseEntity<List<LigneDevis>> getAllLigneDevisOrderDesc() {
        List<LigneDevis> ligneDevisList = ligneDevisService.findAllLigneDevissOrderDesc();
        return new ResponseEntity<>(ligneDevisList, HttpStatus.OK);
    }

    @GetMapping(value = APP_ROOT + "/ligneDevis/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une LigneDevis par ID",
            notes = "Cette méthode permet de chercher une LigneDevis par son ID", response = LigneDevis.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La LigneDevis a été trouver"),
            @ApiResponse(code = 404, message = "Aucun LigneDevis n'existe avec cette ID pas dans la BD")

    })
    public ResponseEntity<LigneDevis> getLigneDevisById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        LigneDevis LigneDevis = ligneDevisService.findLigneDevisById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LigneDevis not found"));
        return ResponseEntity.ok().body(LigneDevis);

    }

    @GetMapping(value = APP_ROOT + "/ligneDevis/findByNumero/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneDevis par numero",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneDevis par numero", responseContainer = "List<LigneDevis>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneDevis par numero / une liste vide")
    })
    public List<LigneDevis> getAllByNumero(@PathVariable(value = "id") Long numero) {
        System.out.println("Get all Lcomms...");

        List<LigneDevis> Ldevis = new ArrayList<>();
        ligneDevisService.findAllLigneDevisByNumero(numero).forEach(Ldevis::add);

        return Ldevis;
    }

    @GetMapping(value = APP_ROOT + "/ligneDevis/searchListLigneDevisByProduitId", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneDevis par Produit",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneDevis par Produit", responseContainer = "List<LigneDevis>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneDevis par Produit / une liste vide")
    })
    public List<LigneDevis> getAllLigneDevisByProduitId(@RequestParam("prodId") Long prodId) {
        return ligneDevisService.findLigneDevisByProduitId(prodId);
    }

    @GetMapping(value = APP_ROOT +  "/ligneDevis/searchListLigneDevisByDevisId/{comId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneDevis par Devis",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneDevis par Devis", responseContainer = "List<LigneDevis>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneDevis par Devis / une liste vide")
    })
    public List<LigneDevis> getAllLigneDevisByDevisId(@PathVariable("comId") Long comId) {
        return ligneDevisService.findLigneDevisByDevId(comId);
    }

    @PostMapping(value = APP_ROOT + "/ligneDevis/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un LigneDevis",
            notes = "Cette méthode permet d'enregistrer et modifier un LigneDevis", response = LigneDevis.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "La LigneDevis a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun LigneDevis  crée / modifié")

    })
    public ResponseEntity<LigneDevis> createLigneDevis(@RequestBody LigneDevis LigneDevis) {
        //	return LigneDevisService.saveLigneDevis(LigneDevis);
        return new ResponseEntity<>(ligneDevisService.saveLigneDevis(LigneDevis), HttpStatus.CREATED);
    }

    @PutMapping(value = APP_ROOT + "/ligneDevis/update/{lcId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier un LigneDevis par son ID",
            notes = "Cette méthode permet de modifier un LigneDevis par son ID", response = LigneDevis.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La LigneDevis avec l'id ID a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun LigneDevis crée / modifié")

    })
    public ResponseEntity<LigneDevis> updateLigneDevis(@PathVariable(value = "lcId") Long lcId, @RequestBody LigneDevis LigneDevis) {
        LigneDevis.setId(lcId);
        return new ResponseEntity<>(ligneDevisService.saveLigneDevis(LigneDevis), HttpStatus.OK);

    }

    @DeleteMapping(value = APP_ROOT + "/ligneDevis/delete/{id}")
    @ApiOperation(value = "Supprimer une LigneDevis par son ID",
            notes = "Cette méthode permet de supprimer une LigneDevis par son ID", response = LigneDevis.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La LigneDevis a été supprimé")
    })
    public ResponseEntity<Object> deleteLigneDevis(@PathVariable(value = "id") Long id) {
        ligneDevisService.deleteLigneDevis(id);
        return ResponseEntity.ok().build();

    }

}
