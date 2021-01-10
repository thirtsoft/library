package com.library.controller;

import com.library.entities.Charge;
import com.library.services.ChargeService;
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
public class ChargeController {

    @Autowired
    private ChargeService chargeService;

    @GetMapping("/charges")
    public List<Charge> getAllCharges() {
        return chargeService.findAllCharges();
    }

    @GetMapping("/charges/{id}")
    public Optional<Charge> getChargeById(@PathVariable(value = "id") Long id) {
        return chargeService.findChargeById(id);
    }

    @GetMapping("/searchChargeByCodeCharge")
    public Charge getChargeByCodeCharge(@RequestParam(name = "ref") String codeCharge) {
        return chargeService.findChargeByCodeCharge(codeCharge);
    }

    @GetMapping("/searchListChargesByCodeCharge")
    public List<Charge> getAllChargesByReference(@RequestParam(name = "ref") String codeCharge) {
        return chargeService.findListChargeByCodeCharge("%"+codeCharge+"%");
    }

    @GetMapping("/searchChargeByNature")
    public Charge getChargeByNature(@RequestParam(name = "nat") String nature) {
        return chargeService.findChargeByNature(nature);
    }

    @GetMapping("/searchListChargesByNature")
    public List<Charge> getAllChargesByNature(@RequestParam(name = "nat") String nature) {
        return chargeService.findListChargeByNature("%"+nature+"%");
    }


    @GetMapping("/searchListChargesByPageable")
    public Page<Charge> getAllChargesByPageable(@RequestParam(name = "page") int page,
                                                  @RequestParam(name = "size") int size) {
        return chargeService.findAllChargesByPageable(PageRequest.of(page, size));
    }

    @GetMapping("/searchListChargesByKeyword")
    public Page<Charge> getAllChargesByKeyword(@RequestParam(name = "mc") String mc,
                                                 @RequestParam(name = "page") int page,
                                                 @RequestParam(name = "size") int size) {
        return chargeService.findChargesByKeyWord("%"+mc+"%", PageRequest.of(page, size));

    }

    @PostMapping("/charges")
    public ResponseEntity<Charge> createCharge(@RequestBody Charge charge) {
        if (chargeService.findChargeByCodeCharge(charge.getCodeCharge()) != null) {
            return new ResponseEntity<Charge>(charge, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Charge>(charge, HttpStatus.CREATED);
    }


    @PutMapping("/charges/{id}")
    public Charge updateCharge(@PathVariable Long id, @RequestBody Charge charge) {
        charge.setId(id);
        return chargeService.saveCharge(charge);
    }

    @DeleteMapping("/charges/{id}")
    public ResponseEntity<?> deleteCharge(@PathVariable(value="id") Long id) {
        chargeService.deleteCharge(id);
        return ResponseEntity.ok().build();
    }


}
