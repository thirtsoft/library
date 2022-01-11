package com.library.controller;

import com.library.entities.Charge;
import com.library.entities.HistoriqueCharge;
import com.library.entities.Utilisateur;
import com.library.security.services.UserPrinciple;
import com.library.services.ChargeService;
import com.library.services.HistoriqueChargeService;
import com.library.services.UtilisateurService;
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

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.library.utils.Constants.APP_ROOT;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ChargeController {

    @Autowired
    private ChargeService chargeService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private HistoriqueChargeService historiqueChargeService;

    @GetMapping(value = APP_ROOT + "/charges/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Charges",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Charge", responseContainer = "List<Charge>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Charge / une liste vide")
    })
    public List<Charge> getAllCharges() {
        return chargeService.findAllCharges();
    }

    @GetMapping(value = APP_ROOT + "/charges/allChargeOrderDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Charge",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Charge",
            responseContainer = "List<Charge>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Charge / une liste vide")
    })
    ResponseEntity<List<Charge>> getAllChargeOrderDesc() {
        List<Charge> chargeList = chargeService.findAllChargesOrderDesc();
        return new ResponseEntity<>(chargeList, HttpStatus.OK);
    }

    @GetMapping(value = APP_ROOT + "/charges/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Charge par ID",
            notes = "Cette méthode permet de chercher une Charge par son ID", response = Charge.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La Charge a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Charge n'existe avec cette ID pas dans la BD")

    })
    public Optional<Charge> getChargeById(@PathVariable(value = "id") Long id) {
        return chargeService.findChargeById(id);
    }

    @GetMapping(value = APP_ROOT + "/charges/searchChargeByCodeCharge", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Charge par CODE",
            notes = "Cette méthode permet de chercher une Charge par son CODE", response = Charge.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La Charge a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Charge n'existe avec ce CODE pas dans la BD")

    })
    public Charge getChargeByCodeCharge(@RequestParam(name = "ref") String codeCharge) {
        return chargeService.findChargeByCodeCharge(codeCharge);
    }

    @GetMapping(value = APP_ROOT + "/charges/searchListChargesByCodeCharge")
    public List<Charge> getAllChargesByReference(@RequestParam(name = "ref") String codeCharge) {
        return chargeService.findListChargeByCodeCharge("%" + codeCharge + "%");
    }

    @GetMapping(value = APP_ROOT + "/charges/searchChargeByNature")
    public Charge getChargeByNature(@RequestParam(name = "nat") String nature) {
        return chargeService.findChargeByNature(nature);
    }

    @GetMapping(value = APP_ROOT + "/charges/searchListChargesByNature")
    public List<Charge> getAllChargesByNature(@RequestParam(name = "nat") String nature) {
        return chargeService.findListChargeByNature("%" + nature + "%");
    }

    @GetMapping(value = APP_ROOT + "/charges/sumMontantTotalChargeByMonth")
    @ApiOperation(value = "Renvoi le montant total des Charges par month",
            notes = "Cette méthode permet de chercher et le montant total des Charges par month", responseContainer = "List<Charge>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le montant total des Charges par month / une liste vide")
    })
    public List<?> sumTotalOfChargeByMonth() {
        return chargeService.sumTotalOfChargeByMonth();
    }

    @PostMapping(value = APP_ROOT + "/charges/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une Charge",
            notes = "Cette méthode permet d'enregistrer un Charge", response = Charge.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "La Charge a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun Charge  crée / modifié")
    })
    public ResponseEntity<Charge> createCharge(@RequestBody Charge charge) {
        if (chargeService.findChargeByCodeCharge(charge.getCodeCharge()) != null) {
            return new ResponseEntity<>(charge, HttpStatus.BAD_REQUEST);
        }
        Charge chargeResultat;
        String ch = "CH_" + Math.random()*10;
        charge.setCodeCharge(ch);
        chargeResultat = chargeService.saveCharge(charge);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple authUser = (UserPrinciple) authentication.getPrincipal();

        Optional<Utilisateur> optionalUtilisateur = utilisateurService.findUtilisateurById(authUser.getId());
        Utilisateur utilisateur = optionalUtilisateur.get();

        HistoriqueCharge historiqueCharge = new HistoriqueCharge();
        historiqueCharge.setUtilisateur(utilisateur);
        historiqueCharge.setCharge(chargeResultat);
        historiqueCharge.setCreatedDate(new Date());
        historiqueCharge.setAction("AJOUT");

        historiqueChargeService.saveHistoriqueCharge(historiqueCharge);

        return new ResponseEntity<>(chargeResultat, HttpStatus.CREATED);
    }

    @PutMapping(value = APP_ROOT + "/charges/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier une Charge par son ID",
            notes = "Cette méthode permet de modifier une Charge par son ID", response = Charge.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "La Charge a été modifié"),
            @ApiResponse(code = 400, message = "Aucun Charge modifié")

    })
    public ResponseEntity<Charge> updateCharge(@PathVariable Long id, @RequestBody Charge charge) {
        String ch = "CH_" + Math.random()*10;
        charge.setCodeCharge(ch);
        charge.setId(id);
        Charge chargeResultat = chargeService.updateCharge(id, charge);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple authUser = (UserPrinciple) authentication.getPrincipal();

        Optional<Utilisateur> optionalUtilisateur = utilisateurService.findUtilisateurById(authUser.getId());
        Utilisateur utilisateur = optionalUtilisateur.get();

        HistoriqueCharge historiqueCharge = new HistoriqueCharge();
        historiqueCharge.setUtilisateur(utilisateur);
        historiqueCharge.setCharge(chargeResultat);
        historiqueCharge.setCreatedDate(new Date());
        historiqueCharge.setAction("MODIFICATION");

        historiqueChargeService.saveHistoriqueCharge(historiqueCharge);

        return new ResponseEntity<>(chargeResultat, HttpStatus.OK);

    }

    @DeleteMapping(value = APP_ROOT + "/charges/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Supprimer un Charge par son ID",
            notes = "Cette méthode permet de supprimer un Charge par son ID", response = Charge.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La Charge a été supprimé")
    })
    public ResponseEntity<?> deleteCharge(@PathVariable(value = "id") Long id) {

        Charge chargeResultat = chargeService.findChargeById(id).get();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple authUser = (UserPrinciple) authentication.getPrincipal();

        Optional<Utilisateur> optionalUtilisateur = utilisateurService.findUtilisateurById(authUser.getId());
        Utilisateur utilisateur = optionalUtilisateur.get();

        HistoriqueCharge historiqueCharge = new HistoriqueCharge();
        historiqueCharge.setUtilisateur(utilisateur);
        historiqueCharge.setCharge(chargeResultat);
        historiqueCharge.setCreatedDate(new Date());
        historiqueCharge.setAction("SUPPRESSION");

        historiqueChargeService.saveHistoriqueCharge(historiqueCharge);

        chargeService.deleteCharge(id);

        return ResponseEntity.ok().build();
    }

}
