package com.library.services;

import com.library.entities.Category;
import com.library.entities.Produit;
import com.library.entities.Scategorie;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface PdfService {

    boolean createCategoriesPdf(List<Category> categories, ServletContext context, HttpServletRequest request, HttpServletResponse response);

    boolean createCategoriesExcel(List<Category> categories, ServletContext context, HttpServletRequest request, HttpServletResponse response);

    boolean createScategoriesPdf(List<Scategorie> scategories, ServletContext context, HttpServletRequest request, HttpServletResponse response);

    boolean createScategoriesExcel(List<Scategorie> scategories, ServletContext context, HttpServletRequest request, HttpServletResponse response);

    boolean createPdfProduits(List<Produit> produits, ServletContext context, HttpServletRequest request, HttpServletResponse response);

    boolean createExcelProduits(List<Produit> produits, ServletContext context, HttpServletRequest request, HttpServletResponse response);


}
