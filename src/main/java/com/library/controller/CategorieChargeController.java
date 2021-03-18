package com.library.controller;

import com.library.entities.CategorieCharge;
import com.library.services.CategorieChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/alAmine")
public class CategorieChargeController {

    @Autowired
    private CategorieChargeService catChargeService;

    @GetMapping("/categorieCharges")
    public List<CategorieCharge> getAllCategorieCharges() {
        return catChargeService.findAllCategorieCharges();
    }

    @GetMapping("/categorieCharges/{id}")
    public ResponseEntity<CategorieCharge> getCategorieChargeById(@PathVariable(value = "id") Long id) {
        Optional<CategorieCharge> catCharge = catChargeService.findCategorieChargeById(id);
        if (catCharge.isPresent())
            return new ResponseEntity<CategorieCharge>(catCharge.get(), HttpStatus.OK);
        else
            return new ResponseEntity<CategorieCharge>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("/searchCategorieChargesByCode")
    public CategorieCharge getCategorieChargeByCode(@RequestParam(value = "code") String code) {
        return catChargeService.findByCodeCategorieCharge(code);
    }

    @GetMapping("/searchListCategorieChargesByCode")
    public List<CategorieCharge> getAllCategorieChargeByCode(@RequestParam(value = "c") String code) {
        return catChargeService.ListCategoryByCodeCategorieCharge(code);
    }

    @GetMapping("/searchCategorieChargeByDesignation")
    public CategorieCharge getCategoryByDesignation(@RequestParam(value = "des") String designation) {
        return catChargeService.findByNomCategorieCharge(designation);
    }

    @GetMapping("/searchListCategorieChargesByDesignation")
    public List<CategorieCharge> getAllCategoryByDesignation(@RequestParam(value = "des") String designation) {
        return catChargeService.ListCategoryByNomCategorieCharge(designation);
    }

    @PostMapping("/categorieCharges")
    public ResponseEntity<CategorieCharge> createCategorieCharge(@RequestBody CategorieCharge categorieCharge) {
        if (catChargeService.findByCodeCategorieCharge(categorieCharge.getCodeCategorieCharge()) != null) {
            return new ResponseEntity<CategorieCharge>(categorieCharge, HttpStatus.BAD_REQUEST);
        }
        catChargeService.saveCategorieCharge(categorieCharge);
        return new ResponseEntity<CategorieCharge>(categorieCharge, HttpStatus.CREATED);

    }

    @PutMapping("/categorieCharges/{catId}")
    public ResponseEntity<CategorieCharge> updateCategorieCharge(@PathVariable Long catId, @RequestBody CategorieCharge catCharge) {
        catCharge.setId(catId);
        return new ResponseEntity<>(catChargeService.updateCategorieCharge(catId, catCharge), HttpStatus.OK);
    }

    @DeleteMapping("/categorieCharges/{id}")
    public ResponseEntity<Object> deleteCategorieCharge(@PathVariable(value = "id") Long id) {
        catChargeService.deleteCategorieCharge(id);
        return ResponseEntity.ok().build();
    }


}
