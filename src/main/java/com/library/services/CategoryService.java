package com.library.services;

import com.library.entities.Category;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;


public interface CategoryService {

    List<Category> findAllCategory();

    Category saveCategory(Category category);

    Optional<Category> findCategoryById(Long i);

    Category updateCategory(Long catId, Category category);

    void deleteCategory(Long catId);

    Category findByCode(String code);

    Category findByDesignation(String designation);

    List<Category> ListCategoryByCode(String designation);

    List<Category> ListCategoryByDesignation(String designation);

    // boolean createCategoriePdf(List<Category> categories, ServletContext context, HttpServletRequest request, HttpServletResponse response);

    boolean createCategorieExcel(List<Category> categories, ServletContext context, HttpServletRequest request, HttpServletResponse response);

}
