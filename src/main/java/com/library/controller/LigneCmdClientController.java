package com.library.controller;

import com.library.entities.LigneCmdClient;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.LigneCmdClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/prodApi")
public class LigneCmdClientController {

    @Autowired
    private LigneCmdClientService ligneCmdClientService;

    @GetMapping("/ligneCommandes")
    public List<LigneCmdClient> getAllLigneCmdClients() {
        return ligneCmdClientService.findAllLigneCmdClient();

    }

    @GetMapping("/ligneCommandes/{id}")
    public ResponseEntity<LigneCmdClient> getLigneCmdClientById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        LigneCmdClient ligneCmdClient = ligneCmdClientService.findLigneCmdClientById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LigneCmdClient that id is" + id + "not found"));
        return ResponseEntity.ok().body(ligneCmdClient);

    }

    @GetMapping("/lcomms/{id}")
    public List<LigneCmdClient> getAllByNumero(@PathVariable(value = "id") long numero) {
        System.out.println("Get all Lcomms...");

        List<LigneCmdClient> Lcomms = new ArrayList<>();
        ligneCmdClientService.findAllLcomByNumero(numero).forEach(Lcomms::add);

        return Lcomms;
    }

    @GetMapping("/searchListLigneCmdClientByProduitId")
    public List<LigneCmdClient> getAllLigneCmdClientByProduitId(@RequestParam("prodId") Long prodId) {
        return ligneCmdClientService.findLigneCmdClientByProduitId(prodId);
    }

    @GetMapping("/searchListLigneCmdClientByCommandeId/{comId}")
    public List<LigneCmdClient> getAllLigneCmdClientByCommandeId(@PathVariable("comId") Long comId) {
        return ligneCmdClientService.findLigneCmdClientByCommandeClientId(comId);
    }

    @PostMapping("/ligneCommandes")
    public ResponseEntity<LigneCmdClient> createLigneCmdClient(@RequestBody LigneCmdClient ligneCmdClient) {
        //	return ligneCmdClientService.saveLigneCmdClient(ligneCmdClient);
        return new ResponseEntity<>(ligneCmdClientService.saveLigneCmdClient(ligneCmdClient), HttpStatus.CREATED);
    }

    @PutMapping("/ligneCommandes/{lcId}")
    public ResponseEntity<LigneCmdClient> updateLigneCmdClient(@PathVariable(value = "lcId") Long lcId, @RequestBody LigneCmdClient ligneCmdClient) {
        ligneCmdClient.setId(lcId);
        return new ResponseEntity<>(ligneCmdClientService.saveLigneCmdClient(ligneCmdClient), HttpStatus.OK);

    }

    @DeleteMapping("/ligneCommandes/{id}")
    public ResponseEntity<?> deleteLigneCmdClient(@PathVariable(value = "id") Long id) {
        ligneCmdClientService.deleteLigneCmdClient(id);
        return ResponseEntity.ok().build();

    }

}
