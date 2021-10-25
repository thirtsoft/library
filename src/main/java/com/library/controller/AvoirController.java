package com.library.controller;

import com.library.entities.Avoir;
import com.library.entities.Avoir;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.AvoirService;
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

@RestController
@CrossOrigin
//@RequestMapping("/alAmine")
public class AvoirController {

    @Autowired
    private AvoirService avoirService;

    @GetMapping(value = APP_ROOT + "/avoirs/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des avoirs",
            notes = "Cette méthode permet de chercher et renvoyer la liste des avoirs", responseContainer = "List<Avoir>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des avoirs / une liste vide")
    })
    public List<Avoir> getAllAvoirs() {
        return avoirService.findAllAvoirs();
    }

    @GetMapping(value = APP_ROOT + "/avoirs/allAvoirOrderDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Avoir",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Avoir",
            responseContainer = "List<Avoir>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Avoirs / une liste vide")
    })
    ResponseEntity<List<Avoir>> getAllAvoirOrderDesc() {
        List<Avoir> avoirList = avoirService.findAllAvoirsOrderDesc();
        return new ResponseEntity<>(avoirList, HttpStatus.OK);
    }

    @GetMapping(value = APP_ROOT + "/avoirs/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un avoir par ID",
            notes = "Cette méthode permet de chercher un avoir par son ID", response = Avoir.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Avoir a été trouver"),
            @ApiResponse(code = 404, message = "Aucun avoir n'existe avec cette ID pas dans la BD")

    })
    public ResponseEntity<Avoir> getAvoirById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Avoir avoir = avoirService.findAvoirById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avoir Not found"));
        return ResponseEntity.ok().body(avoir);

    }

    @GetMapping(value = APP_ROOT +  "/avoirs/searchAvoirByReference", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un avoir par Reference",
            notes = "Cette méthode permet de chercher un avoir par son Reference", response = Avoir.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Avoir a été trouver"),
            @ApiResponse(code = 404, message = "Aucun avoir n'existe avec cette reference pas dans la BD")

    })
    public Avoir getAvoirByReference(@RequestParam(name = "ref") long reference) {
        return avoirService.findAvoirByReference(reference);
    }

    @GetMapping(value = APP_ROOT + "/avoirs/searchListAvoirsByReference", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoie la liste des avoirs par Reference",
            notes = "Cette méthode permet de chercher et renvoyer une liste d'avoir par son Reference", responseContainer = "List<Avoir>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des avoirs / liste vide")
    })
    public List<Avoir> getAllAvoirsByReference(@RequestParam(name = "ref") long reference) {
        return avoirService.findListAvoirByReference(reference);
    }

    @GetMapping(value = APP_ROOT + "/avoirs/searchAvoirByLibelle")
    public Avoir getAvoirByLibelle(@RequestParam(name = "lib") String libelle) {
        return avoirService.findAvoirByLibelle(libelle);
    }

    @GetMapping(value = APP_ROOT + "/avois/searchListAvoirsByLibelle")
    public List<Avoir> getAllAvoirsByLibelle(@RequestParam(name = "lib") String libelle) {
        return avoirService.findListAvoirByLibelle("%" + libelle + "%");
    }

    @GetMapping(value = APP_ROOT + "/avois/searchListAvoirsByFournisseurId")
    public List<Avoir> getAllAvoirsByFournisseurId(@RequestParam("client") Long fourId) {
        return avoirService.findListAvoirByFournisseurId(fourId);
    }

    @GetMapping(value = APP_ROOT + "/avois/searchAvoirByStatus")
    public Avoir getAvoirByStatus(@RequestParam("status") String status) {
        return avoirService.findByStatus(status);
    }

    @GetMapping("/searchListAvoirByStatus")
    public List<Avoir> getAllAvoirByStatus(@RequestParam("status") String status) {
        return avoirService.findListAvoirByStatus(status);
    }

    @PostMapping(value = APP_ROOT + "/avoirs/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un avoir",
            notes = "Cette méthode permet d'enregistrer un avoir", response = Avoir.class )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "L'avoir a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun avoir  crée / modifié")

    })
    public ResponseEntity<Avoir> createAvoir(@RequestBody Avoir avoir) {

        Avoir avoirResultat = avoirService.saveAvoir(avoir);

        return new ResponseEntity<>(avoirResultat, HttpStatus.CREATED);

    }

    @PutMapping(value = APP_ROOT + "/avoirs/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier un avoir par son ID",
            notes = "Cette méthode permet de modifier un avoir par son ID", response = Avoir.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'avoir a été modifié"),
            @ApiResponse(code = 400, message = "Aucun avoir modifié")
    })
    public ResponseEntity<Avoir> updateAvoir(@PathVariable Long id, @RequestBody Avoir avoir) {
        avoir.setId(id);
        return new ResponseEntity<>(avoirService.saveAvoir(avoir), HttpStatus.OK);
    }

    @DeleteMapping(value = APP_ROOT + "/avoirs/delete/{id}")
    @ApiOperation(value = "Supprimer un avoir par son ID",
            notes = "Cette méthode permet de supprimer un avoir par son ID", response = Avoir.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'Avoir a été supprimé")
    })
    public ResponseEntity<Object> deleteAvoir(@PathVariable(value = "id") Long id) {
        avoirService.deleteAvoir(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = APP_ROOT + "/avoirs/generateReferneceAvoir")
    public long generateReferneceAvoir() {
        return avoirService.generateReferenceAvoir();
    }

}
