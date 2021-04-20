package com.library.services.impl;

import ch.qos.logback.core.pattern.color.WhiteCompositeConverter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.library.entities.Category;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.CategoryRepository;
import com.library.services.CategoryService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
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
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findCategoryById(Long catId) {

        if (!categoryRepository.existsById(catId)) {
            throw new ResourceNotFoundException("Category that id is" + catId + "not found");
        }
        return categoryRepository.findById(catId);

    }


    @Override
    public Category saveCategory(Category category) {
        Category checkCategorie = categoryRepository.findByCode(category.getCode());
        if (checkCategorie != null) {
            throw new IllegalArgumentException("La Categorie existe déjà");
        }

        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long catId, Category category) {

        if (!categoryRepository.existsById(catId)) {
            throw new ResourceNotFoundException("Category not found");
        }
        Optional<Category> cat = categoryRepository.findById(catId);
        if (!cat.isPresent()) {
            throw new ResourceNotFoundException("Category that not found");
        }

        Category categorie = cat.get();
        categorie.setCode(category.getCode());
        categorie.setDesignation(category.getDesignation());

        return categoryRepository.save(categorie);
    }

    @Override
    public void deleteCategory(Long catId) {
        if (!categoryRepository.existsById(catId)) {
            throw new ResourceNotFoundException("Category not found");
        }
        categoryRepository.deleteById(catId);
    }

    @Override
    public Category findByCode(String code) {
        return categoryRepository.findByCode(code);
    }

    @Override
    public List<Category> ListCategoryByCode(String code) {
        return categoryRepository.ListCategoryByCode(code);
    }

    @Override
    public Category findByDesignation(String designation) {
        return categoryRepository.findByDesignation(designation);
    }

    @Override
    public List<Category> ListCategoryByDesignation(String designation) {
        return categoryRepository.ListCategoryByDesignation(designation);

    }

    @Override
    public Page<Category> findAllCategoryByPage(Pageable page) {

        return categoryRepository.findCategoryByPageable(page);
    }

    @Override
    public Page<Category> findCategoryByKeyWord(String mc, Pageable pageable) {
        return categoryRepository.findCategoryByKeyWord(mc, pageable);

    }

    @Override
    public boolean createCategoriePdf(List<Category> categories, ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        Document document = new Document(PageSize.A4, 15, 15, 45, 30);
        try {
            String filePath = context.getRealPath("/resources/reports");
            File file = new File(filePath);
            boolean exist = new File(filePath).exists();
            if (!exist) {
                new File(filePath).mkdirs();
            }

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file + "/" + "categories" + ".pdf"));
            document.open();

            Font mainFontEntete = FontFactory.getFont(FontFactory.COURIER, 30, BaseColor.BLACK);
            mainFontEntete.setStyle(Font.BOLD);
            mainFontEntete.setColor(BaseColor.BLUE);
            mainFontEntete.setStyle(Font.UNDERLINE);

            Paragraph paragraphEntete = new Paragraph("AL AMINE", mainFontEntete);
            paragraphEntete.setAlignment(Element.ALIGN_CENTER);
            paragraphEntete.setIndentationLeft(90);
            paragraphEntete.setIndentationRight(90);
            paragraphEntete.setSpacingAfter(6);
            document.add(paragraphEntete);

            Font mainFontTitle = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);

            Paragraph paragraphTitle = new Paragraph("Prestation de Service & Commerce GeneralRC SN ZGR 2016 C233 / NINEA 00058166762P6\n" +
                    "N°Compte CNCAS SN 048 03001 000108318801 J/40N° Compte BNDE SN 169 03001 001000519301/30\n" +
                    "Tél: 77109 18 18 / Email: papeteriealamine@gmail.com\n", mainFontTitle);

            paragraphTitle.setAlignment(Element.ALIGN_CENTER);
            paragraphTitle.setIndentationLeft(50);
            paragraphTitle.setIndentationRight(50);
            paragraphTitle.setSpacingAfter(10);
            document.add(paragraphTitle);

            Font mainFont = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            mainFont.setStyle(Font.UNDERLINE);

            Paragraph paragraph = new Paragraph("LA LISTE DES CATEGORIES", mainFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setIndentationLeft(50);
            paragraph.setIndentationRight(50);
            paragraph.setSpacingAfter(10f);
            paragraph.setSpacingBefore(5f);
            document.add(paragraph);

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10);

            Font tableHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
            Font tableBody = FontFactory.getFont("Arial", 12, BaseColor.BLACK);

            PdfPCell code = new PdfPCell(new Paragraph("Code", tableHeader));
            code.setBorderColor(BaseColor.BLACK);
            code.setPaddingLeft(10);
            code.setHorizontalAlignment(Element.ALIGN_CENTER);
            code.setVerticalAlignment(Element.ALIGN_CENTER);
            code.setBackgroundColor(BaseColor.LIGHT_GRAY);
            code.setExtraParagraphSpace(5f);
            table.addCell(code);

            PdfPCell designation = new PdfPCell(new Paragraph("Designation", tableHeader));
            designation.setBorderColor(BaseColor.BLACK);
            designation.setPaddingLeft(10);
            designation.setHorizontalAlignment(Element.ALIGN_CENTER);
            designation.setVerticalAlignment(Element.ALIGN_CENTER);
            designation.setBackgroundColor(BaseColor.LIGHT_GRAY);
            designation.setExtraParagraphSpace(5f);
            table.addCell(designation);

            for (Category category : categories) {
                PdfPCell codeValue = new PdfPCell(new Paragraph(category.getCode(), tableBody));
                codeValue.setBorderColor(BaseColor.BLACK);
                codeValue.setPaddingLeft(4);
                codeValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                codeValue.setVerticalAlignment(Element.ALIGN_MIDDLE);
                codeValue.setBackgroundColor(BaseColor.WHITE);
                codeValue.setExtraParagraphSpace(5f);
                table.addCell(codeValue);

                PdfPCell designationValue = new PdfPCell(new Paragraph(category.getDesignation(), tableBody));
                designationValue.setBorderColor(BaseColor.BLACK);
                designationValue.setPaddingLeft(4);
                designationValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                designationValue.setVerticalAlignment(Element.ALIGN_MIDDLE);
                designationValue.setBackgroundColor(BaseColor.WHITE);
                designationValue.setExtraParagraphSpace(5f);
                table.addCell(designationValue);

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
    public boolean createCategorieExcel(List<Category> categories, ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        String filePath = context.getRealPath("/resources/reports");
        File file = new File(filePath);
        boolean exists = new File(filePath).exists();
        if (!exists) {
            new File(filePath).mkdirs();
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(file + "/" + "categories" + ".xlsx");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet workSheet = workbook.createSheet("Categories");
            workSheet.setDefaultColumnWidth(30);

            HSSFCellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            HSSFRow headerRow = workSheet.createRow(0);

            HSSFCell code = headerRow.createCell(0);
            code.setCellValue("Code");
            code.setCellStyle(headerCellStyle);

            HSSFCell designation = headerRow.createCell(1);
            designation.setCellValue("Designation");
            designation.setCellStyle(headerCellStyle);

            int i = 1;
            for (Category category : categories) {
                HSSFRow bodyRow = workSheet.createRow(i);

                HSSFCellStyle bodyCellStyle = workbook.createCellStyle();

                HSSFCell codeValue = bodyRow.createCell(0);
                codeValue.setCellValue(category.getCode());
                codeValue.setCellStyle(bodyCellStyle);

                HSSFCell designationValue = bodyRow.createCell(1);
                designationValue.setCellValue(category.getDesignation());
                designationValue.setCellStyle(bodyCellStyle);

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
