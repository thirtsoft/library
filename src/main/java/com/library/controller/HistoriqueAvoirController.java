package com.library.controller;

import com.library.entities.HistoriqueAvoir;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.HistoriqueAvoirService;
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
public class HistoriqueAvoirController {

    private final HistoriqueAvoirService historiqueAvoirService;

    @Autowired
    public HistoriqueAvoirController(HistoriqueAvoirService historiqueAvoirService) {
        this.historiqueAvoirService = historiqueAvoirService;
    }


    @PostMapping(value = APP_ROOT + "/historiqueAvoirs/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregister historique Avoir",
            notes = "Cette méthode permet d'Enregister une historique d'avoir",
            responseContainer = "List<HistoriqueAvoir>")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "L'historique avoirs a été crée"),
            @ApiResponse(code = 400, message = "L'historique avoirs n'a pas été crée")
    })
    ResponseEntity<HistoriqueAvoir> saveHistoriqueAvoir(HistoriqueAvoir historiqueAvoir) {

        HistoriqueAvoir historiqueAvoirResultat = historiqueAvoirService.saveHistoriqueAvoir(historiqueAvoir);

        return new ResponseEntity<>(historiqueAvoirResultat, HttpStatus.CREATED);
    }

    @GetMapping(value = APP_ROOT + "/historiqueAvoirs/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une HistoriqueAvoir par ID",
            notes = "Cette méthode permet de chercher un HistoriqueAvoir par son ID", response = HistoriqueAvoir.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La avec l'id ID HistoriqueAvoir a été trouver"),
            @ApiResponse(code = 404, message = "Aucun HistoriqueAvoir n'existe avec cette ID pas dans la BD")

    })
    ResponseEntity<HistoriqueAvoir> getHistoriqueAvoirById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {

        HistoriqueAvoir historiqueAvoirResultat = historiqueAvoirService.findHistoriqueAvoirById(id).get();

        return new ResponseEntity<>(historiqueAvoirResultat, HttpStatus.OK);

    }

    @GetMapping(value = APP_ROOT + "/historiqueAvoirs/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des HistoriqueAvoir",
            notes = "Cette méthode permet de chercher et renvoyer la liste des HistoriqueAvoir", responseContainer = "List<HistoriqueAvoir>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des historiqueAvoirs / une liste vide")
    })
    ResponseEntity<List<HistoriqueAvoir>> getAllHistoriqueLogin() {
        List<HistoriqueAvoir> historiqueAvoirList = historiqueAvoirService.findAllHistoriqueAvoirs();
        return new ResponseEntity<>(historiqueAvoirList, HttpStatus.OK);

    }

    @GetMapping(value = APP_ROOT + "/historiqueAvoirs/allHistoriqueAvoirOrderDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des HistoriqueAvoir",
            notes = "Cette méthode permet de chercher et renvoyer la liste des historiqueAvoirs", responseContainer = "List<HistoriqueAvoir>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des historiqueLogins / une liste vide")
    })
    ResponseEntity<List<HistoriqueAvoir>> getAllHistoriqueLoginOrderDesc() {
        List<HistoriqueAvoir> historiqueAvoirList = historiqueAvoirService.findAllHistoriqueAvoirsOrderDesc();
        return new ResponseEntity<>(historiqueAvoirList, HttpStatus.OK);
    }

    @GetMapping(value = APP_ROOT + "/historiqueAvoirs/NumberOfHistoriqueAvoir", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi le nombre de HistoriqueAvoir",
            notes = "Cette méthode permet de compter le nombre d'historique d'avoir")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le nombre d'historique d'avoirs / une liste vide")
    })
    BigDecimal countNumberOfHistoriqueLogin() {
        return historiqueAvoirService.countNumberOfHistoriqueAvoirs();
    }

    @DeleteMapping(value = APP_ROOT + "/historiqueAvoirs/delete/{id}")
    @ApiOperation(value = "Supprimer une HistoriqueAvoir par son ID",
            notes = "Cette méthode permet de supprimer un HistoriqueAvoir par son ID", response = HistoriqueAvoir.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La HistoriqueLogin a été supprimé")
    })
    ResponseEntity<?> deleteHistoriqueAvoir(@PathVariable(value = "id") Long id) {
        historiqueAvoirService.deleteHistoriqueAvoir(id);
        return ResponseEntity.ok().build();
    }


}
