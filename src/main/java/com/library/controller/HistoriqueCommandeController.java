package com.library.controller;

import com.library.entities.HistoriqueCommande;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.HistoriqueCommandeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.library.utils.Constants.APP_ROOT;

@RestController
@CrossOrigin
public class HistoriqueCommandeController {

    private final HistoriqueCommandeService historiqueCommandeService;

    @Autowired
    public HistoriqueCommandeController(HistoriqueCommandeService historiqueCommandeService) {
        this.historiqueCommandeService = historiqueCommandeService;
    }

    @PostMapping(value = APP_ROOT + "/historiqueCommandes/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregister historique Appro",
            notes = "Cette méthode permet d'Enregister une historique d'approvisionnement",
            responseContainer = "List<HistoriqueCommande>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des approvisionnements / une liste vide")
    })
    ResponseEntity<HistoriqueCommande> saveHistoriqueCommande(@RequestBody HistoriqueCommande historiqueCommande) {

        HistoriqueCommande HistoriqueCommandeResultat = historiqueCommandeService.saveHistoriqueCommande(historiqueCommande);

        return new ResponseEntity<>(HistoriqueCommandeResultat, HttpStatus.CREATED);
    }

    @GetMapping(value = APP_ROOT + "/historiqueCommandes/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une HistoriqueAppro par ID",
            notes = "Cette méthode permet de chercher un HistoriqueAppro par son ID", response = HistoriqueCommande.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La avec l'id ID HistoriqueLogin a été trouver"),
            @ApiResponse(code = 404, message = "Aucun HistoriqueLogin n'existe avec cette ID pas dans la BD")

    })
    ResponseEntity<HistoriqueCommande> getHistoriqueCommandeById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {

        HistoriqueCommande HistoriqueCommandeResultat = historiqueCommandeService.findHistoriqueCommandeById(id).get();

        return new ResponseEntity<>(HistoriqueCommandeResultat, HttpStatus.OK);

    }

    @GetMapping(value = APP_ROOT + "/historiqueCommandes/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des HistoriqueCommande",
            notes = "Cette méthode permet de chercher et renvoyer la liste des HistoriqueCommande", responseContainer = "List<HistoriqueCommande>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des historiqueLogins / une liste vide")
    })
    ResponseEntity<List<HistoriqueCommande>> getAllHistoriqueLogin() {
        List<HistoriqueCommande> HistoriqueCommandeList = historiqueCommandeService.findAllHistoriqueCommandes();
        return new ResponseEntity<>(HistoriqueCommandeList, HttpStatus.OK);

    }

    @GetMapping(value = APP_ROOT + "/historiqueCommandes/allHistoriqueCommandeOrderDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des HistoriqueCommande",
            notes = "Cette méthode permet de chercher et renvoyer la liste des historiqueLogins", responseContainer = "List<HistoriqueCommande>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des historiqueLogins / une liste vide")
    })
    ResponseEntity<List<HistoriqueCommande>> getAllHistoriqueLoginOrderDesc() {
        List<HistoriqueCommande> HistoriqueCommandeList = historiqueCommandeService.findAllHistoriqueCommandesOrderDesc();
        return new ResponseEntity<>(HistoriqueCommandeList, HttpStatus.OK);
    }

    @GetMapping(value = APP_ROOT + "/historiqueCommandes/NumberOfHistoriqueCommande", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi le nombre de HistoriqueCommande",
            notes = "Cette méthode permet de compter le nombre d'historique de connexion")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le nombre d'historique de connexion / une liste vide")
    })
    BigDecimal countNumberOfHistoriqueLogin() {
        return historiqueCommandeService.countNumberOfHistoriqueCommandes();
    }

    @DeleteMapping(value = APP_ROOT + "/historiqueCommandes/delete/{id}")
    @ApiOperation(value = "Supprimer une HistoriqueCommande par son ID",
            notes = "Cette méthode permet de supprimer un HistoriqueCommande par son ID", response = HistoriqueCommande.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La HistoriqueLogin a été supprimé")
    })
    ResponseEntity<?> deleteHistoriqueCommande(@PathVariable(value = "id") Long id) {
        historiqueCommandeService.deleteHistoriqueCommande(id);
        return ResponseEntity.ok().build();
    }

}
