package com.library.controller;

import com.library.entities.LigneApprovisionnement;
import com.library.entities.LigneAvoir;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.LigneAvoirService;
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
public class LigneAvoirController {

    @Autowired
    private LigneAvoirService ligneAvoirService;

    @GetMapping(value = APP_ROOT + "/ligneAvoirs/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneAvoir",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneAvoir", responseContainer = "List<LigneAvoir>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneAvoir / une liste vide")
    })
    public List<LigneAvoir> getAllLigneAvoirs() {
        return ligneAvoirService.findAllLigneAvoirs();
    }

    @GetMapping(value = APP_ROOT + "/ligneAvoirs/allLigneAvoirOrderDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneAvoir",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneAvoir",
            responseContainer = "List<LigneAvoir>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneAvoir / une liste vide")
    })
    ResponseEntity<List<LigneAvoir>> getAllLigneAvoirOrderDesc() {
        List<LigneAvoir> ligneAvoirList = ligneAvoirService.findAllLigneAvoirsOrderDesc();
        return new ResponseEntity<>(ligneAvoirList, HttpStatus.OK);
    }

    @GetMapping(value = APP_ROOT + "/ligneAvoirs/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un LigneAvoir par ID",
            notes = "Cette méthode permet de chercher une LigneAvoir par son ID", response = LigneAvoir.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La LigneAvoir a été trouver"),
            @ApiResponse(code = 404, message = "Aucun LigneAvoir n'existe avec cette ID pas dans la BD")

    })
    public ResponseEntity<LigneAvoir> getLigneAvoirById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        LigneAvoir ligneAvoir = ligneAvoirService.findLigneAvoirById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ligneAvoir Not found"));
        return ResponseEntity.ok().body(ligneAvoir);

    }

    @GetMapping(value = APP_ROOT + "/lavoirs/findByNumero/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneAvoir par numero",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneAvoir par numero", responseContainer = "List<LigneAvoir>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneAvoir par numero / une liste vide")
    })
    public List<LigneAvoir> getAllByNumero(@PathVariable(value = "id") long numero) {
        System.out.println("Get all Lavoirs...");

        List<LigneAvoir> Lavoirs = new ArrayList<>();
        ligneAvoirService.findAllLavoirByNumero(numero).forEach(Lavoirs::add);

        return Lavoirs;
    }

    @GetMapping(value = APP_ROOT + "/ligneAvoirs/searchListLigneAvoirByProduitId", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneAvoir par Produit",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneAvoir par Produit", responseContainer = "List<LigneAvoir>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneAvoir par Produit / une liste vide")
    })
    public List<LigneAvoir> getAllLigneAvoirByProduitId(@RequestParam("prodId") Long prodId) {
        return ligneAvoirService.findLigneAvoirByProduitId(prodId);
    }

    @GetMapping(value = APP_ROOT + "/ligneAvoirs/searchListLigneAvoirByAvoirId/{avoirId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneAvoir par Avoir",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneAvoir par Avoir", responseContainer = "List<LigneAvoir>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneAvoir par Avoir / une liste vide")
    })
    public List<LigneAvoir> getAllLigneAvoirByAvoirId(@PathVariable("avoirId") Long avoirId) {
        return ligneAvoirService.findLigneAvoirByAvoirId(avoirId);
    }

    @PostMapping(value = APP_ROOT + "/ligneAvoirs/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une LigneAvoir",
            notes = "Cette méthode permet d'enregistrer une LigneAvoir", response = LigneAvoir.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "La LigneAvoir a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun LigneAvoir  crée / modifié")

    })
    public ResponseEntity<LigneAvoir> createLigneAvoir(@RequestBody LigneAvoir ligneAvoir) {
        //	return ligneAvoirService.saveLigneAvoir(ligneAvoir);
        return new ResponseEntity<>(ligneAvoirService.saveLigneAvoir(ligneAvoir), HttpStatus.CREATED);
    }

    @PutMapping(value = APP_ROOT + "/ligneAvoirs/update/{lcId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier une LigneAvoir par son ID",
            notes = "Cette méthode permet d'enregistrer une LigneAvoir par son ID", response = LigneAvoir.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "La LigneAvoir avec l'id ID a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun LigneAvoir avec cet id ID  crée / modifié")

    })
    public ResponseEntity<LigneAvoir> updateLigneAvoir(@PathVariable(value = "lcId") Long lcId, @RequestBody LigneAvoir ligneAvoir) {
        ligneAvoir.setId(lcId);
        return new ResponseEntity<>(ligneAvoirService.saveLigneAvoir(ligneAvoir), HttpStatus.OK);

    }

    @DeleteMapping(value = APP_ROOT + "/ligneAvoirs/delete/{id}")
    @ApiOperation(value = "Supprimer un LigneAvoir par son numero",
            notes = "Cette méthode permet de supprimer un LigneAvoir par son numero")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La LigneAvoir a été supprimé")
    })
    public ResponseEntity<?> deleteLigneAvoir(@PathVariable(value = "id") long numero) {
        ligneAvoirService.deleteLavoirByNumero(numero);
        return ResponseEntity.ok().build();

    }


}
