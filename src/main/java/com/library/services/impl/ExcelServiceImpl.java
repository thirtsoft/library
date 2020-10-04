package com.library.services.impl;

import com.library.entities.Produit;
import com.library.services.ExcelService;
import com.library.services.ProduitService;
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

    @Override
    public void store(MultipartFile file) {
        try {
            List<Produit> produits = ExcelUtils.parseExcelFile(file.getInputStream());
            produits.forEach(p->produitService.saveProduit(p));
        } catch (IOException e) {
            throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
    }
}