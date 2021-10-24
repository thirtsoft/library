package com.library.controller;

import com.library.entities.HistoriqueCreance;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.HistoriqueCreanceService;
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
public class HistoriqueCreanceController {

    private final HistoriqueCreanceService historiqueCreanceService;

    @Autowired
    public HistoriqueCreanceController(HistoriqueCreanceService historiqueCreanceService) {
        this.historiqueCreanceService = historiqueCreanceService;
    }

    @PostMapping(value = APP_ROOT + "/historiqueCreances/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregister historique Appro",
            notes = "Cette méthode permet d'Enregister une historique d'approvisionnement",
            responseContainer = "List<HistoriqueCreance>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des approvisionnements / une liste vide")
    })
    ResponseEntity<HistoriqueCreance> saveHistoriqueCreance(@RequestBody HistoriqueCreance historiqueCreance) {

        HistoriqueCreance HistoriqueCreanceResultat = historiqueCreanceService.saveHistoriqueCreance(historiqueCreance);

        return new ResponseEntity<>(HistoriqueCreanceResultat, HttpStatus.CREATED);
    }

    @GetMapping(value = APP_ROOT + "/historiqueCreances/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une HistoriqueAppro par ID",
            notes = "Cette méthode permet de chercher un HistoriqueAppro par son ID", response = HistoriqueCreance.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La avec l'id ID HistoriqueLogin a été trouver"),
            @ApiResponse(code = 404, message = "Aucun HistoriqueLogin n'existe avec cette ID pas dans la BD")

    })
    ResponseEntity<HistoriqueCreance> getHistoriqueCreanceById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {

        HistoriqueCreance HistoriqueCreanceResultat = historiqueCreanceService.findHistoriqueCreanceById(id).get();

        return new ResponseEntity<>(HistoriqueCreanceResultat, HttpStatus.OK);

    }

    @GetMapping(value = APP_ROOT + "/historiqueCreances/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des HistoriqueCreance",
            notes = "Cette méthode permet de chercher et renvoyer la liste des HistoriqueCreance", responseContainer = "List<HistoriqueCreance>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des historiqueLogins / une liste vide")
    })
    ResponseEntity<List<HistoriqueCreance>> getAllHistoriqueLogin() {
        List<HistoriqueCreance> HistoriqueCreanceList = historiqueCreanceService.findAllHistoriqueCreances();
        return new ResponseEntity<>(HistoriqueCreanceList, HttpStatus.OK);

    }

    @GetMapping(value = APP_ROOT + "/historiqueCreances/allHistoriqueCreanceOrderDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des HistoriqueCreance",
            notes = "Cette méthode permet de chercher et renvoyer la liste des historiqueLogins", responseContainer = "List<HistoriqueCreance>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des historiqueLogins / une liste vide")
    })
    ResponseEntity<List<HistoriqueCreance>> getAllHistoriqueLoginOrderDesc() {
        List<HistoriqueCreance> HistoriqueCreanceList = historiqueCreanceService.findAllHistoriqueCreancesOrderDesc();
        return new ResponseEntity<>(HistoriqueCreanceList, HttpStatus.OK);
    }

    @GetMapping(value = APP_ROOT + "/historiqueCreances/NumberOfHistoriqueCreance", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi le nombre de HistoriqueCreance",
            notes = "Cette méthode permet de compter le nombre d'historique de connexion")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le nombre d'historique de connexion / une liste vide")
    })
    BigDecimal countNumberOfHistoriqueLogin() {
        return historiqueCreanceService.countNumberOfHistoriqueCreances();
    }

    @DeleteMapping(value = APP_ROOT + "/historiqueCreances/delete/{id}")
    @ApiOperation(value = "Supprimer une HistoriqueCreance par son ID",
            notes = "Cette méthode permet de supprimer un HistoriqueCreance par son ID", response = HistoriqueCreance.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La HistoriqueLogin a été supprimé")
    })
    ResponseEntity<?> deleteHistoriqueCreance(@PathVariable(value = "id") Long id) {
        historiqueCreanceService.deleteHistoriqueCreance(id);
        return ResponseEntity.ok().build();
    }


}
