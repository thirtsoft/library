package com.library.controller;

import com.library.entities.CategorieCharge;
import com.library.services.CategorieChargeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.library.utils.Constants.APP_ROOT;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CategorieChargeController {

    @Autowired
    private CategorieChargeService catChargeService;

    @GetMapping(value = APP_ROOT + "/categorieCharges/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des category de charge ",
            notes = "Cette méthode permet de chercher et renvoyer la liste des category de charge", responseContainer = "List<CategorieCharge>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des category de charge / une liste vide")
    })
    public List<CategorieCharge> getAllCategorieCharges() {
        return catChargeService.findAllCategorieCharges();
    }

    @GetMapping(value = APP_ROOT + "/categorieCharges/allCategorieChargeOrderDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des CategorieCharge",
            notes = "Cette méthode permet de chercher et renvoyer la liste des CategorieCharge",
            responseContainer = "List<CategorieCharge>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des CategorieCharge / une liste vide")
    })
    ResponseEntity<List<CategorieCharge>> getAllCategorieChargeOrderDesc() {
        List<CategorieCharge> categorieChargeList = catChargeService.findAllCategorieChargesOrderDesc();
        return new ResponseEntity<>(categorieChargeList, HttpStatus.OK);
    }

    @GetMapping(value = APP_ROOT + "/categorieCharges/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un CategorieCharge par ID",
            notes = "Cette méthode permet de chercher un CategorieCharge par son ID", response = CategorieCharge.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CategorieCharge a été trouver"),
            @ApiResponse(code = 404, message = "Aucun CategorieCharge n'existe avec cette ID pas dans la BD")

    })
    public ResponseEntity<CategorieCharge> getCategorieChargeById(@PathVariable(value = "id") Long id) {
        Optional<CategorieCharge> catCharge = catChargeService.findCategorieChargeById(id);
        if (catCharge.isPresent())
            return new ResponseEntity<>(catCharge.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping(value = APP_ROOT + "/categorieCharges/searchCategorieChargesByCode")
    public CategorieCharge getCategorieChargeByCode(@RequestParam(value = "code") String code) {
        return catChargeService.findByCodeCategorieCharge(code);
    }

    @GetMapping(value = APP_ROOT + "/categorieCharges/searchListCategorieChargesByCode", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des category de charge par CODE",
            notes = "Cette méthode permet de chercher et renvoyer la liste des category de charge par CODE", responseContainer = "List<CategorieCharge>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des category de charge / une liste vide")
    })
    public List<CategorieCharge> getAllCategorieChargeByCode(@RequestParam(value = "c") String code) {
        return catChargeService.ListCategoryByCodeCategorieCharge(code);
    }

    @GetMapping(value = APP_ROOT + "/categorieCharges/searchCategorieChargeByDesignation")
    public CategorieCharge getCategoryByDesignation(@RequestParam(value = "des") String designation) {
        return catChargeService.findByNomCategorieCharge(designation);
    }

    @GetMapping(value = "/searchListCategorieChargesByDesignation")
    public List<CategorieCharge> getAllCategoryByDesignation(@RequestParam(value = "des") String designation) {
        return catChargeService.ListCategoryByNomCategorieCharge(designation);
    }

    @PostMapping(value = APP_ROOT + "/categorieCharges/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un CategorieCharge",
            notes = "Cette méthode permet d'enregistrer un CategorieCharge", response = CategorieCharge.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CategorieCharge a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun CategorieCharge  crée / modifié")

    })
    public ResponseEntity<CategorieCharge> createCategorieCharge(@RequestBody CategorieCharge categorieCharge) {
        if (catChargeService.findByCodeCategorieCharge(categorieCharge.getCodeCategorieCharge()) != null) {
            return new ResponseEntity<>(categorieCharge, HttpStatus.BAD_REQUEST);
        }
        catChargeService.saveCategorieCharge(categorieCharge);
        return new ResponseEntity<>(categorieCharge, HttpStatus.CREATED);

    }

    @PutMapping(value = APP_ROOT + "/categorieCharges/update/{catId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier un CategorieCharge par son ID",
            notes = "Cette méthode permet de modifier un CategorieCharge par son ID", response = CategorieCharge.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CategorieCharge a été modifié"),
            @ApiResponse(code = 400, message = "Aucun CategorieCharge modifié")

    })
    public ResponseEntity<CategorieCharge> updateCategorieCharge(@PathVariable Long catId, @RequestBody CategorieCharge catCharge) {
        catCharge.setId(catId);
        return new ResponseEntity<>(catChargeService.updateCategorieCharge(catId, catCharge), HttpStatus.OK);
    }

    @DeleteMapping(value = APP_ROOT + "/categorieCharges/delete/{id}")
    @ApiOperation(value = "Supprimer un CategorieCharge par son ID",
            notes = "Cette méthode permet de supprimer un CategorieCharge par son ID", response = CategorieCharge.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CategorieCharge a été supprimé")
    })
    public ResponseEntity<?> deleteCategorieCharge(@PathVariable(value = "id") Long id) {
        catChargeService.deleteCategorieCharge(id);
        return ResponseEntity.ok().build();
    }


}
