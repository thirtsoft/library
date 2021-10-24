package com.library.services.impl;

import com.library.entities.Scategorie;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.ScategorieRepository;
import com.library.services.ScategorieService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ScategorieServiceImpl implements ScategorieService {

    @Autowired
    private ScategorieRepository scategorieRepository;

    @Override
    public List<Scategorie> findAllScategories() {
        return scategorieRepository.findAll();
    }

    @Override
    public List<Scategorie> findAllScategoriesOrderDesc() {
        return scategorieRepository.findByOrderByIdDesc();
    }

    @Override
    public Optional<Scategorie> findScategorieById(Long sCatId) {
        if (!scategorieRepository.existsById(sCatId)) {
            throw new ResourceNotFoundException("Scategorie Not found");
        }
        return scategorieRepository.findById(sCatId);
    }

    @Override
    public Scategorie findByCode(String code) {
        return scategorieRepository.findByCode(code);
    }

    @Override
    public List<Scategorie> findListScategorieByCode(String code) {
        return scategorieRepository.findListScategorieByCode(code);
    }

    @Override
    public Scategorie findByLibelle(String libelle) {
        return scategorieRepository.findByLibelle(libelle);
    }

    @Override
    public List<Scategorie> findListScategorieByLibelle(String libelle) {
        return scategorieRepository.findListScategorieByLibelle(libelle);
    }

    @Override
    public List<Scategorie> findScategorieByCateoryId(Long catId) {
        return scategorieRepository.findScategorieByCateoryId(catId);
    }

    @Override
    public Scategorie saveScategorie(Scategorie scategorie) {
        Scategorie scategorieinfo = scategorieRepository.findByCode(scategorie.getCode());

        if (scategorieinfo != null) {
            throw new IllegalArgumentException("Scategorie existe");
        }

        return scategorieRepository.save(scategorie);
    }

    @Override
    public Scategorie updateScategorie(Long sCatId, Scategorie sCategorie) {
        if (!scategorieRepository.existsById(sCatId)) {
            throw new ResourceNotFoundException("Scategorie Not found");
        }
        Optional<Scategorie> Scat = scategorieRepository.findById(sCatId);

        if (!Scat.isPresent()) {
            throw new ResourceNotFoundException("Scategorie Not found");
        }

        Scategorie sCat = Scat.get();
        sCat.setCode(sCategorie.getCode());
        sCat.setLibelle(sCategorie.getLibelle());
        sCat.setCategorie(sCategorie.getCategorie());

        return scategorieRepository.save(sCat);
    }

    @Override
    public void deleteScategorie(Long sCatId) {
        if (!scategorieRepository.existsById(sCatId)) {
            throw new ResourceNotFoundException("Scategorie not found");
        }
        scategorieRepository.deleteById(sCatId);
    }

    @Override
    public boolean createScategorieExcel(List<Scategorie> scategories, ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        String filePath = context.getRealPath("/resources/reports");
        File file = new File(filePath);
        boolean exists = new File(filePath).exists();
        if (!exists) {
            new File(filePath).mkdirs();
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(file + "/" + "scategories" + ".xlsx");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet workSheet = workbook.createSheet("Scategories");
            workSheet.setDefaultColumnWidth(30);

            HSSFCellStyle headerCellStyle = workbook.createCellStyle();
            //headerCellStyle.setFillForegroundColor(HSSFColor.BLUE.index);
            //headerCellStyle.setFillPattern();

            HSSFRow headerRow = workSheet.createRow(0);

            HSSFCell code = headerRow.createCell(0);
            code.setCellValue("Code");
            code.setCellStyle(headerCellStyle);

            HSSFCell libelle = headerRow.createCell(1);
            libelle.setCellValue("Libelle");
            libelle.setCellStyle(headerCellStyle);


            HSSFCell categorie = headerRow.createCell(2);
            libelle.setCellValue("Categorie");
            libelle.setCellStyle(headerCellStyle);

            int i = 1;
            for (Scategorie scategorie : scategories) {
                HSSFRow bodyRow = workSheet.createRow(i);

                HSSFCellStyle bodyCellStyle = workbook.createCellStyle();
                //bodyCellStyle.setFillBackgroundColor(HSSFColor.);

                HSSFCell codeValue = bodyRow.createCell(0);
                codeValue.setCellValue(scategorie.getCode());
                codeValue.setCellStyle(bodyCellStyle);

                HSSFCell libelleValue = bodyRow.createCell(1);
                libelleValue.setCellValue(scategorie.getLibelle());
                libelleValue.setCellStyle(bodyCellStyle);

                HSSFCell categorieValue = bodyRow.createCell(2);
                libelleValue.setCellValue(scategorie.getCategorie().getDesignation());
                libelleValue.setCellStyle(bodyCellStyle);

                i++;

            }

            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();

            return true;

        } catch (Exception e) {
            return false;
        }
    }


}
