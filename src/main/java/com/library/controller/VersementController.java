package com.library.controller;

import com.library.entities.Versement;
import com.library.services.VersementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class VersementController {

    @Autowired
    private VersementService versementService;

    @GetMapping("/versements")
    public List<Versement> getAllVersements() {
        return versementService.findAllVersements();
    }

    @GetMapping("/versements/{id}")
    public Optional<Versement> getVersementById(@PathVariable(value = "id") Long id) {
        return versementService.findVersementById(id);
    }

    @GetMapping("/searchVersementByNumVersement")
    public Versement getVersementByNumVersement(@RequestParam(name = "num") String numVersement) {
        return versementService.findByNumVersement(numVersement);
    }

    @GetMapping("/searchListVersementsByNumVersement")
    public List<Versement> getListVersementsByNumVersement(@RequestParam(name = "num") String numVersement) {
        return versementService.findListVersementByNumVersement("%" + numVersement + "%");
    }

    @GetMapping("/searchVersementByNature")
    public Versement getVersementByNature(@RequestParam(name = "nat") String nature) {
        return versementService.findByNature(nature);
    }

    @GetMapping("/searchListVersementsByNature")
    public List<Versement> getListVersementsByNature(@RequestParam(name = "nat") String nature) {
        return versementService.findListVersementByNature("%" + nature + "%");
    }

    @GetMapping("/searchListVersementsByEmployeId")
    public List<Versement> getAllVersementsByEmployeId(@RequestParam("id") Long empId) {
        return versementService.findVersementByEmployeId(empId);
    }

    @GetMapping("/searchListVersementsByPageable")
    public Page<Versement> getAllVersementsByPageable(@RequestParam(name = "page") int page,
                                                      @RequestParam(name = "size") int size) {
        return versementService.findAllVersementsByPageable(PageRequest.of(page, size));
    }

    @GetMapping("/searchListVersementsByKeyword")
    public Page<Versement> getAllVersementsByKeyword(@RequestParam(name = "mc") String mc,
                                                     @RequestParam(name = "page") int page,
                                                     @RequestParam(name = "size") int size) {
        return versementService.findVersementByKeyWord("%" + mc + "%", PageRequest.of(page, size));

    }

    @PostMapping("/versements")
    public ResponseEntity<Versement> saveVersement(@RequestBody Versement versement) {
        if (versementService.findByNumVersement(versement.getNumVersement()) != null) {
            return new ResponseEntity<Versement>(versement, HttpStatus.BAD_REQUEST);
        }
        versementService.saveVersement(versement);
        return new ResponseEntity<Versement>(versement, HttpStatus.CREATED);
    }

    @PutMapping("/versements/{id}")
    public ResponseEntity<Versement> updateVersement(@PathVariable Long id, @RequestBody Versement versement) {
        versement.setId(id);
        return new ResponseEntity<>(versementService.updateVersement(id, versement), HttpStatus.OK);
    }

    @DeleteMapping("/versements/{id}")
    public ResponseEntity<?> deleteVersement(@PathVariable(value = "id") Long id) {
        versementService.deleteVersement(id);
        return ResponseEntity.ok().build();
    }

}
