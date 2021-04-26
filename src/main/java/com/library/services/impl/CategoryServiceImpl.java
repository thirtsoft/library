package com.library.services.impl;

import com.library.entities.Category;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.CategoryRepository;
import com.library.services.CategoryService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
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
            throw new IllegalArgumentException("This Category already exist");
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
