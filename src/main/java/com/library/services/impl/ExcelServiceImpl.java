package com.library.services.impl;

import com.library.entities.Category;
import com.library.entities.Produit;
import com.library.entities.Scategorie;
import com.library.services.CategoryService;
import com.library.services.ExcelService;
import com.library.services.ProduitService;
import com.library.services.ScategorieService;
import com.library.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private ProduitService produitService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ScategorieService scategorieService;

    @Autowired
    private ExcelUtils utils;

    @Override
    public void storeCategorieFile(MultipartFile file) {
        try {
            List<Category> categories = ExcelUtils.parseCategorieExcelFile(file.getInputStream());
            categories.forEach(cat -> categoryService.saveCategory(cat));

        } catch (IOException e) {
            throw new RuntimeException("Faill!  -> message = " + e.getMessage());
        }

    }

    @Override
    public void storeScategorieFile(MultipartFile file) {
        try {
            List<Scategorie> scategories = utils.parseScategorieExcelFile(file.getInputStream());
            scategories.forEach(scat -> scategorieService.saveScategorie(scat));

        } catch (IOException e) {
            throw new RuntimeException("Fail! -> message " + e.getMessage());
        }

    }

    @Override
    public void store(MultipartFile file) {
        try {
            List<Produit> produits = utils.parseProduitsExcelFile(file.getInputStream());
            produits.forEach(p -> {
                try {
                    produitService.saveProductWithBarcode(p);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
    }


}