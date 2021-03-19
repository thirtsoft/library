package com.library.controller;

import com.library.entities.Avoir;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.AvoirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class AvoirController {

    @Autowired
    private AvoirService avoirService;

    @GetMapping("/avoirs")
    public List<Avoir> getAllAvoirs() {
        return avoirService.findAllAvoirs();
    }

    @GetMapping("/avoirs/{id}")
    public ResponseEntity<Avoir> getAvoirById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Avoir avoir = avoirService.findAvoirById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avoir Not found"));
        return ResponseEntity.ok().body(avoir);

    }

    @GetMapping("/searchAvoirByReference")
    public Avoir getAvoirByReference(@RequestParam(name = "ref") long reference) {
        return avoirService.findAvoirByReference(reference);
    }

    @GetMapping("/searchListAvoirsByReference")
    public List<Avoir> getAllAvoirsByReference(@RequestParam(name = "ref") long reference) {
        return avoirService.findListAvoirByReference(reference);
    }

    @GetMapping("/searchAvoirByLibelle")
    public Avoir getAvoirByLibelle(@RequestParam(name = "lib") String libelle) {
        return avoirService.findAvoirByLibelle(libelle);
    }

    @GetMapping("/searchListAvoirsByLibelle")
    public List<Avoir> getAllAvoirsByLibelle(@RequestParam(name = "lib") String libelle) {
        return avoirService.findListAvoirByLibelle("%" + libelle + "%");
    }

    @GetMapping("/searchListAvoirsByFournisseurId")
    public List<Avoir> getAllAvoirsByFournisseurId(@RequestParam("client") Long fourId) {
        return avoirService.findListAvoirByFournisseurId(fourId);
    }

    @GetMapping("/searchAvoirByStatus")
    public Avoir getAvoirByStatus(@RequestParam("status") String status) {
        return avoirService.findByStatus(status);
    }

    @GetMapping("/searchListAvoirByStatus")
    public List<Avoir> getAllAvoirByStatus(@RequestParam("status") String status) {
        return avoirService.findListAvoirByStatus(status);
    }


    @PostMapping("/avoirs")
    public Avoir createAvoir(@RequestBody Avoir avoir) {
        return avoirService.saveAvoir(avoir);
    }


    @PutMapping("/avoirs/{id}")
    public Avoir updateAvoir(@PathVariable Long id, @RequestBody Avoir avoir) {
        avoir.setId(id);
        return avoirService.saveAvoir(avoir);
    }

    @DeleteMapping("/avoirs/{id}")
    public void deleteAvoir(@PathVariable(value = "id") Long id) {
        avoirService.deleteAvoir(id);
    }

    @GetMapping("/generateReferneceAvoir")
    public long generateReferneceAvoir() {
        return avoirService.generateRefereceAvoir();
    }

}
