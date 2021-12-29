package com.library.controller;

import com.library.entities.HistoriqueApprovisionnement;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.HistoriqueApprovisionnementService;
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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class HistoriqueApprivisionnementController {

    private final HistoriqueApprovisionnementService historiqueApprovisionnementService;

    @Autowired
    public HistoriqueApprivisionnementController(HistoriqueApprovisionnementService historiqueApprovisionnementService) {
        this.historiqueApprovisionnementService = historiqueApprovisionnementService;
    }

    @PostMapping(value = APP_ROOT + "/historiqueApprovisionnements/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregister historique Appro",
            notes = "Cette méthode permet d'Enregister une historique d'approvisionnement",
            responseContainer = "List<HistoriqueApprovisionnement>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des approvisionnements / une liste vide")
    })
    ResponseEntity<HistoriqueApprovisionnement> saveApprovisionnement(HistoriqueApprovisionnement historiqueApprovisionnement) {

        HistoriqueApprovisionnement historiqueApprovisionnementResultat = historiqueApprovisionnementService.saveHistoriqueApprovisionnement(historiqueApprovisionnement);

        return new ResponseEntity<>(historiqueApprovisionnementResultat, HttpStatus.CREATED);
    }

    @GetMapping(value = APP_ROOT + "/historiqueApprovisionnements/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une HistoriqueAppro par ID",
            notes = "Cette méthode permet de chercher un HistoriqueAppro par son ID", response = HistoriqueApprovisionnement.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La avec l'id ID HistoriqueLogin a été trouver"),
            @ApiResponse(code = 404, message = "Aucun HistoriqueLogin n'existe avec cette ID pas dans la BD")

    })
    ResponseEntity<HistoriqueApprovisionnement> getHistoriqueApprovisionnementById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {

        HistoriqueApprovisionnement historiqueApprovisionnementResultat = historiqueApprovisionnementService.findHistoriqueApprovisionnementById(id).get();

        return new ResponseEntity<>(historiqueApprovisionnementResultat, HttpStatus.OK);

    }

    @GetMapping(value = APP_ROOT + "/historiqueApprovisionnements/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des HistoriqueApprovisionnement",
            notes = "Cette méthode permet de chercher et renvoyer la liste des HistoriqueApprovisionnement", responseContainer = "List<HistoriqueApprovisionnement>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des historiqueLogins / une liste vide")
    })
    ResponseEntity<List<HistoriqueApprovisionnement>> getAllHistoriqueLogin() {
        List<HistoriqueApprovisionnement> historiqueApprovisionnementList = historiqueApprovisionnementService.findAllHistoriqueApprovisionnements();
        return new ResponseEntity<>(historiqueApprovisionnementList, HttpStatus.OK);

    }

    @GetMapping(value = APP_ROOT + "/historiqueApprovisionnements/allHistoriqueApprovisionnementOrderDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des HistoriqueApprovisionnement",
            notes = "Cette méthode permet de chercher et renvoyer la liste des historiqueLogins", responseContainer = "List<HistoriqueApprovisionnement>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des historiqueLogins / une liste vide")
    })
    ResponseEntity<List<HistoriqueApprovisionnement>> getAllHistoriqueLoginOrderDesc() {
        List<HistoriqueApprovisionnement> historiqueApprovisionnementList = historiqueApprovisionnementService.findAllHistoriqueApprovisionnementsOrderDesc();
        return new ResponseEntity<>(historiqueApprovisionnementList, HttpStatus.OK);
    }

    @GetMapping(value = APP_ROOT + "/historiqueApprovisionnements/NumberOfHistoriqueApprovisionnement", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi le nombre de HistoriqueApprovisionnement",
            notes = "Cette méthode permet de compter le nombre d'historique de connexion")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le nombre d'historique de connexion / une liste vide")
    })
    BigDecimal countNumberOfHistoriqueLogin() {
        return historiqueApprovisionnementService.countNumberOfHistoriqueApprovisionnements();
    }

    @DeleteMapping(value = APP_ROOT + "/historiqueApprovisionnements/delete/{id}")
    @ApiOperation(value = "Supprimer une HistoriqueApprovisionnement par son ID",
            notes = "Cette méthode permet de supprimer un HistoriqueApprovisionnement par son ID", response = HistoriqueApprovisionnement.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La HistoriqueLogin a été supprimé")
    })
    ResponseEntity<?> deleteHistoriqueApprovisionnement(@PathVariable(value = "id") Long id) {
        historiqueApprovisionnementService.deleteHistoriqueApprovisionnement(id);
        return ResponseEntity.ok().build();
    }


}
