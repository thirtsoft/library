package com.library.controller;

import com.library.entities.HistoriqueCharge;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.HistoriqueChargeService;
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
public class HistoriqueChargeController {

    private final HistoriqueChargeService historiqueChargeService;

    @Autowired
    public HistoriqueChargeController(HistoriqueChargeService historiqueChargeService) {
        this.historiqueChargeService = historiqueChargeService;
    }

    @PostMapping(value = APP_ROOT + "/historiqueCharges/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregister historique Appro",
            notes = "Cette méthode permet d'Enregister une historique d'approvisionnement",
            responseContainer = "List<HistoriqueCharge>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des approvisionnements / une liste vide")
    })
    ResponseEntity<HistoriqueCharge> saveHistoriqueCharge(@RequestBody HistoriqueCharge historiqueCharge) {

        HistoriqueCharge HistoriqueChargeResultat = historiqueChargeService.saveHistoriqueCharge(historiqueCharge);

        return new ResponseEntity<>(HistoriqueChargeResultat, HttpStatus.CREATED);
    }

    @GetMapping(value = APP_ROOT + "/historiqueCharges/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une HistoriqueAppro par ID",
            notes = "Cette méthode permet de chercher un HistoriqueAppro par son ID", response = HistoriqueCharge.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La avec l'id ID HistoriqueLogin a été trouver"),
            @ApiResponse(code = 404, message = "Aucun HistoriqueLogin n'existe avec cette ID pas dans la BD")

    })
    ResponseEntity<HistoriqueCharge> getHistoriqueChargeById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {

        HistoriqueCharge HistoriqueChargeResultat = historiqueChargeService.findHistoriqueChargeById(id).get();

        return new ResponseEntity<>(HistoriqueChargeResultat, HttpStatus.OK);

    }

    @GetMapping(value = APP_ROOT + "/historiqueCharges/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des HistoriqueCharge",
            notes = "Cette méthode permet de chercher et renvoyer la liste des HistoriqueCharge", responseContainer = "List<HistoriqueCharge>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des historiqueLogins / une liste vide")
    })
    ResponseEntity<List<HistoriqueCharge>> getAllHistoriqueLogin() {
        List<HistoriqueCharge> HistoriqueChargeList = historiqueChargeService.findAllHistoriqueCharges();
        return new ResponseEntity<>(HistoriqueChargeList, HttpStatus.OK);

    }

    @GetMapping(value = APP_ROOT + "/historiqueCharges/allHistoriqueChargeOrderDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des HistoriqueCharge",
            notes = "Cette méthode permet de chercher et renvoyer la liste des historiqueLogins", responseContainer = "List<HistoriqueCharge>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des historiqueLogins / une liste vide")
    })
    ResponseEntity<List<HistoriqueCharge>> getAllHistoriqueLoginOrderDesc() {
        List<HistoriqueCharge> HistoriqueChargeList = historiqueChargeService.findAllHistoriqueChargesOrderDesc();
        return new ResponseEntity<>(HistoriqueChargeList, HttpStatus.OK);
    }

    @GetMapping(value = APP_ROOT + "/historiqueCharges/NumberOfHistoriqueCharge", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi le nombre de HistoriqueCharge",
            notes = "Cette méthode permet de compter le nombre d'historique de connexion")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le nombre d'historique de connexion / une liste vide")
    })
    BigDecimal countNumberOfHistoriqueLogin() {
        return historiqueChargeService.countNumberOfHistoriqueCharges();
    }

    @DeleteMapping(value = APP_ROOT + "/historiqueCharges/delete/{id}")
    @ApiOperation(value = "Supprimer une HistoriqueCharge par son ID",
            notes = "Cette méthode permet de supprimer un HistoriqueCharge par son ID", response = HistoriqueCharge.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La HistoriqueLogin a été supprimé")
    })
    ResponseEntity<?> deleteHistoriqueCharge(@PathVariable(value = "id") Long id) {
        historiqueChargeService.deleteHistoriqueCharge(id);
        return ResponseEntity.ok().build();
    }


}
