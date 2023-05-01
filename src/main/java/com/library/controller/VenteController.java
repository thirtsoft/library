package com.library.controller;

import com.library.entities.HistoriqueVente;
import com.library.entities.Utilisateur;
import com.library.entities.Vente;
import com.library.exceptions.ResourceNotFoundException;
import com.library.security.services.UserPrinciple;
import com.library.services.HistoriqueVenteService;
import com.library.services.UtilisateurService;
import com.library.services.VenteService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.library.utils.Constants.APP_ROOT;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class VenteController {

    @Autowired
    private VenteService venteService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private HistoriqueVenteService historiqueVenteService;

    @GetMapping(value = APP_ROOT + "/ventes/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Vente",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Vente", responseContainer = "List<Vente>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Vente / une liste vide")
    })
    public List<Vente> getAllVentes() {
        return venteService.findAllVentes();
    }

    @GetMapping(value = APP_ROOT + "/ventes/allVenteOrderDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Vente par ordre descroissante",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Vente par ordre descroissante",
            responseContainer = "List<Vente>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Vente / une liste vide")
    })
    ResponseEntity<List<Vente>> getAllVenteOrderDesc() {
        List<Vente> venteList = venteService.findAllVentesOrderDesc();
        return new ResponseEntity<>(venteList, HttpStatus.OK);
    }

    @GetMapping(value = APP_ROOT + "/ventes/allVenteOf3LatestMonths", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Vente des trois derniers mois",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Vente des trois derniers mois",
            responseContainer = "List<Vente>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Vente / une liste vide")
    })
    ResponseEntity<List<Vente>> getAllVenteOf3LatestMonths() {
        List<Vente> venteList = venteService.findListVenteOf3LatestMonth();
        return new ResponseEntity<>(venteList, HttpStatus.OK);
    }

    @GetMapping(value = APP_ROOT + "/ventes/findTop500OfVenteOrderByIdDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des 500 derniers Vente",
            notes = "Cette méthode permet de chercher et renvoyer la liste des 500 derniers Vente",
            responseContainer = "List<Vente>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Vente / une liste vide")
    })
    ResponseEntity<List<Vente>> getTop500OfVenteOrderByIdDesc() {
        List<Vente> venteList = venteService.findTop500OfVenteOrderByIdDesc();
        return new ResponseEntity<>(venteList, HttpStatus.OK);
    }


    @GetMapping(value = APP_ROOT + "/ventes/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Vente par ID",
            notes = "Cette méthode permet de chercher une Vente par son ID", response = Vente.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La Vente a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Vente n'existe avec cette ID pas dans la BD")

    })
    public ResponseEntity<Vente> getVenteById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Vente vente = venteService.findVenteById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vente Not found"));
        return ResponseEntity.ok().body(vente);

    }

    @GetMapping(value = APP_ROOT + "/ventes/searchVenteByNumeroVente", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Vente par numero",
            notes = "Cette méthode permet de chercher une Vente par son numero", response = Vente.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La Vente avec ce numero a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Vente n'existe avec ce numero pas dans la BD")

    })
    public Vente getVenteByNumeroVente(@RequestParam("num") Long numeroVente) {
        return venteService.findVenteByNumeroVente(numeroVente);
    }

    @GetMapping(value = APP_ROOT + "/ventes/NumberOfVente")
    @ApiOperation(value = "Compter le nombre de Vente",
            notes = "Cette méthode permet de rechercher et retourner le nombre total Vente"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le nombre total de  Vente / zéro")
    })
    public int getNumberOfVentes() {
        return venteService.getNumberOfVente();
    }

    @GetMapping(value = APP_ROOT + "/ventes/NumberOfVenteByDay")
    @ApiOperation(value = "Compter le nombre de Vente par jour",
            notes = "Cette méthode permet de rechercher et retourner le nombre total Vente par jour"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le nombre total de  Vente par jour / zéro")
    })
    public int getNumberOfVenteByDay() {
        return venteService.countNumberOfVenteByDay();
    }

    @GetMapping(value = APP_ROOT + "/ventes/SumsOfVentes")
    @ApiOperation(value = "Additionner le montant total de Vente",
            notes = "Cette méthode permet de rechercher et retourner le montant total Vente"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le montant total total de  Vente / zéro")
    })
    public BigDecimal getSumsOfVentes() {
        return venteService.countSumsOfVentess();
    }

    @GetMapping(value = APP_ROOT + "/ventes/SumsOfVentesByMonth")
    @ApiOperation(value = "Renvoi le montant total de Vente par mois",
            notes = "Cette méthode permet de rechercher et retourner le montant total de Vente par mois"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le montant total total de  Vente par mois / zéro")
    })
    public BigDecimal sumTotalOfVentesByMonth() {
        return venteService.sumTotalOfVentesByMonth();
    }

    @GetMapping(value = APP_ROOT + "/ventes/SumsOfVentesByYear")
    @ApiOperation(value = "Renvoi le montant total de Vente par années",
            notes = "Cette méthode permet de rechercher et retourner le montant total de Vente par années"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le montant total total de  Vente par années / zéro")
    })
    public BigDecimal sumTotalOfVentesByYear() {
        return venteService.sumTotalOfVentesByYear();
    }

    @GetMapping(value = APP_ROOT + "/ventes/generateNumeroVente")
    @ApiOperation(value = "Générer le numéro de Vente",
            notes = "Cette méthode permet de générer automatiquement le numero de Vente"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le le numero de Vente a été génégé avec succès")
    })
    public long generateNumeroVente() {
        return venteService.generateNumeroVente();
    }

    @GetMapping(value = APP_ROOT + "/ventes/searchVenteByStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une Vente par Status",
            notes = "Cette méthode permet de chercher une Vente par son status", response = Vente.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La Vente a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Vente n'existe avec cette ID pas dans la BD")

    })
    public Vente getVenteByStatus(@RequestParam("status") String status) {
        return venteService.findByStatus(status);
    }

    @PostMapping(value = APP_ROOT + "/ventes/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une Vente",
            notes = "Cette méthode permet d'enregistrer une Vente", response = Vente.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "La Vente a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun Vente  crée / modifié")

    })
    public ResponseEntity<Vente> createVente(@RequestBody Vente vente, @RequestParam Long id) {

        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple authUser = (UserPrinciple) authentication.getPrincipal();*/
        Utilisateur userInfo = utilisateurService.findUtilisateurById(id).get();

        Vente venteResultat;

        vente.setUtilisateur(userInfo);
        vente.setNumeroVente(this.generateNumeroVente());

        venteResultat = venteService.saveVente(vente);

        HistoriqueVente historiqueVente = new HistoriqueVente();

        historiqueVente.setUtilisateur(userInfo);
        historiqueVente.setVente(venteResultat);
        historiqueVente.setAction("AJOUT VENTE");
        historiqueVente.setCreatedDate(new Date());

        historiqueVenteService.saveHistoriqueVente(historiqueVente);

        return new ResponseEntity<>(venteResultat, HttpStatus.CREATED);
    }

    @PostMapping(value = APP_ROOT + "/ventes/venteWithbarCode", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une Vente avec lecteur code-barres",
            notes = "Cette méthode permet d'enregistrer une Vente en utilisant un lecteur code-barres", response = Vente.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "La Vente a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun Vente  crée / modifié")

    })
    public ResponseEntity<Vente> saveVenteWithBarcode(@RequestBody Vente vente, @RequestParam Long id) {

        Vente venteResultat;

        Utilisateur userInfo = utilisateurService.findUtilisateurById(id).get();

        vente.setUtilisateur(userInfo);
        vente.setNumeroVente(this.generateNumeroVente());

        venteResultat = venteService.saveVenteWithBarcode(vente);


        //    venteService.saveVenteWithBarcode(vente);

        HistoriqueVente historiqueVente = new HistoriqueVente();

        historiqueVente.setUtilisateur(userInfo);
        historiqueVente.setVente(venteResultat);
        historiqueVente.setAction("AJOUT VENTE");
        historiqueVente.setCreatedDate(new Date());

        historiqueVenteService.saveHistoriqueVente(historiqueVente);

        return new ResponseEntity<>(venteResultat, HttpStatus.CREATED);
    }

    @PutMapping(value = APP_ROOT + "/ventes/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier une Vente par son ID",
            notes = "Cette méthode permet de modifier une Vente par son ID", response = Vente.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La Vente par son ID a été modifié"),
            @ApiResponse(code = 400, message = "Aucun Vente  avec cet id ID n'a été modifié")

    })
    public ResponseEntity<Vente> updateVente(@PathVariable(value = "id") Long id, @RequestBody Vente vente) throws Exception {

        Vente ventedResult;

        vente.setId(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple authUser = (UserPrinciple) authentication.getPrincipal();

        Optional<Utilisateur> optionalUtilisation = utilisateurService.findUtilisateurById(authUser.getId());
        Utilisateur utilisateur = optionalUtilisation.get();

        ventedResult = venteService.saveVente(vente);

        HistoriqueVente historiqueVente = new HistoriqueVente();
        historiqueVente.setUtilisateur(utilisateur);
        historiqueVente.setVente(ventedResult);
        historiqueVente.setCreatedDate(new Date());
        historiqueVente.setAction("MODIFICATION VENTE");

        historiqueVenteService.saveHistoriqueVente(historiqueVente);

        return new ResponseEntity<>(ventedResult, HttpStatus.OK);

    }

    @DeleteMapping(value = APP_ROOT + "/ventes/delete/{id}")
    @ApiOperation(value = "Supprimer un Vente par son ID",
            notes = "Cette méthode permet de supprimer un Vente par son ID", response = Vente.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La Vente été supprimé")
    })
    public ResponseEntity<?> deleteVente(@PathVariable(value = "id") Long id) {

        Optional<Vente> optionalVent = venteService.findVenteById(id);
        Vente ventedResult = optionalVent.get();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple authUser = (UserPrinciple) authentication.getPrincipal();

        Optional<Utilisateur> optionalUtilisation = utilisateurService.findUtilisateurById(authUser.getId());
        Utilisateur utilisateur = optionalUtilisation.get();

        HistoriqueVente historiqueVente = new HistoriqueVente();
        historiqueVente.setUtilisateur(utilisateur);
        historiqueVente.setVente(ventedResult);
        historiqueVente.setCreatedDate(new Date());
        historiqueVente.setAction("SUPPRESSION UNE VENTE");

        historiqueVenteService.saveHistoriqueVente(historiqueVente);

        venteService.deleteVente(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = APP_ROOT + "/ventes/searchVenteWithParticularDayAndMonth", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Vente par jour et mois",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Vente par jour et mois", responseContainer = "List<Vente>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Ventes par jour et mois / une liste vide")
    })
    public List<Vente> getVenteWithParticularDayAndMonth() {
        return venteService.findVenteWithParticularDayAndMonth();
    }

    @GetMapping(value = APP_ROOT + "/ventes/searchNumberOfVenteByMonth")
    @ApiOperation(value = "Compter le nombre de Vente par mois",
            notes = "Cette méthode permet de chercher et renvoyer le nombre de Vente par mois")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La nombre de Vente par mois / une liste vide")
    })
    public List<?> getNumberTotalOfVenteByMonth() {
        return venteService.countNumberTotalOfVenteByMonth();
    }

    @GetMapping(value = APP_ROOT + "/ventes/searchSumVenteByMonth")
    @ApiOperation(value = "Compter la somme des Ventes par mois",
            notes = "Cette méthode permet de chercher et renvoyer la montant total des Ventes par mois")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le montant total de Vente par mois / une liste vide")
    })
    public List<?> getSumTotalOfVenteByMonth() {
        return venteService.sumTotalOfVenteByMonth();
    }

    @GetMapping(value = APP_ROOT + "/ventes/searchSumVenteByYears")
    @ApiOperation(value = "Compter la somme des Ventes par années",
            notes = "Cette méthode permet de chercher et renvoyer la montant total des Ventes par années")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le montant total de Vente par années / une liste vide")
    })
    public List<?> getSumTotalOfVenteByYears() {
        return venteService.sumTotalOfVenteByYears();
    }

    @GetMapping(value = APP_ROOT + "/ventes/searchSumsOfVenteByDay")
    @ApiOperation(value = "Compter la somme des Ventes par jours",
            notes = "Cette méthode permet de chercher et renvoyer la montant total des Ventes par jours")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le montant total de Vente par jours / une liste vide")
    })
    public BigDecimal getSumsOfVenteByDay() {
        return venteService.sumTotalOfVenteByDay();
    }

    @GetMapping(value = APP_ROOT + "/ventes/searchListVenteByEmpId/{empId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Vente par Client",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Vente par client", responseContainer = "List<Vente>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Ventes par Client / une liste vide")
    })
    public List<Vente> findListVenteByEmployeId(@PathVariable Long empId) {
        return venteService.findListVenteByEmployeId(empId);
    }

}
