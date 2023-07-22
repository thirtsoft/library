package com.library.controller;

import com.library.entities.LigneVente;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.LigneVenteService;
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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class LigneVenteController {

    @Autowired
    private LigneVenteService ligneVenteService;

    @GetMapping(value = APP_ROOT + "/ligneVentes/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneVente",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneVente", responseContainer = "List<LigneVente>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneVente / une liste vide")
    })
    public List<LigneVente> getAllLigneVentes() {
        return ligneVenteService.findAllLigneVentes();
    }

    @GetMapping(value = APP_ROOT + "/ligneVentes/allLigneVenteOrderDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneVente",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneVente",
            responseContainer = "List<LigneVente>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneVente / une liste vide")
    })
    ResponseEntity<List<LigneVente>> getAllLigneVenteOrderDesc() {
        List<LigneVente> ligneVenteList = ligneVenteService.findAllLigneVentesOrderDesc();
        return new ResponseEntity<>(ligneVenteList, HttpStatus.OK);
    }

    @GetMapping(value = APP_ROOT + "/ligneVentes/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un LigneVente par ID",
            notes = "Cette méthode permet de chercher une LigneVente par son ID", response = LigneVente.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La LigneVente a été trouver"),
            @ApiResponse(code = 404, message = "Aucun LigneVente n'existe avec cette ID pas dans la BD")

    })
    public ResponseEntity<LigneVente> getLigneVenteById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        LigneVente ligneVente = ligneVenteService.findLigneVenteById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ligne Vente Not found"));
        return ResponseEntity.ok().body(ligneVente);

    }

    @GetMapping(value = APP_ROOT + "/ligneVentes/findByNumero/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneVente par numero",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneVente par numero", responseContainer = "List<LigneVente>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneVente par numero / une liste vide")
    })
    public List<LigneVente> getAllByNumero(@PathVariable(value = "id") Long numero) {
        System.out.println("Get all Lventes...");

        List<LigneVente> Lventes = new ArrayList<>();
        ligneVenteService.findAllLventeByNumero(numero).forEach(Lventes::add);

        return Lventes;
    }

    @GetMapping(value = APP_ROOT + "/ligneVentes/searchListLigneVentestByProduitId", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneVente par produit",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneVente par produit", responseContainer = "List<LigneVente>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneVente par produit / une liste vide")
    })
    public List<LigneVente> getAllLigneVenteByProduitId(@RequestParam("prodId") Long prodId) {
        return ligneVenteService.findLigneVenteByProduitId(prodId);
    }

    @GetMapping(value = APP_ROOT + "/ligneVentes/searchListLigneVentesByVenteId/{venteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneVente par vente",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneVente par vente", responseContainer = "List<LigneVente>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneVente par vente / une liste vide")
    })
    public List<LigneVente> getAllLigneVenteByVenteId(@PathVariable("venteId") Long venteId) {
        return ligneVenteService.findLigneVenteByVenteId(venteId);
    }

    @PostMapping(value = APP_ROOT + "/ligneVentes/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une LigneVente",
            notes = "Cette méthode permet d'enregistrer et modifier une LigneVente", response = LigneVente.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "La LigneVente a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun LigneVente  crée / modifié")

    })
    public ResponseEntity<LigneVente> createLigneVente(@RequestBody LigneVente ligneVente) {
        //    return ligneVenteService.saveLigneVente(ligneVente);
        return new ResponseEntity<>(ligneVenteService.saveLigneVente(ligneVente), HttpStatus.CREATED);
    }

    @PutMapping(value = APP_ROOT + "/ligneVentes/update/{lcId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier une LigneVente par son ID",
            notes = "Cette méthode permet de modifier une LigneVente par son ID", response = LigneVente.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La LigneVente a été modifié"),
            @ApiResponse(code = 400, message = "Aucun LigneVente n'a pas été modifié")

    })
    public ResponseEntity<LigneVente> updateLigneVente(@PathVariable(value = "lventeId") Long lventeId, @RequestBody LigneVente ligneVente) {
        ligneVente.setId(lventeId);
        return new ResponseEntity<>(ligneVenteService.saveLigneVente(ligneVente), HttpStatus.OK);

    }

    @DeleteMapping(value = APP_ROOT + "/ligneVentes/delete/{id}")
    @ApiOperation(value = "Supprimer une LigneVente par son ID",
            notes = "Cette méthode permet de supprimer une LigneVente par son ID", response = LigneVente.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La LigneVente a été supprimé")
    })
    public ResponseEntity<?> deleteLigneVente(@PathVariable(value = "id") Long id) {
        ligneVenteService.deleteLigneVente(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = APP_ROOT + "/ligneVentes/top-100-ligne-ventes-order-desc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des 100 dernière LigneVente",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneVente",
            responseContainer = "List<LigneVente>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneVente / une liste vide")
    })
    ResponseEntity<List<LigneVente>> getTop100LigneVenteOrderDesc() {
        List<LigneVente> ligneVenteList = ligneVenteService.findTop100ByOrderByIdDesc();
        return new ResponseEntity<>(ligneVenteList, HttpStatus.OK);
    }
}
