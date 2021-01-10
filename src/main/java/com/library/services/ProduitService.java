package com.library.services;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.library.entities.Produit;

public interface ProduitService {
	
	Optional<Produit> findProduitById(Long prodId);
	//public Produit saveProduit(Long catId, Produit produit);
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
	List<Produit> findListProduitByAddDate(Date add_date);


	public Page<Produit> findAllProduitsByPageable(Pageable page);
	
	public Page<Produit> findProduitByKeyWord(String mc, Pageable pageable);
	
	boolean createPdf(List<Produit> produits, ServletContext context, HttpServletRequest request, HttpServletResponse response);
	
	boolean createExcel(List<Produit> produits, ServletContext context, HttpServletRequest request, HttpServletResponse response);
	
	public ResponseEntity<List<Produit>> importExcelFile(MultipartFile files);

	List<?> countNumberOfProduitWithStoc();


}
