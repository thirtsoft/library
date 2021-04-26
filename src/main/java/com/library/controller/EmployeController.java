package com.library.controller;

import com.library.entities.Employe;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class EmployeController {

    @Autowired
    private EmployeService employeService;

    @GetMapping("/employes")
    public List<Employe> getAllEmployes() {
        return employeService.findAllEmploye();

    }

    @GetMapping("/employes/{id}")
    public ResponseEntity<Employe> getEmployeById(@PathVariable(value = "id") Long empId)
            throws ResourceNotFoundException {
        Employe employe = employeService.findEmployeById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employe that id is" + empId + "not found"));
        return ResponseEntity.ok().body(employe);

    }

    @GetMapping("/serachEmployeByEmail")
    public Employe getEmployeByEmail(String email) {
        return employeService.findByEmail(email);
    }

    @GetMapping("/searchEmployeByCni")
    public Employe getEmployeByCni(@RequestParam(value = "cni") String cni) {
        return employeService.findByCni(cni);

    }

    @GetMapping("/searchListEmployeByCni")
    public List<Employe> getEmployesByCni(@RequestParam(value = "cni") String cni) {

        return employeService.ListEmployeByCni("%" + cni + "%");

    }

    @GetMapping("/searchEmployeByNom")
    public Employe getEmployeByNom(@RequestParam(value = "name") String nom) {
        return employeService.findByNom(nom);

    }

    @GetMapping("/searchListEmployeByNom")
    public List<Employe> getEmployesByNom(@RequestParam(value = "name") String nom) {

        return employeService.ListEmployeByNom("%" + nom + "%");
    }

    @GetMapping("/searchEmployeByPrenom")
    public Employe getEmployeByPrenom(@RequestParam(value = "pren") String prenom) {
        return employeService.findByPrenom(prenom);

    }

    @GetMapping("/searchListEmployeByPrenom")
    public List<Employe> getEmployesByPrenom(@RequestParam(value = "pren") String prenom) {

        return employeService.ListEmployeByPrenom("%" + prenom + "%");
    }

    @GetMapping("/searchEmployeByTelephone")
    public Employe getEmployeByTelephone(@RequestParam(value = "tel") String telephone) {
        return employeService.findByTelephone(telephone);

    }

    @GetMapping("/searchListEmployeByTelephone")
    public List<Employe> getEmployesByTelephone(@RequestParam(value = "tel") String telephone) {

        return employeService.ListEmployeByTelephone("%" + telephone + "%");
    }

    @GetMapping("/searchListEmployeByEmail")
    public List<Employe> getEmployesByEmail(@RequestParam(value = "mail") String email) {

        return employeService.ListEmployeByEmail("%" + email + "%");
    }

    @PostMapping("/employes")
    public ResponseEntity<Employe> createEmploye(@RequestBody Employe employe) {
        if (employeService.findByEmail(employe.getEmail()) != null) {
            return new ResponseEntity<>(employe, HttpStatus.BAD_REQUEST);
        }

        employeService.saveEmploye(employe);
        return new ResponseEntity<>(employe, HttpStatus.CREATED);
    }

    @PutMapping("/employes/{empId}")
    public ResponseEntity<Employe> updateEmploye(@PathVariable(value = "empId") Long empId, @RequestBody Employe employe) {
        employe.setId(empId);
        return new ResponseEntity<>(employeService.updateEmploye(empId, employe), HttpStatus.OK);

    }

    @DeleteMapping("/employes/{id}")
    public ResponseEntity<?> deleteEmploye(@PathVariable(value = "id") Long id) {
        employeService.deleteEmploye(id);
        return ResponseEntity.ok().build();

    }

}
