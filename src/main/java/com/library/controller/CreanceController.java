package com.library.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.library.entities.Creance;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.CreanceService;
import com.library.services.LigneCreanceService;
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
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/alAmine")
public class CreanceController {

    @Autowired
    private CreanceService creanceService;

    @Autowired
    private LigneCreanceService ligneCreanceService;

    @GetMapping(value = "/creances", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Creance",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Creance", responseContainer = "List<Creance>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Creance / une liste vide")
    })
    public List<Creance> getAllCreances() {
        return creanceService.findAllCreances();

    }

    @GetMapping(value = "/creances/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une Creance par ID",
            notes = "Cette méthode permet de chercher une Creance par son ID", response = Creance.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La Creance a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Creance n'existe avec cette ID pas dans la BD")

    })
    public ResponseEntity<Creance> getCreanceById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Creance creance = creanceService.findCreanceById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande not found"));
        return ResponseEntity.ok().body(creance);

    }

    @GetMapping(value = "/searchCreanceByReference", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une Creance par REFERENCE",
            notes = "Cette méthode permet de chercher une Creance par son REFERENCE", response = Creance.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La Creance a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Creance n'existe avec cette REFERENCE pas dans la BD")

    })
    public Optional<Creance> getCreanceByReference(@RequestParam("num") long reference) {
        return creanceService.findByReference(reference);
    }

    @PostMapping(path = "/updateStatus")
    public ResponseEntity<Boolean> updateStatus(@RequestBody ObjectNode json) {
        Long reference;
        String ref;
        String status;
        try {
            ref = new ObjectMapper().treeToValue(json.get("ref"), String.class);
            reference = Long.parseLong(ref);
            status = new ObjectMapper().treeToValue(json.get("status"), String.class);
            boolean test = this.creanceService.updateStatus(reference, status);
            if (test)
                return new ResponseEntity<>(test, HttpStatus.OK);

        } catch (JsonProcessingException e) {
            System.out.println("Parsing Exception!!");
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);

        }

        return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);

    }

    @PostMapping(path = "/updateStatusCreance", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier une Creance par status",
            notes = "Cette méthode permet de modifier une Creance par son status", response = Creance.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le status de la  Creance a été modifié"),
            @ApiResponse(code = 404, message = "Aucun le status de la Creance n'a pas été modifié")

    })
    public ResponseEntity<Boolean> updateStatusCreance(@RequestBody ObjectNode json) {
        String codeCreance;
        String status;
        try {
            codeCreance = new ObjectMapper().treeToValue(json.get("codeCreance"), String.class);
            status = new ObjectMapper().treeToValue(json.get("status"), String.class);
            boolean test = this.creanceService.updateStatusCreance(codeCreance, status);
            if (test)
                return new ResponseEntity<>(test, HttpStatus.OK);

        } catch (JsonProcessingException e) {
            System.out.println("Parsing Exception!!");
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);

        }

        return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);

    }

    @GetMapping(value = "/NumberOfCreances")
    @ApiOperation(value = "Compter le nombre de Creance",
            notes = "Cette méthode permet de compter le nombre total de Creance"
    )
    public int getNumberOfCreances() {
        return creanceService.getNumberOfCreances();
    }

    @GetMapping(value = "/SumNumbersOfCreances")
    @ApiOperation(value = "Additionner le total de Creance",
            notes = "Cette méthode permet de faire la somme total de Creance"
    )
    public BigDecimal getNumbersOfCreances() {
        return creanceService.countNumbersOfCreances();
    }


    @GetMapping(value = "/searchCreanceByStatus")
    public Creance getCreanceByStatus(@RequestParam("status") String status) {
        return creanceService.findByStatus(status);
    }

    @GetMapping(value = "/searchListCreanceByStatus")
    public List<Creance> getAllCreanceByStatus(@RequestParam("status") String status) {
        return creanceService.findListCreanceByStatus(status);
    }

    @GetMapping(value = "/searchListCreanceByClientId", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Charges par Client",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Creance d'un Client", responseContainer = "List<Creance>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Creance par client / une liste vide")
    })
    public List<Creance> getAllCreanceByClientId(@RequestParam("clientId") Long clientId) {
        return creanceService.findCreanceByClientId(clientId);
    }

    @GetMapping(value = "/sumTotalOfCreanceByMonth")
    @ApiOperation(value = "Additionner la somme des Creance par mois",
            notes = "Cette méthode permet de faire la somme total des Creance par mois"
    )
    public List<?> sumTotalOfCreancesByMonth() {
        return creanceService.sumTotalOfCreancesByMonth();
    }

    @PostMapping(value = "/creances", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une Creance",
            notes = "Cette méthode permet d'enregistrer et modifier une Creance", response = Creance.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "La Creance a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun Creance n'a été crée / modifié")

    })
    public ResponseEntity<Creance> createCreance(@RequestBody Creance creance) {
        return new ResponseEntity<>(creanceService.saveCreance(creance), HttpStatus.CREATED);
    }

    @PutMapping(value = "/creances/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier une Creance par son ID",
            notes = "Cette méthode permet de modifier une Creance par son ID", response = Creance.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La Creance a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun Creance n'a été crée / modifié")

    })
    public ResponseEntity<Creance> updateCreance(@PathVariable(value = "id") Long id, @RequestBody Creance creance) throws Exception {
        creance.setId(id);
        return new ResponseEntity<>(creanceService.saveCreance(creance), HttpStatus.OK);

    }

    @PatchMapping(value = "/setCreanceStatusById/{id}")
    public ResponseEntity<Creance> setCreanceStatusById(@PathVariable("id") Long id, @RequestBody Creance creance) {
        creance.setId(id);
        return new ResponseEntity<>(creanceService.saveCreance(creance), HttpStatus.OK);
    }

    @PatchMapping(value = "/setCreanceOnlyStatus/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier le status d'une Creance par son ID",
            notes = "Cette méthode permet de modifier le status d'une Creance par son ID", response = Creance.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le status de la Creance a été modifié"),
            @ApiResponse(code = 400, message = "Aucun status de Creance n'a été modifié")

    })
    public ResponseEntity<?> setCreanceOnlyStatus(@RequestParam("status") String status, @PathVariable("id") String id) {
        Creance newCreance = creanceService.setCreanceOnlyStatus(status, id);
        return new ResponseEntity<>(newCreance, HttpStatus.OK);
    }

    @PatchMapping(value = "/setCreanceOnlyAvanceCreance/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier l'avance d'une Creance par son ID",
            notes = "Cette méthode permet de modifier la somme de creance avancée par son ID", response = Creance.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'avance de la Creance a été modifié"),
            @ApiResponse(code = 400, message = "Aucun avance de Creance n'a été modifié")

    })
    public ResponseEntity<?> setCreanceOnlyAvanceCreance(@RequestParam("avanceCreance") double avanceCreance, @PathVariable("id") String id) {
        Creance newCreance = creanceService.setCreanceOnlyAvanceCreance(avanceCreance, id);
        return new ResponseEntity<>(newCreance, HttpStatus.OK);
    }

    @DeleteMapping(value = "/creances/{id}")
    @ApiOperation(value = "Supprimer une Creance par son ID",
            notes = "Cette méthode permet de supprimer une Creance par son ID", response = Creance.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La Creance a été supprimé")
    })
    public ResponseEntity<?> deleteCreance(@PathVariable(value = "id") Long id) {

        creanceService.deleteCreance(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/generateReferenceCreance")
    public long generateReferenceCreance() {
        return creanceService.generateReferenceCreance();
    }
}
