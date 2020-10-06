package com.library.controller;

import com.library.entities.Creance;
import com.library.services.CreanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class CreanceController {

    @Autowired
    private CreanceService creanceService;

    @GetMapping("/creances")
    public List<Creance> getAllCreances() {
        return creanceService.findAllCreances();
    }

    @GetMapping("/creances/{id}")
    public Optional<Creance> getCreanceById(@PathVariable(value = "id") Long id) {
        return creanceService.findCreanceById(id);
    }

    @GetMapping("/searchCreanceByReference")
    public Creance getCreanceByReference(@RequestParam(name = "ref") String reference) {
        return creanceService.findByReference(reference);
    }

    @GetMapping("/searchListCreancesByReference")
    public List<Creance> getAllCreancesByReference(@RequestParam(name = "ref") String reference) {
        return creanceService.findListCreanceByReference("%"+reference+"%");
    }

    @GetMapping("/searchCreanceByLibelle")
    public Creance getCreanceByLibelle(@RequestParam(name = "lib") String libelle) {
        return creanceService.findByLibelle(libelle);
    }

    @GetMapping("/searchListCreancesByLibelle")
    public List<Creance> getAllCreancesByLibelle(@RequestParam(name = "lib") String libelle) {
        return creanceService.findListCreanceByLibelle("%"+libelle+"%");
    }

    @GetMapping("/searchListCreancesByClientId")
    public List<Creance> getAllCreancesByClientId(@RequestParam("client") Long clientId) {
        return creanceService.findCreanceByClientId(clientId);
    }

    @GetMapping("/searchListCreancesByClientPageable")
    public Page<Creance> getAllCreancesByPageable(@RequestParam(name = "client")Long clientId,
                                                  @RequestParam(name = "page") int page,
                                                  @RequestParam(name = "size") int size) {
        return creanceService.findAllCreancesByClient(clientId, PageRequest.of(page, size));
    }

    @GetMapping("/searchListCreancesByPageable")
    public Page<Creance> getAllCreancesByPageable(@RequestParam(name = "page") int page,
                                                  @RequestParam(name = "size") int size) {
        return creanceService.findAllCreancesByPageable(PageRequest.of(page, size));
    }

    @GetMapping("/searchListCreancesByKeyword")
    public Page<Creance> getAllCreancesByKeyword(@RequestParam(name = "mc") String mc,
                                                 @RequestParam(name = "page") int page,
                                                 @RequestParam(name = "size") int size) {
        return creanceService.findCreanceByKeyWord("%"+mc+"%", PageRequest.of(page, size));

    }

    @PostMapping("/creances")
    public Creance createCreance(@RequestBody Creance creance) {
        return creanceService.saveCreance(creance);
    }


    @PutMapping("/creances/{id}")
    public Creance updateCreance(@PathVariable Long id, @RequestBody Creance creance) {
        creance.setId(id);
        return creanceService.saveCreance(creance);
    }

    @DeleteMapping("/creances/{id}")
    public ResponseEntity<Object> deleteCreance(@PathVariable(value="id") Long id) {
        return creanceService.deleteCreance(id);
    }
}
