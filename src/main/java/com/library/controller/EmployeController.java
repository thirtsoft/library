package com.library.controller;

import com.library.entities.Devis;
import com.library.entities.Employe;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.EmployeService;
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
@RequestMapping("/alAmine")
public class EmployeController {

    @Autowired
    private EmployeService employeService;

    @GetMapping(value = "/employes", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Employes",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Employes", responseContainer = "List<Employe>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Employe / une liste vide")
    })
    public List<Employe> getAllEmployes() {
        return employeService.findAllEmploye();
    }

    @GetMapping(value = APP_ROOT + "/employes/allEmployeOrderDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Employe",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Employe",
            responseContainer = "List<Employe>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Employe / une liste vide")
    })
    ResponseEntity<List<Employe>> getAllEmployeOrderDesc() {
        List<Employe> employeList = employeService.findAllEmployesOrderDesc();
        return new ResponseEntity<>(employeList, HttpStatus.OK);
    }

    @GetMapping(value = "/employes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Employe par ID",
            notes = "Cette méthode permet de chercher un Employe par son ID", response = Employe.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'Employe a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Employe n'existe avec cette ID pas dans la BD")

    })
    public ResponseEntity<Employe> getEmployeById(@PathVariable(value = "id") Long empId)
            throws ResourceNotFoundException {
        Employe employe = employeService.findEmployeById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employe that id is" + empId + "not found"));
        return ResponseEntity.ok().body(employe);

    }

    @GetMapping(value = "/serachEmployeByEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Employe par EMAIL",
            notes = "Cette méthode permet de chercher un Employe par son EMAIL", response = Employe.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'Employe avec l'email EMAIL a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Employe n'existe avec cet EMAIL pas dans la BD")

    })
    public Employe getEmployeByEmail(String email) {
        return employeService.findByEmail(email);
    }

    @GetMapping(value = "/searchEmployeByCni", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Employe par CNI",
            notes = "Cette méthode permet de chercher un Employe par son CNI", response = Employe.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'Employe avec le CNI a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Employe n'existe avec le CNI pas dans la BD")

    })
    public Employe getEmployeByCni(@RequestParam(value = "cni") String cni) {
        return employeService.findByCni(cni);
    }

    @GetMapping(value = "/searchListEmployeByCni", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Employes par CNI",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Employes par son par CNI", responseContainer = "List<Employe>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Employe par CNI / une liste vide")
    })
    public List<Employe> getEmployesByCni(@RequestParam(value = "cni") String cni) {
        return employeService.ListEmployeByCni("%" + cni + "%");
    }

    @GetMapping(value = "/searchEmployeByNom", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Employe par nom",
            notes = "Cette méthode permet de chercher un Employe par son nom", response = Employe.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'Employe avec le nom a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Employe n'existe avec le nom pas dans la BD")

    })
    public Employe getEmployeByNom(@RequestParam(value = "name") String nom) {
        return employeService.findByNom(nom);

    }

    @GetMapping(value = "/searchListEmployeByNom")
    public List<Employe> getEmployesByNom(@RequestParam(value = "name") String nom) {
        return employeService.ListEmployeByNom("%" + nom + "%");
    }

    @GetMapping(value = "/searchEmployeByPrenom")
    public Employe getEmployeByPrenom(@RequestParam(value = "pren") String prenom) {
        return employeService.findByPrenom(prenom);

    }

    @GetMapping(value = "/searchListEmployeByPrenom")
    public List<Employe> getEmployesByPrenom(@RequestParam(value = "pren") String prenom) {
        return employeService.ListEmployeByPrenom("%" + prenom + "%");
    }

    @GetMapping(value = "/searchEmployeByTelephone")
    public Employe getEmployeByTelephone(@RequestParam(value = "tel") String telephone) {
        return employeService.findByTelephone(telephone);

    }

    @GetMapping(value = "/searchListEmployeByTelephone")
    public List<Employe> getEmployesByTelephone(@RequestParam(value = "tel") String telephone) {
        return employeService.ListEmployeByTelephone("%" + telephone + "%");
    }

    @GetMapping(value = "/searchListEmployeByEmail")
    public List<Employe> getEmployesByEmail(@RequestParam(value = "mail") String email) {
        return employeService.ListEmployeByEmail("%" + email + "%");
    }

    @PostMapping(value = "/employes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un Employe",
            notes = "Cette méthode permet d'enregistrer et modifier un Employe", response = Employe.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "L'Employe a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun Employe a été modifié")

    })
    public ResponseEntity<Employe> createEmploye(@RequestBody Employe employe) {

        if (employeService.findByEmail(employe.getEmail()) != null) {
            return new ResponseEntity<>(employe, HttpStatus.BAD_REQUEST);
        }

        if (employeService.findByTelephone(employe.getTelephone()) != null) {
            return new ResponseEntity<>(employe, HttpStatus.BAD_REQUEST);
        }

        employeService.saveEmploye(employe);
        return new ResponseEntity<>(employe, HttpStatus.CREATED);
    }

    @PutMapping(value = "/employes/{empId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier un Employe par son ID",
            notes = "Cette méthode permet de modifier un Employe par son ID", response = Employe.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'Employe a été modifié"),
            @ApiResponse(code = 400, message = "Aucun Employe a été modifié avec l'id ID")

    })
    public ResponseEntity<Employe> updateEmploye(@PathVariable(value = "empId") Long empId, @RequestBody Employe employe) {
        employe.setId(empId);
        return new ResponseEntity<>(employeService.updateEmploye(empId, employe), HttpStatus.OK);

    }

    @DeleteMapping(value = "/employes/{id}")
    @ApiOperation(value = "Supprimer un Employe par son ID",
            notes = "Cette méthode permet de supprimer un Employe par son ID", response = Employe.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Employe dont l'id est ID a été supprimé")
    })
    public ResponseEntity<?> deleteEmploye(@PathVariable(value = "id") Long id) {
        employeService.deleteEmploye(id);
        return ResponseEntity.ok().build();

    }

}
