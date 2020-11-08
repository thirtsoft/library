package com.library.services.impl;

import com.library.entities.Category;
import com.library.entities.Produit;
import com.library.entities.Scategorie;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.CategoryService;
import com.library.services.ExcelService;
import com.library.services.ProduitService;
import com.library.services.ScategorieService;
import com.library.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
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
    public void store(MultipartFile file) {
        try {
            List<Produit> produits = utils.parseExcelFile(file.getInputStream());
            produits.forEach(p->produitService.saveProduit(p));
        } catch (IOException e) {
            throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
    }

    @Override
    public void storeCategorieFile(MultipartFile file) {
        try {
            List<Category> categories = utils.parseCategorieExcelFile(file.getInputStream());
            categories.forEach(cat->categoryService.saveCategory(cat));

        }catch (IOException e) {
            throw new RuntimeException("Faill!  -> message = " + e.getMessage());
        }

    }

    @Override
    public void storeScategorieFile(MultipartFile file) {
        try {
            List<Scategorie> scategories = utils.parseScategorieExcelFile(file.getInputStream());
            scategories.forEach(scat->scategorieService.saveScategorie(scat));

        }catch (IOException e) {
            throw new RuntimeException("Fail! -> message " + e.getMessage());
        }

    }


}