package com.library.controller;

import com.library.assembler.CategoryRestAssembler;
import com.library.controller.model.CategoryModel;
import com.library.entities.CategorieCharge;
import com.library.entities.Category;
import com.library.exceptions.ResourceNotFoundException;
import com.library.message.ResponseMessage;
import com.library.services.CategorieChargeService;
import com.library.services.CategoryService;
import com.library.services.ExcelService;
import com.library.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Locale;
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
