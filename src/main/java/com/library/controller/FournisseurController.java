package com.library.controller;

import com.library.entities.Fournisseur;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class FournisseurController {

    @Autowired
    private FournisseurService fournisseurService;

    @GetMapping("/fournisseurs")
    public List<Fournisseur> getAllFournisseurs() {
        return fournisseurService.findAllFournisseurs();
    }

    @GetMapping("/fournisseurs/{id}")
    public ResponseEntity<Fournisseur> getFournisseurById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Fournisseur fournisseur = fournisseurService.findProduitById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur that id is" + id + "not found"));
        return ResponseEntity.ok().body(fournisseur);

    }

    @GetMapping("/serachFournisseurByEmail")
    public Fournisseur getFournisseurByEmail(String email) {
        return fournisseurService.findByEmail(email);
    }

    @GetMapping("/searchFournisseurByCode")
    public Fournisseur getFournisseurByCode(@RequestParam(value = "code") String code) {
        return fournisseurService.findByCode(code);

    }

    @GetMapping("/countFournisseurs")
    public Integer countNumberOfFournisseurs() {
        return fournisseurService.countNumberOfFournisseurs();
    }

    @GetMapping("/searchListFournisseurByCode")
    public List<Fournisseur> getAllFournisseurByCode(@RequestParam(value = "c") String code) {
        return fournisseurService.findListFournisseurByCode("%" + code + "%");
    }

    @GetMapping("/searchFournisseurByNom")
    public Fournisseur getFournisseurByNom(@RequestParam(value = "name") String nom) {
        return fournisseurService.findByNom(nom);
    }

    @GetMapping("/searchListFournisseurByNom")
    public List<Fournisseur> getAllFournisseurByNom(@RequestParam(value = "name") String nom) {
        return fournisseurService.findListFournisseurByNom("%" + nom + "%");
    }

    @GetMapping("/searchFournisseurByRaisonSocial")
    public Fournisseur getFournisseurByRaisonSocial(@RequestParam(value = "raison") String raisonSocial) {
        return fournisseurService.findByRaisonSociale(raisonSocial);

    }

    @GetMapping("/searchListFournisseurByRaisonSocial")
    public List<Fournisseur> getAllFournisseurByRaisonSocial(@RequestParam(value = "raison") String raisonSocial) {
        return fournisseurService.findListFournisseurByRaisonSociale("%" + raisonSocial + "%");
    }

    @GetMapping("/searchListFournisseurByPageable")
    public Page<Fournisseur> getAllFournisseurByPageable(@RequestParam(name = "page") int page,
                                                         @RequestParam(name = "size") int size) {
        return fournisseurService.findAllFournisseursByPageable(PageRequest.of(page, size));
    }

    @GetMapping(value = "/searchListFournisseurByPageableParMotCle")
    public Page<Fournisseur> getFournisseurByKeyWord(@RequestParam(name = "mc", defaultValue = "") String mc,
                                                     @RequestParam(name = "page") int page,
                                                     @RequestParam(name = "size") int size) {
        return fournisseurService.findFournisseurByKeyWord("%" + mc + "%", PageRequest.of(page, size));
    }


    @PostMapping("/fournisseurs")
    public ResponseEntity<Fournisseur> createFournisseur(@RequestBody Fournisseur fournisseur) {
        if (fournisseurService.findByCode(fournisseur.getCode()) != null) {
            return new ResponseEntity<Fournisseur>(fournisseur, HttpStatus.BAD_REQUEST);
        }
        if (fournisseurService.findByEmail(fournisseur.getEmail()) != null) {
            return new ResponseEntity<Fournisseur>(fournisseur, HttpStatus.BAD_REQUEST);
        }
        fournisseurService.saveFournisseur(fournisseur);
        return new ResponseEntity<Fournisseur>(fournisseur, HttpStatus.CREATED);
    }

    @PutMapping("/fournisseurs/{id}")
    public ResponseEntity<Fournisseur> updateFournisseur(@PathVariable(value = "id") Long id, @RequestBody Fournisseur fournisseur) {
        fournisseur.setId(id);
        return new ResponseEntity<>(fournisseurService.updateFournisseurt(id, fournisseur), HttpStatus.OK);

    }

    @DeleteMapping("/fournisseurs/{id}")
    public ResponseEntity<?> deleteFournisseur(@PathVariable(value = "id") Long id) {
        fournisseurService.deleteFournisseur(id);
        return ResponseEntity.ok().build();
    }

}
