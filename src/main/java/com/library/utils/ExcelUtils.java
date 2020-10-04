package com.library.utils;

import com.library.entities.Produit;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

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