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

@RestController
@CrossOrigin
@RequestMapping("/apiSeller")
public class LigneVenteController {

    @Autowired
    private LigneVenteService ligneVenteService;

    @GetMapping(value = "/ligneVentes", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneVente",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneVente", responseContainer = "List<LigneVente>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneVente / une liste vide")
    })
    public List<LigneVente> getAllLigneVentes() {
        return ligneVenteService.findAllLigneVentes();

    }

    @GetMapping(value = "/ligneVentes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping(value = "/lventes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping(value = "/searchListLigneVentestByProduitId", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneVente par produit",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneVente par produit", responseContainer = "List<LigneVente>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneVente par produit / une liste vide")
    })
    public List<LigneVente> getAllLigneVenteByProduitId(@RequestParam("prodId") Long prodId) {
        return ligneVenteService.findLigneVenteByProduitId(prodId);
    }

    @GetMapping(value = "/searchListLigneVentesByVenteId/{venteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneVente par vente",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneVente par vente", responseContainer = "List<LigneVente>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneVente par vente / une liste vide")
    })
    public List<LigneVente> getAllLigneVenteByVenteId(@PathVariable("venteId") Long venteId) {
        return ligneVenteService.findLigneVenteByVenteId(venteId);
    }

    @PostMapping(value = "/ligneVentes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @PutMapping(value = "/ligneVentes/{lcId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @DeleteMapping(value = "/ligneVentes/{id}")
    @ApiOperation(value = "Supprimer une LigneVente par son ID",
            notes = "Cette méthode permet de supprimer une LigneVente par son ID", response = LigneVente.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La LigneVente a été supprimé")
    })
    public ResponseEntity<?> deleteLigneVente(@PathVariable(value = "id") Long id) {
        ligneVenteService.deleteLigneVente(id);
        return ResponseEntity.ok().build();

    }
}
