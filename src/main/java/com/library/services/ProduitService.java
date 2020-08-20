package com.library.services;

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
	
	public Optional<Produit> findProduitById(Long prodId);
	public Produit saveProduit(Long catId, Produit produit);
	public Produit saveProduit(Produit produit);
	
	public Produit updateProduit(Long prodId, Produit produit);
	public Produit updateProduit(Produit produit);
	public ResponseEntity<Object> deleteProduit(Long prodId);
	
	public Produit findByReference(String reference);
	public Produit findByDesignation(String designation);
	public Produit findByPrixAchat(Double prixAchat);
	
	public List<Produit> findAllProduits();
	public List<Produit> findListProduitByDesignation(String designation);
	public List<Produit> findProductByCateoryId(Long catId);
	public List<Produit> findProductByScateoryId(Long scatId);
	
	
	public Page<Produit> findAllProduitsByPageable(Pageable page);
	public Page<Produit>findAllProduitsByCategory(Long catId, Pageable pageable);
	
	public Page<Produit> findProduitByKeyWord(String mc, Pageable pageable);
	
	boolean createPdf(List<Produit> produits, ServletContext context, HttpServletRequest request, HttpServletResponse response);
	
	boolean createExcel(List<Produit> produits, ServletContext context, HttpServletRequest request, HttpServletResponse response);
	
	public ResponseEntity<List<Produit>> importExcelFile(MultipartFile files);

}
