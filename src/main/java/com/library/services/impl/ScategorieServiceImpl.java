package com.library.services.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.library.entities.Scategorie;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.ScategorieRepository;
import com.library.services.ScategorieService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<Scategorie> findAllScategorietsByPageable(Pageable page) {
        return scategorieRepository.findAllScategoriesByPageable(page);
    }

    @Override
    public Page<Scategorie> findAllScategoriesByCategory(Long catId, Pageable pageable) {
        return scategorieRepository.findScategorieByCateoryPageable(catId, pageable);
    }

    @Override
    public Page<Scategorie> findScategorieByKeyWord(String mc, Pageable pageable) {
        return scategorieRepository.findScategorieByKeyWord(mc, pageable);
    }

    @Override
    public boolean createScategoriePdf(List<Scategorie> scategories, ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        Document document = new Document(PageSize.A4, 15, 15, 45, 30);
        try {
            String filePath = context.getRealPath("/resources/reports");
            File file = new File(filePath);
            boolean exist = new File(filePath).exists();
            if (!exist) {
                new File(filePath).mkdirs();
            }

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file + "/" + "scategories" + ".pdf"));
            document.open();

            Font mainFont = FontFactory.getFont("Arial", 14, BaseColor.BLACK);

            Paragraph paragraph = new Paragraph("LISTE DES SCATEGORIES", mainFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setIndentationLeft(50);
            paragraph.setIndentationRight(50);
            paragraph.setSpacingAfter(10);
            document.add(paragraph);

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10);

            Font tableHeader = FontFactory.getFont("Arial", 10, BaseColor.BLACK);
            Font tableBody = FontFactory.getFont("Arial", 9, BaseColor.BLACK);

            PdfPCell code = new PdfPCell(new Paragraph("Code", tableHeader));
            code.setBorderColor(BaseColor.BLACK);
            code.setPaddingLeft(10);
            code.setHorizontalAlignment(Element.ALIGN_CENTER);
            code.setVerticalAlignment(Element.ALIGN_CENTER);
            code.setBackgroundColor(BaseColor.GRAY);
            code.setExtraParagraphSpace(5f);
            table.addCell(code);

            PdfPCell libelle = new PdfPCell(new Paragraph("Libelle", tableHeader));
            libelle.setBorderColor(BaseColor.BLACK);
            libelle.setPaddingLeft(10);
            libelle.setHorizontalAlignment(Element.ALIGN_CENTER);
            libelle.setVerticalAlignment(Element.ALIGN_CENTER);
            libelle.setBackgroundColor(BaseColor.GRAY);
            libelle.setExtraParagraphSpace(5f);
            table.addCell(libelle);

            PdfPCell categorie = new PdfPCell(new Paragraph("Categorie", tableHeader));
            categorie.setBorderColor(BaseColor.BLACK);
            categorie.setPaddingLeft(10);
            categorie.setHorizontalAlignment(Element.ALIGN_CENTER);
            categorie.setVerticalAlignment(Element.ALIGN_CENTER);
            categorie.setBackgroundColor(BaseColor.GRAY);
            categorie.setExtraParagraphSpace(5f);
            table.addCell(categorie);

            for (Scategorie scategorie : scategories) {
                PdfPCell codeValue = new PdfPCell(new Paragraph(scategorie.getCode(), tableBody));
                codeValue.setBorderColor(BaseColor.BLACK);
                codeValue.setPaddingLeft(10);
                codeValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                codeValue.setVerticalAlignment(Element.ALIGN_CENTER);
                codeValue.setBackgroundColor(BaseColor.WHITE);
                codeValue.setExtraParagraphSpace(5f);
                table.addCell(codeValue);

                PdfPCell libelleValue = new PdfPCell(new Paragraph(scategorie.getLibelle(), tableBody));
                libelleValue.setBorderColor(BaseColor.BLACK);
                libelleValue.setPaddingLeft(10);
                libelleValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                libelleValue.setVerticalAlignment(Element.ALIGN_CENTER);
                libelleValue.setBackgroundColor(BaseColor.WHITE);
                libelleValue.setExtraParagraphSpace(5f);
                table.addCell(libelleValue);

                PdfPCell categorieValue = new PdfPCell(new Paragraph(scategorie.getCategorie().getDesignation(), tableBody));
                categorieValue.setBorderColor(BaseColor.BLACK);
                categorieValue.setPaddingLeft(10);
                categorieValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                categorieValue.setVerticalAlignment(Element.ALIGN_CENTER);
                categorieValue.setBackgroundColor(BaseColor.WHITE);
                categorieValue.setExtraParagraphSpace(5f);
                table.addCell(categorieValue);

            }

            document.add(table);
            document.close();
            writer.close();
            return true;

        } catch (Exception e) {
            return false;
        }

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
