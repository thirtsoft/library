package com.library.utils;

import com.library.entities.Category;
import com.library.entities.Produit;
import com.library.entities.Scategorie;
import com.library.services.CategoryService;
import com.library.services.ScategorieService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ExcelUtils {

    public static String EXCELTYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    @Autowired
    private ScategorieService scategorieService;
    @Autowired
    private CategoryService categoryService;


    public static boolean isExcelFile(MultipartFile file) {
        return EXCELTYPE.equals(file.getContentType());
    }

    public static List<Category> parseCategorieExcelFile(InputStream inputStream) {
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheet("Categories");
            Iterator<Row> rows = sheet.iterator();
            List<Category> categories = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                //skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();
                Category categorie = new Category();
                int cellIndex = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIndex) {
                        case 0:
                            categorie.setCode(currentCell.getStringCellValue());
                            break;
                        case 1:
                            categorie.setDesignation(currentCell.getStringCellValue());
                            break;
                    }
                    cellIndex++;
                }
                categories.add(categorie);
            }
            return categories;
        } catch (IOException e) {
            throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
    }

    public static ByteArrayInputStream CategoriesToExcel(List<Category> categories) throws IOException {

        String[] COLUMNs = {"Code", "Designation"};

        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream()
        ) {
            CreationHelper createHelper = workbook.getCreationHelper();

            Sheet sheet = workbook.createSheet("Categories");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLUE.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Row for Header
            Row headerRow = sheet.createRow(0);

            // Header
            for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
                cell.setCellStyle(headerCellStyle);
            }


            int rowIdx = 1;
            for (Category category : categories) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(category.getCode());
                row.createCell(1).setCellValue(category.getDesignation());

            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    public static ByteArrayInputStream ScategoriesToExcel(List<Scategorie> scategories) throws IOException {

        String[] COLUMNs = {"Code", "Libelle", "Categorie"};

        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream()
        ) {
            CreationHelper createHelper = workbook.getCreationHelper();

            Sheet sheet = workbook.createSheet("Scategories");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLUE.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Row for Header
            Row headerRow = sheet.createRow(0);

            // Header
            for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowIdx = 1;
            for (Scategorie scategorie : scategories) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(scategorie.getCode());
                row.createCell(1).setCellValue(scategorie.getLibelle());
                row.createCell(2).setCellValue(scategorie.getCategorie().getDesignation());

            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    public static ByteArrayInputStream produitsToExcel(List<Produit> produits) throws IOException {

        String[] COLUMNs = {"Barcode", "Reference", "Designation", "Prix_Achat", "Prix_Vente", "Prix_Detail", "Stock", "StockInitial", "Scategorie"};

        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream()
        ) {
            CreationHelper createHelper = workbook.getCreationHelper();

            Sheet sheet = workbook.createSheet("Produits");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLUE.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Row for Header
            Row headerRow = sheet.createRow(0);

            // Header
            for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
                cell.setCellStyle(headerCellStyle);
            }

            // CellStyle for Date
            //    CellStyle dateCellStyle = workbook.createCellStyle();
            //    dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/mm/dd"));

            int rowIdx = 1;
            for (Produit produit : produits) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(produit.getBarCode());
                row.createCell(1).setCellValue(produit.getReference());
                row.createCell(2).setCellValue(produit.getDesignation());
                row.createCell(3).setCellValue(produit.getPrixAchat());
                row.createCell(4).setCellValue(produit.getPrixVente());
                row.createCell(5).setCellValue(produit.getPrixDetail());
                row.createCell(6).setCellValue(produit.getQtestock());
                row.createCell(7).setCellValue(produit.getStockInitial());

                row.createCell(8).setCellValue(produit.getScategorie().getLibelle());

            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    public List<Scategorie> parseScategorieExcelFile(InputStream inputStream) {
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheet("Scategories");
            Iterator<Row> rows = sheet.iterator();
            List<Scategorie> scategories = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                //skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();
                Scategorie scategorie = new Scategorie();
                int cellIndex = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIndex) {
                        case 0:
                            scategorie.setCode(currentCell.getStringCellValue());
                            break;
                        case 1:
                            scategorie.setLibelle(currentCell.getStringCellValue());
                            break;
                        case 2:
                            Category category = categoryService.findByDesignation(currentCell.getStringCellValue());
                            scategorie.setCategorie(category);
                            break;
                    }
                    cellIndex++;
                }
                scategories.add(scategorie);
            }
            return scategories;
        } catch (IOException e) {
            throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
    }

    public List<Produit> parseProduitsExcelFile(InputStream inputStream) {
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheet("Produits");
            Iterator<Row> rows = sheet.iterator();
            List<Produit> produitList = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                //skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();
                Produit produit = new Produit();
                int cellIndex = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIndex) {
                        case 0:
                            produit.setBarCode(String.format("%.0f",currentCell.getNumericCellValue()));
                            break;
                        case 1:
                            produit.setReference(currentCell.getStringCellValue());
                            break;
                        case 2:
                            produit.setDesignation(currentCell.getStringCellValue());
                            break;
                        case 3:
                            produit.setPrixAchat(currentCell.getNumericCellValue());
                            break;
                        case 4:
                            produit.setPrixVente(currentCell.getNumericCellValue());
                            break;
                        case 5:
                            produit.setPrixDetail(currentCell.getNumericCellValue());
                            break;
                        case 6:
                            produit.setQtestock((int) currentCell.getNumericCellValue());
                            break;
                        case 7:
                            produit.setStockInitial((int) currentCell.getNumericCellValue());
                            break;
                        case 8:
                            Scategorie scategorie = scategorieService.findByLibelle(currentCell.getStringCellValue());
                            produit.setScategorie(scategorie);
                            break;
                    }
                    cellIndex++;
                }
                produitList.add(produit);
            }
            return produitList;
        } catch (IOException e) {
            throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
    }

    public List<Produit> parseExcelFile(InputStream inputStream) {
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheet("Produits");
            Iterator<Row> rows = sheet.iterator();
            List<Produit> produits = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                //skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();
                Produit produit = new Produit();
                int cellIndex = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIndex) {
                        case 0:
                            produit.setBarCode(currentCell.getStringCellValue());
                            break;
                        case 1:
                            produit.setReference(currentCell.getStringCellValue());
                            break;
                        case 2:
                            produit.setDesignation(currentCell.getStringCellValue());
                            break;
                        case 3:
                            produit.setPrixAchat(currentCell.getNumericCellValue());
                            break;
                        case 4:
                            produit.setPrixVente(currentCell.getNumericCellValue());
                            break;
                        case 5:
                            produit.setPrixDetail(currentCell.getNumericCellValue());
                            break;
                        case 6:
                            produit.setQtestock((int) currentCell.getNumericCellValue());
                            break;
                        case 7:
                            produit.setStockInitial((int) currentCell.getNumericCellValue());
                            break;
                        case 8:
                            Scategorie scategorie = scategorieService.findByLibelle(currentCell.getStringCellValue());
                            produit.setScategorie(scategorie);
                            break;
                    }
                    cellIndex++;
                }
                produits.add(produit);
            }
            return produits;
        } catch (IOException e) {
            throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
    }

}
