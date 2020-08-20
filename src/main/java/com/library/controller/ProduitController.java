package com.library.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.entities.Produit;
import com.library.services.ProduitService;


@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class ProduitController {
	
	@Autowired
	private ProduitService produitService;
	
	@Autowired
	private ServletContext context;
	
	@GetMapping("/produits")
	public List<Produit> getAllProduits() {
		return produitService.findAllProduits();
	}
	
	@GetMapping("/produits/{id}")
	public Optional<Produit> getProduitById(@PathVariable(value = "id") Long prodId) {
		return produitService.findProduitById(prodId);
	}
	
	@GetMapping("/searchProduitByReference")
	public Produit getProduitByReference(@RequestParam(name = "ref") String reference) {
		return produitService.findByReference(reference);
	}
	
	@GetMapping("/searchProduitByDesignation")
	public Produit getProduitByDesignation(@RequestParam(name = "des") String designation) {
		return produitService.findByDesignation(designation);
	}
	
	@GetMapping("/searchProduitByPrixAchat")
	public Produit getProduitByPrixAchat(@RequestParam(name = "price") Double prixAchat) {
		return produitService.findByPrixAchat(prixAchat);
	}
	
	@GetMapping("/searchListProduitsByDesignation")
	public List<Produit> getAllProduitsByDesignation(@RequestParam(name = "des") String designation) {
		return produitService.findListProduitByDesignation("%"+designation+"%");
	}
	
	@GetMapping("/searchListProduitsByCategoryId")
	public List<Produit> getAllProduitsByCategoryId(@RequestParam("caId") Long catId) {
		return produitService.findProductByCateoryId(catId);
	}
	
	@GetMapping("/searchListProduitsByScategoryId")
	public List<Produit> getAllProduitsByScategoryId(@RequestParam("scaId") Long scatId) {
		return produitService.findProductByScateoryId(scatId);
	}
	
	@GetMapping("/searchListProduitsByCategoryPageable")
	public Page<Produit> getAllProduitsByPageable(@RequestParam(name = "cat")Long catId,
			@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size) {
		return produitService.findAllProduitsByCategory(catId, PageRequest.of(page, size));
	}
	
	@GetMapping("/searchListProduitsByPageable")
	public Page<Produit> getAllProduitsByPageable(@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size) {
		return produitService.findAllProduitsByPageable(PageRequest.of(page, size));
	}
	
	@GetMapping("/searchListProduitsByKeyword")
	public Page<Produit> getAllProduitsByKeyword(@RequestParam(name = "mc") String mc, 
			@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size) {
		return produitService.findProduitByKeyWord("%"+mc+"%", PageRequest.of(page, size));
		
	}
	
	@PostMapping("/categories/{catId}/produits")
	public Produit createProduit(@PathVariable(value = "catId") Long catId, @RequestBody Produit produit) {
		return produitService.saveProduit(catId, produit);
	}
	
	@PostMapping("/produits")
	public Produit saveProduit(@RequestBody Produit produit) {
		return produitService.saveProduit(produit);
	}
	
	@PutMapping("/produits/{prodId}")
	public Produit updateProduit(@PathVariable Long prodId, @RequestBody Produit produit) {
		produit.setId(prodId);
		return produitService.saveProduit(produit);	
	}
	
	@DeleteMapping("/produits/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable(value="id") Long prodId) {
		return produitService.deleteProduit(prodId);
	}
	
	@GetMapping(value = "/createPdf")
	public void createPdf(HttpServletRequest request, HttpServletResponse response) {
		List<Produit> produitList = produitService.findAllProduits();
		boolean isFlag = produitService.createPdf(produitList, context, request, response);
		
		if (isFlag) {
			String fullPath = request.getServletContext().getRealPath("/resources/reports/"+"articles"+".pdf");
			filedownload(fullPath, response, "articles.pdf");
		}
		
		
	}
	
	@GetMapping(value = "/createExcel")
	public void createExcel(HttpServletRequest request, HttpServletResponse response) {
		List<Produit> produitList = produitService.findAllProduits();
		boolean isFlag = produitService.createExcel(produitList, context, request, response);
		
		if (isFlag) {
			String fullPath = request.getServletContext().getRealPath("/resources/reports/"+"articles"+".xls");
			filedownload(fullPath, response, "articles.xls");
		}
		
		
	}
	
	private void filedownload(String fullPath, HttpServletResponse response, String fileName) {
		File file = new File(fullPath);
		final int BUFFER_SIZE = 4096;
		if (file.exists()) {
			try {
				FileInputStream inputStream = new FileInputStream(file);
				String mimeType = context.getMimeType(fullPath);
				response.setContentType(mimeType);
				response.setHeader("content-disposition", "attachment; filename="+fileName);
				OutputStream outputStream = response.getOutputStream();
				byte[] buffer = new byte[BUFFER_SIZE];
				int byteRead = -1;
				while ((byteRead = inputStream.read(buffer))!= -1) {
					outputStream.write(buffer, 0, byteRead); 
				}
				inputStream.close();
				outputStream.close();
				file.delete();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
