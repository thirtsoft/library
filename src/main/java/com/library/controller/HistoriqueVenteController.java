package com.library.controller;

import com.library.entities.HistoriqueVente;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.HistoriqueVenteService;
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
public class HistoriqueVenteController {

    private final HistoriqueVenteService historiqueVenteService;

    @Autowired
    public HistoriqueVenteController(HistoriqueVenteService historiqueVenteService) {
        this.historiqueVenteService = historiqueVenteService;
    }

    @PostMapping(value = APP_ROOT + "/historiqueVentes/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregister historique Appro",
            notes = "Cette méthode permet d'Enregister une historique d'approvisionnement",
            responseContainer = "List<HistoriqueVente>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des approvisionnements / une liste vide")
    })
    ResponseEntity<HistoriqueVente> saveHistoriqueVente(@RequestBody HistoriqueVente historiqueVente) {

        HistoriqueVente HistoriqueVenteResultat = historiqueVenteService.saveHistoriqueVente(historiqueVente);

        return new ResponseEntity<>(HistoriqueVenteResultat, HttpStatus.CREATED);
    }

    @GetMapping(value = APP_ROOT + "/historiqueVentes/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une HistoriqueAppro par ID",
            notes = "Cette méthode permet de chercher un HistoriqueAppro par son ID", response = HistoriqueVente.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La avec l'id ID HistoriqueVente a été trouver"),
            @ApiResponse(code = 404, message = "Aucun HistoriqueVente n'existe avec cette ID pas dans la BD")

    })
    ResponseEntity<HistoriqueVente> getHistoriqueVenteById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {

        HistoriqueVente HistoriqueVenteResultat = historiqueVenteService.findHistoriqueVenteById(id).get();

        return new ResponseEntity<>(HistoriqueVenteResultat, HttpStatus.OK);

    }

    @GetMapping(value = APP_ROOT + "/historiqueVentes/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des HistoriqueVente",
            notes = "Cette méthode permet de chercher et renvoyer la liste des HistoriqueVente", responseContainer = "List<HistoriqueVente>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des HistoriqueVentes / une liste vide")
    })
    ResponseEntity<List<HistoriqueVente>> getAllHistoriqueVente() {
        List<HistoriqueVente> HistoriqueVenteList = historiqueVenteService.findAllHistoriqueVentes();
        return new ResponseEntity<>(HistoriqueVenteList, HttpStatus.OK);

    }

    @GetMapping(value = APP_ROOT + "/historiqueVentes/allHistoriqueVenteOrderDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des HistoriqueVente",
            notes = "Cette méthode permet de chercher et renvoyer la liste des HistoriqueVentes", responseContainer = "List<HistoriqueVente>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des HistoriqueVentes / une liste vide")
    })
    ResponseEntity<List<HistoriqueVente>> getAllHistoriqueVenteOrderDesc() {
        List<HistoriqueVente> HistoriqueVenteList = historiqueVenteService.findAllHistoriqueVentesOrderDesc();
        return new ResponseEntity<>(HistoriqueVenteList, HttpStatus.OK);
    }

    @GetMapping(value = APP_ROOT + "/historiqueVentes/NumberOfHistoriqueVente", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi le nombre de HistoriqueVente",
            notes = "Cette méthode permet de compter le nombre d'historique de connexion")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le nombre d'historique de connexion / une liste vide")
    })
    BigDecimal countNumberOfHistoriqueVente() {
        return historiqueVenteService.countNumberOfHistoriqueVentes();
    }

    @DeleteMapping(value = APP_ROOT + "/historiqueVentes/delete/{id}")
    @ApiOperation(value = "Supprimer une HistoriqueVente par son ID",
            notes = "Cette méthode permet de supprimer un HistoriqueVente par son ID", response = HistoriqueVente.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La HistoriqueVente a été supprimé")
    })
    ResponseEntity<?> deleteHistoriqueVente(@PathVariable(value = "id") Long id) {
        historiqueVenteService.deleteHistoriqueVente(id);
        return ResponseEntity.ok().build();
    }

}
