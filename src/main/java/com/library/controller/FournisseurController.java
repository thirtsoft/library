package com.library.controller;

import com.library.entities.Fournisseur;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.FournisseurService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.library.utils.Constants.APP_ROOT;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class FournisseurController {

    @Autowired
    private FournisseurService fournisseurService;

    @GetMapping(value = APP_ROOT + "/fournisseurs/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Fournisseur",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Fournisseur", responseContainer = "List<Fournisseur>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Fournisseur / une liste vide")
    })
    public List<Fournisseur> getAllFournisseurs() {
        return fournisseurService.findAllFournisseurs();
    }

    @GetMapping(value = APP_ROOT + "/fournisseurs/allFournisseurOrderDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Fournisseur",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Fournisseur",
            responseContainer = "List<Fournisseur>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Fournisseur / une liste vide")
    })
    ResponseEntity<List<Fournisseur>> getAllFournisseurOrderDesc() {
        List<Fournisseur> fournisseurList = fournisseurService.findAllFournisseursOrderDesc();
        return new ResponseEntity<>(fournisseurList, HttpStatus.OK);
    }

    @GetMapping(value = APP_ROOT + "/fournisseurs/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Fournisseur par ID",
            notes = "Cette méthode permet de chercher une Fournisseur par son ID", response = Fournisseur.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Fournisseur a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Fournisseur n'existe avec cette ID pas dans la BD")

    })
    public ResponseEntity<Fournisseur> getFournisseurById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Fournisseur fournisseur = fournisseurService.findProduitById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur that id is" + id + "not found"));
        return ResponseEntity.ok().body(fournisseur);

    }

    @GetMapping(value = APP_ROOT + "/fournisseurs/serachFournisseurByEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Fournisseur par EMAIL",
            notes = "Cette méthode permet de chercher une Fournisseur par son EMAIL", response = Fournisseur.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Fournisseur dont l'email est EMAIL a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Fournisseur n'existe avec l'email EMAIL pas dans la BD")

    })
    public Fournisseur getFournisseurByEmail(String email) {
        return fournisseurService.findByEmail(email);
    }

    @GetMapping(value = APP_ROOT + "/fournisseurs/searchFournisseurByCode", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Fournisseur par CODE",
            notes = "Cette méthode permet de chercher une Fournisseur par son CODE", response = Fournisseur.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Fournisseur dont le code est CODE a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Fournisseur n'existe avec le code CODE pas dans la BD")

    })
    public Fournisseur getFournisseurByCode(@RequestParam(value = "code") String code) {
        return fournisseurService.findByCode(code);
    }

    @GetMapping(value = APP_ROOT + "/fournisseurs/countFournisseurs")
    @ApiOperation(value = "Compter le nombre de Fournisseur",
            notes = "Cette méthode permet de compter le nombre total de Fournisseur")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le nombre total de Fournisseur / z&ro")
    })
    public Integer countNumberOfFournisseurs() {
        return fournisseurService.countNumberOfFournisseurs();
    }

    @GetMapping(value = APP_ROOT + "/fournisseurs/searchListFournisseurByCode", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste de Fournisseur par CODE",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Fournisseurs par CODE")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Fournisseurs par CODE / liste vide")
    })
    public List<Fournisseur> getAllFournisseurByCode(@RequestParam(value = "c") String code) {
        return fournisseurService.findListFournisseurByCode("%" + code + "%");
    }

    @GetMapping(value = APP_ROOT + "/fournisseurs/searchFournisseurByRaisonSocial")
    public Fournisseur getFournisseurByRaisonSocial(@RequestParam(value = "raison") String raisonSocial) {
        return fournisseurService.findByRaisonSociale(raisonSocial);

    }

    @GetMapping(value = APP_ROOT + "/fournisseurs/searchListFournisseurByRaisonSocial")
    public List<Fournisseur> getAllFournisseurByRaisonSocial(@RequestParam(value = "raison") String raisonSocial) {
        return fournisseurService.findListFournisseurByRaisonSociale("%" + raisonSocial + "%");
    }


    @PostMapping(value = APP_ROOT + "/fournisseurs/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un Fournisseur",
            notes = "Cette méthode permet d'enregistrer et modifier un Fournisseur", response = Fournisseur.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Le Fournisseur a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun Fournisseur a été crée / modifié")

    })
    public ResponseEntity<Fournisseur> createFournisseur(@RequestBody Fournisseur fournisseur) {
        fournisseurService.saveFournisseur(fournisseur);
        return new ResponseEntity<>(fournisseur, HttpStatus.CREATED);
    }

    @PutMapping(value = APP_ROOT + "/fournisseurs/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier un Fournisseur par son ID",
            notes = "Cette méthode permet de modifier un Fournisseur par son ID", response = Fournisseur.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Fournisseur avec l'id ID a été modifié"),
            @ApiResponse(code = 400, message = "Aucun Fournisseur n'a été modifié")

    })
    public ResponseEntity<Fournisseur> updateFournisseur(@PathVariable(value = "id") Long id, @RequestBody Fournisseur fournisseur) {
        fournisseur.setId(id);
        return new ResponseEntity<>(fournisseurService.updateFournisseurt(id, fournisseur), HttpStatus.OK);

    }

    @DeleteMapping(value = APP_ROOT + "/fournisseurs/delete/{id}")
    @ApiOperation(value = "Supprimer un Fournisseur par son ID",
            notes = "Cette méthode permet de supprimer un Fournisseur par son ID", response = Fournisseur.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Fournisseur avec l'id ID a été supprimé")
    })
    public ResponseEntity<?> deleteFournisseur(@PathVariable(value = "id") Long id) {
        fournisseurService.deleteFournisseur(id);
        return ResponseEntity.ok().build();
    }

}
