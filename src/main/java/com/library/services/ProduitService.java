package com.library.services;

import com.library.entities.Produit;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public interface ProduitService {

    Optional<Produit> findProduitById(Long prodId);

    Produit saveProduit(Produit produit);

    Produit updateProduit(Long prodId, Produit produit);

    void deleteProduit(Long prodId);

    Produit findByReference(String reference);

    Produit findByDesignation(String designation);

    Produit findByPrixAchat(Double prixAchat);

    List<Produit> findAllProduits();

    List<Produit> findListProduitByDesignation(String designation);

    List<Produit> findProductByScateoryId(Long scatId);

    //  boolean createPdf(List<Produit> produits, ServletContext context, HttpServletRequest request, HttpServletResponse response);

    boolean createExcel(List<Produit> produits, ServletContext context, HttpServletRequest request, HttpServletResponse response);

    List<?> countNumberOfProduitWithStoc();

    Integer countNumbersOfProductsByStock();

    Integer countNumbersOfProductsWhenQStockEqualStockInit();

    Integer countNumbersOfProductsWhenQStockInfStockInit();


}
