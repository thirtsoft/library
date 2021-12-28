package com.library.controller;

import com.library.entities.Devis;
import com.library.entities.Utilisateur;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.DevisService;
import com.library.services.UtilisateurService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.library.utils.Constants.APP_ROOT;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class DevisController {

    private final Double total = 0.0;
    @Autowired
    private DevisService devisService;
    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping(value = APP_ROOT + "/devis/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Devis",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Devis", responseContainer = "List<Devis>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Devis / une liste vide")
    })
    public List<Devis> getAllDevis() {
        return devisService.findAllDevis();
    }

    @GetMapping(value = APP_ROOT + "/devis/allDevisOrderDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Devis",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Devis",
            responseContainer = "List<Devis>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Devis / une liste vide")
    })
    ResponseEntity<List<Devis>> getAllDevisOrderDesc() {
        List<Devis> devisList = devisService.findAllDevissOrderDesc();
        return new ResponseEntity<>(devisList, HttpStatus.OK);
    }

    @GetMapping(value = APP_ROOT + "/devis/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Devis par ID",
            notes = "Cette méthode permet de chercher un Devis par son ID", response = Devis.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Devis a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Devis n'existe avec cette ID pas dans la BD")

    })
    public ResponseEntity<Devis> getDevisById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Devis devis = devisService.findDevisById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Devis not found"));
        return ResponseEntity.ok().body(devis);

    }

    @GetMapping(value = APP_ROOT + "/devis/searchDevisByNumeroDevis", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Devis par numeroDevis",
            notes = "Cette méthode permet de chercher un Devis par le numeroDevis", response = Devis.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Devis avec le numero numeroDevis a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Devis n'existe avec cette ID pas dans la BD")

    })
    public Devis getDevisByNumeroDevis(@RequestParam("num") Long numeroDevis) {
        return devisService.findByNumeroDevis(numeroDevis);
    }

    @GetMapping(value = APP_ROOT + "/devis/NumberOfDevis")
    public int getNumberOfdevis() {
        return devisService.getNumberOfDevis();
    }

    @GetMapping(value = "/NumbersOfdevis")
    @ApiOperation(value = "Compter le nombre de Devis",
            notes = "Cette méthode permet de compter le nombre total de Devis"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Nombre total de Devis / zéro")
    })
    public BigDecimal getNumbersOfdevis() {
        return devisService.countNumbersOfDevis();
    }


    @GetMapping(value = APP_ROOT + "/devis/searchListDevisByClientId", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Devis par CLIENT",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Devis par CLIENT", responseContainer = "List<Devis>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Devis par CLIENT / une liste vide")
    })
    public List<Devis> getAllDevisByClientId(@RequestParam("clientId") Long clientId) {
        return devisService.findDevisByClientId(clientId);
    }

    @GetMapping(value = APP_ROOT + "/devis/getAllDeviswithdate")
    public List<Devis> getAlldevisWithDatet(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateCommande) {

        return devisService.findDevisByDate(dateCommande);
    }

    @PostMapping(value = APP_ROOT + "/devis/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un Devis",
            notes = "Cette méthode permet d'enregistrer un Devis", response = Devis.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Le Devis a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun Devis  crée / modifié")

    })
    public ResponseEntity<Devis> createDevis(@RequestBody Devis devis, @RequestParam Long id) {

        Utilisateur utilisateur = utilisateurService.findUtilisateurById(id).get();

        devis.setUtilisateur(utilisateur);

        return new ResponseEntity<>(devisService.saveDevis(devis), HttpStatus.CREATED);
    }

    @PutMapping(value = APP_ROOT + "/devis/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier un Devis par son ID",
            notes = "Cette méthode permet de modifier un Devis par son ID", response = Devis.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Devis avec cet ID a été modifié"),
            @ApiResponse(code = 400, message = "Aucun Devis  modifié avec cet ID")

    })
    public ResponseEntity<Devis> updateDevis(@PathVariable(value = "id") Long id, @RequestBody Devis devis) throws Exception {
        devis.setId(id);
        return new ResponseEntity<>(devisService.saveDevis(devis), HttpStatus.OK);

    }

    @DeleteMapping(value = APP_ROOT + "/devis/delete/{id}")
    @ApiOperation(value = "Supprimer un Devis par son ID",
            notes = "Cette méthode permet de supprimer un Devis par son ID", response = Devis.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Devis a été supprimé")
    })
    public ResponseEntity<?> deleteDevis(@PathVariable(value = "id") Long id) {
        devisService.deleteDevis(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = APP_ROOT + "/devis/searchNumberOfDevisByMonth")
    @ApiOperation(value = "Renvoi le nombre de devis par mois",
            notes = "Cette méthode permet de compter et renvoyer le nombre de Devis par mois")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Nombre de Devis par mois / zéro")
    })
    public List<?> getNumberTotalOfDevisByMonth() {
        return devisService.countNumberTotalOfDevisByMonth();
    }

    @GetMapping(value = APP_ROOT + "/devis/searchSumDevisByMonth")
    @ApiOperation(value = "Renvoi le montant de devis par mois",
            notes = "Cette méthode permet de renvoyer le montant total de Devis par mois")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Montant total de Devis par mois / zéro")
    })
    public List<?> getSumTotalOfDevisByMonth() {
        return devisService.sumTotalOfDevisByMonth();
    }

    @GetMapping(value = APP_ROOT + "/devis/generateNumeroDevis")
    @ApiOperation(value = "Générer le numero de devis",
            notes = "Cette méthode permet de générer de façon automatique le numero de Devis par mois")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le numero de Devis par mois / zéro")
    })
    public long generateNumeroDevis() {
        return devisService.generateNumeroDevis();
    }


}
