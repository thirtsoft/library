package com.library.utils;

import com.library.entities.Produit;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelUtils {

    public static String EXCELTYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static boolean isExcelFile(MultipartFile file) {
        return EXCELTYPE.equals(file.getContentType());
    }

    public static List<Produit> parseExcelFile(InputStream inputStream) {
        try(Workbook workbook = new XSSFWorkbook(inputStream)) {
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
                            produit.setReference(currentCell.getStringCellValue());
                            break;
                        case 1:
                            produit.setDesignation(currentCell.getStringCellValue());
                            break;
                        case 2:
                            produit.setStockInitial((int) currentCell.getNumericCellValue());
                            break;
                        case 3:
                            produit.setPrixAchat(currentCell.getNumericCellValue());
                            break;
                        case 4:
                            boolean isPromo = (int) currentCell.getNumericCellValue() == 1;
                            produit.setPromo(isPromo);
                            break;
                        case 5:
                            produit.setAdd_date(currentCell.getDateCellValue());
                            break;
                        case 6:
                            produit.setPrixVente(currentCell.getNumericCellValue());
                            break;
                        case 7:
                            produit.setPrixDetail(currentCell.getNumericCellValue());
                            break;
                        case 8:
                            produit.setQtestock((int)currentCell.getNumericCellValue());
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

    public static ByteArrayInputStream produitsToExcel(List<Produit> produits) throws IOException {

        String[] COLUMNs = {"Reference", "Designation", "Prix_Achat", "Prix_Vente","Prix_Detail","Stock", "StockInitial", "Date_Ajout"};

        try(
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ){
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
            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/mm/dd"));

            int rowIdx = 1;
            for (Produit produit : produits) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(produit.getReference());
                row.createCell(1).setCellValue(produit.getDesignation());
                row.createCell(2).setCellValue(produit.getPrixAchat());
                row.createCell(3).setCellValue(produit.getPrixVente());
                row.createCell(4).setCellValue(produit.getPrixDetail());
                row.createCell(5).setCellValue(produit.getQtestock());
                row.createCell(6).setCellValue(produit.getStockInitial());

                Cell dateCell = row.createCell(7);
                dateCell.setCellValue(produit.getAdd_date());
                dateCell.setCellStyle(dateCellStyle);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}