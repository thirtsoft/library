package com.library.controller;

import com.library.entities.Avoir;
import com.library.entities.Creance;
import com.library.services.AvoirService;
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
public class AvoirController {

    @Autowired
    private AvoirService avoirService;

    @GetMapping("/avoirs")
    public List<Avoir> getAllAvoirs() {
        return avoirService.findAllAvoirs();
    }

    @GetMapping("/avoirs/{id}")
    public Optional<Avoir> getAvoirById(@PathVariable(value = "id") Long id) {
        return avoirService.findAvoirById(id);
    }

    @GetMapping("/searchAvoirByReference")
    public Avoir getAvoirByReference(@RequestParam(name = "ref") String reference) {
        return avoirService.findAvoirByReference(reference);
    }

    @GetMapping("/searchListAvoirsByReference")
    public List<Avoir> getAllAvoirsByReference(@RequestParam(name = "ref") String reference) {
        return avoirService.findListAvoirByReference("%"+reference+"%");
    }

    @GetMapping("/searchAvoirByLibelle")
    public Avoir getAvoirByLibelle(@RequestParam(name = "lib") String libelle) {
        return avoirService.findAvoirByLibelle(libelle);
    }

    @GetMapping("/searchListAvoirsByLibelle")
    public List<Avoir> getAllAvoirsByLibelle(@RequestParam(name = "lib") String libelle) {
        return avoirService.findListAvoirByLibelle("%"+libelle+"%");
    }

    @GetMapping("/searchListAvoirsByFournisseurId")
    public List<Avoir> getAllAvoirsByFournisseurId(@RequestParam("client") Long fourId) {
        return avoirService.findListAvoirByFournisseurId(fourId);
    }

    @GetMapping("/searchListAvoirsByFournisseurByPageable")
    public Page<Avoir> getAllAvoirsByPageable(@RequestParam(name = "four")Long fourId,
                                              @RequestParam(name = "page") int page,
                                              @RequestParam(name = "size") int size) {
        return avoirService.findAllAvoirsByFournisseur(fourId, PageRequest.of(page, size));
    }

    @GetMapping("/searchListAvoirsByPageable")
    public Page<Avoir> getAllAvoirsByPageable(@RequestParam(name = "page") int page,
                                              @RequestParam(name = "size") int size) {
        return avoirService.findAllAvoirsByPageable(PageRequest.of(page, size));
    }

    @GetMapping("/searchListAvoirsByKeyword")
    public Page<Avoir> getAllAvoirsByKeyword(@RequestParam(name = "mc") String mc,
                                             @RequestParam(name = "page") int page,
                                             @RequestParam(name = "size") int size) {
        return avoirService.findAvoirByKeyWord("%"+mc+"%", PageRequest.of(page, size));

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
    public ResponseEntity<Object> deleteAvoir(@PathVariable(value="id") Long id) {
        return avoirService.deleteAvoir(id);
    }

}
