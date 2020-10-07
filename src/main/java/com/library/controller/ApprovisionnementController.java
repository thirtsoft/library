package com.library.controller;

import com.library.entities.Approvisionnement;
import com.library.entities.CommandeClient;
import com.library.entities.LigneCmdClient;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.ApprovisionnementService;
import com.library.services.ClientService;
import com.library.services.CommandeClientService;
import com.library.services.LigneCmdClientService;
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
public class ApprovisionnementController {

    @Autowired
    private ApprovisionnementService approvisionnementService;


    @GetMapping("/approvisionnements")
    public List<Approvisionnement> getAllApprovisionnements() {
        return approvisionnementService.findAllApprovisionnements();

    }

    @GetMapping("/approvisionnement/{id}")
    public ResponseEntity<Approvisionnement> getApprovisionnementById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Approvisionnement approvisionnement  = approvisionnementService.findApprovisionnementById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Commande that id is" + id + "not found"));
        return ResponseEntity.ok().body(approvisionnement);

    }

    @GetMapping("/searchApprovisionnementByCode")
    public Approvisionnement getApprovisionnementByCode(@RequestParam("code") String code) {
        return approvisionnementService.findApprovisionnementByCode(code);
    }

    @GetMapping("/searchListApprovisionnementByCode")
    public List<Approvisionnement> getListApprovisionnementByCode(@RequestParam("code") String code) {
        return approvisionnementService.findListApprovisionnementByCode(code);
    }


    @GetMapping("/searchListApprovisionnementByFournisseurId")
    public List<Approvisionnement> getListApprovisionnementByFournisseurId(@RequestParam("fourId") Long fourId) {
        return approvisionnementService.findListApprovisionnementByFournisseurId(fourId);
    }

    @GetMapping("/searchListApprovisionnementByPageable")
    public Page<Approvisionnement> getAllApprovisionnementByPageable(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size) {
        return approvisionnementService.findAllApprovisionnementByPageable(PageRequest.of(page, size));
    }

    @GetMapping("/searchListApprovisionnementByFournisseurPageable")
    public Page<Approvisionnement> getAllApprovisionnementByFournisseurIdByPageable(@RequestParam(name = "prod")Long fourId,
                                                               @RequestParam(name = "page") int page,
                                                               @RequestParam(name = "size") int size) {
        return approvisionnementService.findAllApprovisionnementByFournisseur(fourId, PageRequest.of(page, size));
    }


    @GetMapping("/searchListApprovisionnementByKeyword")
    public Page<Approvisionnement> getAllApprovisionnementByKeyword(@RequestParam(name = "mc")String mc,
                                                         @RequestParam(name = "page") int page,
                                                         @RequestParam(name = "size") int size) {
        return approvisionnementService.findApprovisionnementByKeyWord(mc, PageRequest.of(page, size));
    }



    @PostMapping("/approvisionnements")
    public ResponseEntity<Approvisionnement> createApprovisionnement(@RequestBody Approvisionnement approvisionnement) {

        approvisionnementService.saveApprovisionnement(approvisionnement);

        return new ResponseEntity<>(HttpStatus.OK);

    }


    @PutMapping("/approvisionnements/{id}")
    public ResponseEntity<Approvisionnement>  updateApprovisionnement(@PathVariable(value = "id") Long ApproId, @RequestBody Approvisionnement approvisionnement) throws Exception {
        approvisionnement.setId(ApproId);
        return new ResponseEntity<>(approvisionnementService.saveApprovisionnement(approvisionnement), HttpStatus.OK);

    }
    @DeleteMapping("/approvisionnements/{id}")
    public ResponseEntity<Object> deleteApprovisionnement(@PathVariable(value = "id") Long ApproId) {
        return approvisionnementService.deleteApprovisionnement(ApproId);

    }
}