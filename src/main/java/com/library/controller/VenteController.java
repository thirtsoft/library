package com.library.controller;

import com.library.entities.Vente;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.VenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/apiSeller")
public class VenteController {
    @Autowired
    private VenteService venteService;

    private Double total = 0.0;

    @GetMapping("/ventes")
    public List<Vente> getAllVentes() {
        return venteService.findAllVentes();
    }

    @GetMapping("/ventes/{id}")
    public ResponseEntity<Vente> getVenteById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Vente vente = venteService.findVenteById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vente Not found"));
        return ResponseEntity.ok().body(vente);

    }

    @GetMapping("/searchVenteByNumeroVente")
    public Vente getVenteByNumeroVente(@RequestParam("num") Long numeroVente) {
        return venteService.findVenteByNumeroVente(numeroVente);
    }

    @GetMapping("/NumberOfVente")
    public int getNumberOfVentes() {
        return venteService.getNumberOfVente();
    }

    @GetMapping("/NumberOfVenteByDay")
    public int getNumberOfVenteByDay() {
        return venteService.countNumberOfVenteByDay();
    }


    @GetMapping("/SumsOfVentes")
    public BigDecimal getSumsOfVentes() {
        return venteService.countSumsOfVentess();
    }

    @GetMapping("/SumsOfVentesByMonth")
    public BigDecimal sumTotalOfVentesByMonth() {
        return venteService.sumTotalOfVentesByMonth();
    }

    @GetMapping("/SumsOfVentesByYear")
    public BigDecimal sumTotalOfVentesByYear() {
        return venteService.sumTotalOfVentesByYear();
    }

    @GetMapping("/generateNumeroVente")
    public long generateNumeroVente() {
        return venteService.generateNumeroVente();
    }

    @GetMapping("/searchVenteByStatus")
    public Vente getVenteByStatus(@RequestParam("status") String status) {
        return venteService.findByStatus(status);
    }

    @GetMapping("/searchListVenteByPageable")
    public Page<Vente> getVenteByPageable(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size) {
        return venteService.findAllVenteByPageable(PageRequest.of(page, size));
    }

    @GetMapping("/searchListVenteByKeyword")
    public Page<Vente> getAllVenteByPageable(@RequestParam(name = "mc") String mc, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
        return venteService.findVenteByKeyWord(mc, PageRequest.of(page, size));
    }

    @PostMapping("/ventes")
    public ResponseEntity<Vente> createVente(@RequestBody Vente vente) {

        venteService.saveVente(vente);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/ventes/{id}")
    public ResponseEntity<Vente> updateVente(@PathVariable(value = "id") Long id, @RequestBody Vente vente) throws Exception {
        vente.setId(id);
        return new ResponseEntity<>(venteService.saveVente(vente), HttpStatus.OK);

    }

    @DeleteMapping("/ventes/{id}")
    public void deleteVente(@PathVariable(value = "id") Long id) {
        venteService.deleteVente(id);
        //return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/searchVenteWithParticularDayAndMonth")
    public List<Vente> getVenteWithParticularDayAndMonth() {
        return venteService.findVenteWithParticularDayAndMonth();
    }

    @GetMapping("/searchNumberOfVenteByMonth")
    public List<?> getNumberTotalOfVenteByMonth() {
        return venteService.countNumberTotalOfVenteByMonth();
    }

    @GetMapping("/searchSumVenteByMonth")
    public List<?> getSumTotalOfVenteByMonth() {
        return venteService.sumTotalOfVenteByMonth();
    }

    @GetMapping("/searchSumVenteByYears")
    public List<?> getSumTotalOfVenteByYears() {
        return venteService.sumTotalOfVenteByYears();
    }


    @GetMapping("/searchSumsOfVenteByDay")
    public BigDecimal getSumsOfVenteByDay() {
        return venteService.sumTotalOfVenteByDay();
    }

    @GetMapping("/searchListVenteByEmpId")
    public List<Vente> findListVenteByEmployeId(Long empId) {
        return venteService.findListVenteByEmployeId(empId);
    }

}
