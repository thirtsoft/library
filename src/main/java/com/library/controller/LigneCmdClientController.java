package com.library.controller;

import com.library.entities.LigneAvoir;
import com.library.entities.LigneCmdClient;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.LigneCmdClientService;
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
@RequestMapping("/prodApi")
public class LigneCmdClientController {

    @Autowired
    private LigneCmdClientService ligneCmdClientService;

    @GetMapping(value = "/ligneCommandes", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneCmdClient",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneCmdClient", responseContainer = "List<LigneCmdClient>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneCmdClient / une liste vide")
    })
    public List<LigneCmdClient> getAllLigneCmdClients() {
        return ligneCmdClientService.findAllLigneCmdClient();
    }

    @GetMapping(value = APP_ROOT + "/ligneCmdClient/allLigneCmdClientOrderDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneCmdClient",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneCmdClient",
            responseContainer = "List<LigneCmdClient>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneCmdClient / une liste vide")
    })
    ResponseEntity<List<LigneCmdClient>> getAllLigneCmdClientOrderDesc() {
        List<LigneCmdClient> ligneCmdClientList = ligneCmdClientService.findAllLigneCmdClientsOrderDesc();
        return new ResponseEntity<>(ligneCmdClientList, HttpStatus.OK);
    }

    @GetMapping(value = "/ligneCommandes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un LigneCmdClient par ID",
            notes = "Cette méthode permet de chercher une LigneCmdClient par son ID", response = LigneCmdClient.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La LigneCmdClient a été trouver"),
            @ApiResponse(code = 404, message = "Aucun LigneCmdClient n'existe avec cette ID pas dans la BD")

    })
    public ResponseEntity<LigneCmdClient> getLigneCmdClientById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        LigneCmdClient ligneCmdClient = ligneCmdClientService.findLigneCmdClientById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LigneCmdClient that id is" + id + "not found"));
        return ResponseEntity.ok().body(ligneCmdClient);

    }

    @GetMapping(value = "/lcomms/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneCmdClient par numero",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneCmdClient par numero", responseContainer = "List<LigneCmdClient>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneCmdClient numero / une liste vide")
    })
    public List<LigneCmdClient> getAllByNumero(@PathVariable(value = "id") long numero) {
        System.out.println("Get all Lcomms...");

        List<LigneCmdClient> Lcomms = new ArrayList<>();
        ligneCmdClientService.findAllLcomByNumero(numero).forEach(Lcomms::add);

        return Lcomms;
    }

    @GetMapping(value = "/searchListLigneCmdClientByProduitId", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneCmdClient par Produit",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneCmdClient par Produit", responseContainer = "List<LigneCmdClient>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneCmdClient Produit / une liste vide")
    })
    public List<LigneCmdClient> getAllLigneCmdClientByProduitId(@RequestParam("prodId") Long prodId) {
        return ligneCmdClientService.findLigneCmdClientByProduitId(prodId);
    }

    @GetMapping(value = "/searchListLigneCmdClientByCommandeId/{comId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des LigneCmdClient par Commande",
            notes = "Cette méthode permet de chercher et renvoyer la liste des LigneCmdClient par Commande", responseContainer = "List<LigneCmdClient>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des LigneCmdClient Commande / une liste vide")
    })
    public List<LigneCmdClient> getAllLigneCmdClientByCommandeId(@PathVariable("comId") Long comId) {
        return ligneCmdClientService.findLigneCmdClientByCommandeClientId(comId);
    }

    @PostMapping(value = "/ligneCommandes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une LigneCmdClient",
            notes = "Cette méthode permet d'enregistrer et modifier une LigneCmdClient", response = LigneCmdClient.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "La LigneCmdClient a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun LigneCmdClient a été  crée / modifié")

    })
    public ResponseEntity<LigneCmdClient> createLigneCmdClient(@RequestBody LigneCmdClient ligneCmdClient) {
        //	return ligneCmdClientService.saveLigneCmdClient(ligneCmdClient);
        return new ResponseEntity<>(ligneCmdClientService.saveLigneCmdClient(ligneCmdClient), HttpStatus.CREATED);
    }

    @PutMapping(value = "/ligneCommandes/{lcId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier une LigneCmdClient par son ID",
            notes = "Cette méthode permet de modifier une LigneCmdClient par son ID", response = LigneCmdClient.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La LigneCmdClient a été modifié"),
            @ApiResponse(code = 400, message = "Aucun LigneCmdClient n'a été modifié")

    })
    public ResponseEntity<LigneCmdClient> updateLigneCmdClient(@PathVariable(value = "lcId") Long lcId, @RequestBody LigneCmdClient ligneCmdClient) {
        ligneCmdClient.setId(lcId);
        return new ResponseEntity<>(ligneCmdClientService.saveLigneCmdClient(ligneCmdClient), HttpStatus.OK);

    }

    @DeleteMapping(value = "/ligneCommandes/{id}")
    @ApiOperation(value = "Supprimer une LigneCmdClient par son ID",
            notes = "Cette méthode permet de supprimer une LigneCmdClient par son ID", response = LigneCmdClient.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La LigneCmdClient a été supprimé")
    })
    public ResponseEntity<?> deleteLigneCmdClient(@PathVariable(value = "id") Long id) {
        ligneCmdClientService.deleteLigneCmdClient(id);
        return ResponseEntity.ok().build();

    }

}
