package com.library.services;

import com.library.entities.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProduitService {

    Optional<Produit> findProduitById(Long prodId);

    Produit saveProduit(Produit produit);

    Produit updateProduit(Long prodId, Produit produit);

    Produit updateProduit(Produit produit);

    void deleteProduit(Long prodId);

    Produit findByReference(String reference);

    Produit findByDesignation(String designation);

    Produit findByPrixAchat(Double prixAchat);

    List<Produit> findAllProduits();

    List<Produit> findListProduitByDesignation(String designation);

    List<Produit> findProductByScateoryId(Long scatId);

    Page<Produit> findAllProduitsByPageable(Pageable page);

    Page<Produit> findProduitByKeyWord(String mc, Pageable pageable);

    boolean createPdf(List<Produit> produits, ServletContext context, HttpServletRequest request, HttpServletResponse response);

    boolean createExcel(List<Produit> produits, ServletContext context, HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<List<Produit>> importExcelFile(MultipartFile files);

    List<?> countNumberOfProduitWithStoc();

    Integer countNumbersOfProductsByStock();

    Integer countNumbersOfProductsWhenQStockEqualStockInit();

    Integer countNumbersOfProductsWhenQStockInfStockInit();


}
