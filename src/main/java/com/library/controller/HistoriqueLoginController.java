package com.library.controller;

import com.library.entities.HistoriqueLogin;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.HistoriqueLoginService;
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
public class HistoriqueLoginController {

    private final HistoriqueLoginService historiqueLoginService;

    @Autowired
    public HistoriqueLoginController(HistoriqueLoginService historiqueLoginService) {
        this.historiqueLoginService = historiqueLoginService;
    }

    @PostMapping(value = APP_ROOT + "/historiqueLogins/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregister historique Appro",
            notes = "Cette méthode permet d'Enregister une historique de login",
            responseContainer = "List<HistoriqueLogin>")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "La liste des approvisionnements / une liste vide")
    })
    ResponseEntity<HistoriqueLogin> saveHistoriqueLogin(@RequestBody HistoriqueLogin historiqueLogin) {

        HistoriqueLogin HistoriqueLoginResultat = historiqueLoginService.saveHistoriqueLogin(historiqueLogin);

        return new ResponseEntity<>(HistoriqueLoginResultat, HttpStatus.CREATED);
    }

    @GetMapping(value = APP_ROOT + "/historiqueLogins/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une HistoriqueAppro par ID",
            notes = "Cette méthode permet de chercher un HistoriqueAppro par son ID", response = HistoriqueLogin.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La avec l'id ID HistoriqueLogin a été trouver"),
            @ApiResponse(code = 404, message = "Aucun HistoriqueLogin n'existe avec cette ID pas dans la BD")

    })
    ResponseEntity<HistoriqueLogin> getHistoriqueLoginById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {

        HistoriqueLogin HistoriqueLoginResultat = historiqueLoginService.findHistoriqueLoginById(id).get();

        return new ResponseEntity<>(HistoriqueLoginResultat, HttpStatus.OK);

    }

    @GetMapping(value = APP_ROOT + "/historiqueLogins/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des HistoriqueLogin",
            notes = "Cette méthode permet de chercher et renvoyer la liste des HistoriqueLogin", responseContainer = "List<HistoriqueLogin>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des historiqueLogins / une liste vide")
    })
    ResponseEntity<List<HistoriqueLogin>> getAllHistoriqueLogin() {
        List<HistoriqueLogin> HistoriqueLoginList = historiqueLoginService.findAllHistoriqueLogins();
        return new ResponseEntity<>(HistoriqueLoginList, HttpStatus.OK);

    }

    @GetMapping(value = APP_ROOT + "/historiqueLogins/allHistoriqueLoginOrderDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des HistoriqueLogin",
            notes = "Cette méthode permet de chercher et renvoyer la liste des historiqueLogins", responseContainer = "List<HistoriqueLogin>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des historiqueLogins / une liste vide")
    })
    ResponseEntity<List<HistoriqueLogin>> getAllHistoriqueLoginOrderDesc() {
        List<HistoriqueLogin> HistoriqueLoginList = historiqueLoginService.findAllHistoriqueLoginsOrderDesc();
        return new ResponseEntity<>(HistoriqueLoginList, HttpStatus.OK);
    }

    @GetMapping(value = APP_ROOT + "/historiqueLogins/NumberOfHistoriqueLogin", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi le nombre de HistoriqueLogin",
            notes = "Cette méthode permet de compter le nombre d'historique de connexion")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le nombre d'historique de connexion / une liste vide")
    })
    BigDecimal countNumberOfHistoriqueLogin() {
        return historiqueLoginService.countNumberOfHistoriqueLogins();
    }

    @DeleteMapping(value = APP_ROOT + "/historiqueLogins/delete/{id}")
    @ApiOperation(value = "Supprimer une HistoriqueLogin par son ID",
            notes = "Cette méthode permet de supprimer un HistoriqueLogin par son ID", response = HistoriqueLogin.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La HistoriqueLogin a été supprimé")
    })
    ResponseEntity<?> deleteHistoriqueLogin(@PathVariable(value = "id") Long id) {
        historiqueLoginService.deleteHistoriqueLogin(id);
        return ResponseEntity.ok().build();
    }

}
