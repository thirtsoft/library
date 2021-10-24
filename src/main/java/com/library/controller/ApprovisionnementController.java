package com.library.controller;

import com.library.entities.Approvisionnement;
import com.library.entities.HistoriqueApprovisionnement;
import com.library.entities.Utilisateur;
import com.library.exceptions.ResourceNotFoundException;
import com.library.security.services.UserPrinciple;
import com.library.services.ApprovisionnementService;
import com.library.services.HistoriqueApprovisionnementService;
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

@RestController
@CrossOrigin
@RequestMapping("/alAmine")
//@Api(APP_ROOT)
public class ApprovisionnementController {

    @Autowired
    private ApprovisionnementService approvisionnementService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private HistoriqueApprovisionnementService historiqueApprovisionnementService;


    @GetMapping(value = "/approvisionnements", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des approvisonnement",
            notes = "Cette méthode permet de chercher et renvoyer la liste des approvisionnements", responseContainer = "List<Approvisionnement>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des approvisionnements / une liste vide")
    })
    public List<Approvisionnement> getAllApprovisionnements() {
        return approvisionnementService.findAllApprovisionnements();

    }

    @GetMapping(value = "/approvisionnement/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un approvisonnement par ID",
            notes = "Cette méthode permet de chercher un approvisionnement par son ID", response = Approvisionnement.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'approvisionnement a été trouver"),
            @ApiResponse(code = 404, message = "Aucun approvisionnement n'existe avec cette ID pas dans la BD")

    })
    public ResponseEntity<Approvisionnement> getApprovisionnementById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Approvisionnement approvisionnement = approvisionnementService.findApprovisionnementById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Approvisionnement that id is" + id + "not found"));
        return ResponseEntity.ok().body(approvisionnement);

    }

    @GetMapping(value = "/searchApprovisionnementByCode", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un approvisonnement par CODE",
            notes = "Cette méthode permet de chercher un approvisionnement par son CODE", response = Approvisionnement.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'approvisionnement a été trouver"),
            @ApiResponse(code = 404, message = "Aucun approvisionnement  n'existe pas dans la BD")

    })
    public Approvisionnement getApprovisionnementByCode(@RequestParam("code") long code) {
        return approvisionnementService.findApprovisionnementByCode(code);
    }

    @GetMapping("/searchListApprovisionnementByFournisseurId")
    public List<Approvisionnement> getListApprovisionnementByFournisseurId(@RequestParam("fourId") Long fourId) {
        return approvisionnementService.findListApprovisionnementByFournisseurId(fourId);
    }

    @PostMapping(value = "/approvisionnements", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un approvisonnement",
            notes = "Cette méthode permet d'enregistrer ou modifier un approvisionnement", response = Approvisionnement.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "L'approvisionnement a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun approvisionnement  crée / modifié")

    })
    public ResponseEntity<Approvisionnement> createApprovisionnement(@RequestBody Approvisionnement approvisionnement) {

        Approvisionnement approvisionnementResultat = approvisionnementService.saveApprovisionnement(approvisionnement);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple authUser = (UserPrinciple) authentication.getPrincipal();

        Optional<Utilisateur> optionalUtilisateur = utilisateurService.findUtilisateurById(authUser.getId());
        Utilisateur utilisateur = optionalUtilisateur.get();

        HistoriqueApprovisionnement historiqueApprovisionnement = new HistoriqueApprovisionnement();

        historiqueApprovisionnement.setUtilisateur(utilisateur);
        historiqueApprovisionnement.setApprovisionnement(approvisionnementResultat);
        historiqueApprovisionnement.setAction("AJOUT");
        historiqueApprovisionnement.setCreatedDate(new Date());

        historiqueApprovisionnementService.saveHistoriqueApprovisionnement(historiqueApprovisionnement);

        return new ResponseEntity<>(approvisionnementResultat, HttpStatus.CREATED);
    }

    @PutMapping(value = "/approvisionnements/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier un approvisonnement par son ID",
            notes = "Cette méthode permet de modifier un approvisionnement par son ID", response = Approvisionnement.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'approvisionnement a été modifié"),
            @ApiResponse(code = 400, message = "Aucun approvisionnement modifié")

    })
    public ResponseEntity<Approvisionnement> updateApprovisionnement(@PathVariable(value = "id") Long ApproId, @RequestBody Approvisionnement approvisionnement) throws Exception {

        approvisionnement.setId(ApproId);

        Approvisionnement approvisionnementResultat = approvisionnementService.saveApprovisionnement(approvisionnement);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple authUser = (UserPrinciple) authentication.getPrincipal();

        Optional<Utilisateur> optionalUtilisateur = utilisateurService.findUtilisateurById(authUser.getId());
        Utilisateur utilisateur = optionalUtilisateur.get();

        HistoriqueApprovisionnement historiqueApprovisionnement = new HistoriqueApprovisionnement();

        historiqueApprovisionnement.setUtilisateur(utilisateur);
        historiqueApprovisionnement.setApprovisionnement(approvisionnementResultat);
        historiqueApprovisionnement.setAction("MODIFICATION");
        historiqueApprovisionnement.setCreatedDate(new Date());

        historiqueApprovisionnementService.saveHistoriqueApprovisionnement(historiqueApprovisionnement);

        return new ResponseEntity<>(approvisionnementResultat, HttpStatus.OK);

    }

    @DeleteMapping(value = "/approvisionnements/{id}")
    @ApiOperation(value = "Supprimer un approvisonnement par son ID",
            notes = "Cette méthode permet de supprimer un approvisionnement par son ID", response = Approvisionnement.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'approvisionnement a été supprimé")
    })
    public ResponseEntity<?> deleteAppro(@PathVariable(value = "id") Long id) {

        Optional<Approvisionnement> optionalApprovisionnement = approvisionnementService.findApprovisionnementById(id);
        Approvisionnement approvisionnement = optionalApprovisionnement.get();

        approvisionnementService.deleteAppro(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple authUser = (UserPrinciple) authentication.getPrincipal();

        Optional<Utilisateur> optionalUtilisateur = utilisateurService.findUtilisateurById(authUser.getId());
        Utilisateur utilisateur = optionalUtilisateur.get();

        HistoriqueApprovisionnement historiqueApprovisionnement = new HistoriqueApprovisionnement();

        historiqueApprovisionnement.setUtilisateur(utilisateur);
        historiqueApprovisionnement.setApprovisionnement(approvisionnement);
        historiqueApprovisionnement.setAction("SUPPRESSION");
        historiqueApprovisionnement.setCreatedDate(new Date());

        historiqueApprovisionnementService.saveHistoriqueApprovisionnement(historiqueApprovisionnement);

        return ResponseEntity.ok().build();

    }

    @GetMapping(value = "/generateCodeAppro")
    @ApiOperation(value = "Générer le code d'un approvisonnement",
            notes = "Cette méthode permet de générer automatiquement le code d'un approvisionnement")
    public long generateCodeApprovisionnement() {
        return approvisionnementService.generateCodeApprovisionnement();
    }

    @PatchMapping("/updateStatusApproById/{id}")
    @ApiOperation(value = "Modifier un approvisonnement par son Status",
            notes = "Cette méthode permet de modifier un approvisionnement par son Status", response = Approvisionnement.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le status de l'approvisionnement a été modifié")
    })
    public ResponseEntity<?> updateStatusAppro(@RequestParam("status") String status, @PathVariable("id") String id) {
        Approvisionnement newApprovisionnement = approvisionnementService.updateStatusAppro(status, id);
        return new ResponseEntity<>(newApprovisionnement, HttpStatus.OK);
    }

    @PatchMapping("/updateMontantAvanceApproById/{id}")
    @ApiOperation(value = "Modifier un approvisonnement par son ID",
            notes = "Cette méthode permet de modifier un approvisionnement par son ID", response = Approvisionnement.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'approvisionnement a été modifié")
    })
    public ResponseEntity<?> updateMontantAvanceAppro(@RequestParam("montantAvance") double montantAvance, @PathVariable("id") String id) {
        Approvisionnement newApprovisionnement = approvisionnementService.updateMontantAvanceAppro(montantAvance, id);
        return new ResponseEntity<>(newApprovisionnement, HttpStatus.OK);
    }

}
