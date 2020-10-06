package com.library.services.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import com.library.services.ProduitService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.library.entities.Category;
import com.library.entities.Produit;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.CategoryRepository;
import com.library.repository.ProduitRepository;


@Service
@Transactional
public class ProduitServiceImpl implements ProduitService {
	
	@Autowired
	private ProduitRepository produitRepository;

	/*
	@Autowired
	private CategoryRepository categoryRepository;

	*/

	@Override
	public List<Produit> findAllProduits() {
		return produitRepository.findAll();
	}

	@Override
	public Optional<Produit> findProduitById(Long prodId) {
		if (!produitRepository.existsById(prodId)) {
			throw new ResourceNotFoundException("Produit that id is" + prodId + "is not found");
		}
		return produitRepository.findById(prodId);
	}
	
	@Override
	public Produit findByReference(String reference) {
		return produitRepository.findByReference(reference);
	}

	@Override
	public Produit findByDesignation(String designation) {
		return produitRepository.findByDesignation(designation);
	}

	@Override
	public Produit findByPrixAchat(Double prixAchat) {
		return produitRepository.findByPrixAchat(prixAchat);
	}

	@Override
	public List<Produit> findListProduitByDesignation(String designation) {
		return produitRepository.findListProduitByDesignation(designation);
	}
		
	@Override
	public List<Produit> findProductByCateoryId(Long catId) {
		return produitRepository.findProductByCateoryId(catId);
	}
	
	@Override
	public List<Produit> findProductByScateoryId(Long scatId) {
		return produitRepository.findProductByScateoryId(scatId);
	}
	
	@Override
	public Page<Produit> findAllProduitsByPageable(Pageable page) {
		return produitRepository.findAllProduitsByPageable(page);
	}

	@Override
	public Page<Produit> findAllProduitsByCategory(Long catId, Pageable pageable) {
		return produitRepository.findProduitByCateoryId(catId, pageable);
	}

	@Override
	public Page<Produit> findProduitByKeyWord(String mc, Pageable pageable) {
		return produitRepository.findProduitByKeyWord(mc, pageable);
	}

	/*
	@Override
	public Produit saveProduit(Long catId, Produit produit) {
		Set<Produit> products = new HashSet<Produit>(); 
		
		Category cat = new Category();
	  
		Optional<Category> catbyId = categoryRepository.findById(catId);
		
		if(!catbyId.isPresent()) { throw new ResourceNotFoundException("Category" +catId + "not found"); 
		
		} 
		
		Category category = catbyId.get();
		
		Produit prod = produitRepository.save(produit); products.add(prod);
		//cat.setProducts(products);
	  
		return prod;
	}

	*/

	@Override
	public Produit saveProduit(Produit produit) {
		return produitRepository.save(produit);
	}

	@Override
	public Produit updateProduit(Long prodId, Produit produit) {
		if(!produitRepository.existsById(prodId)) {
			throw new ResourceNotFoundException("Produit that id is" + prodId + "not found");
		}
		Optional<Produit> prod = produitRepository.findById(prodId);
		
		if(!prod.isPresent()) {
			throw new ResourceNotFoundException("Produit that id is" + prodId + "not found");
			
		}
		Produit product = prod.get();
		
		product.setReference(produit.getReference());
		product.setDesignation(produit.getDesignation());
		product.setPrixAchat(produit.getPrixAchat());
		product.setPrixVente(produit.getPrixVente());
		product.setStockInitial(produit.getStockInitial());
		product.setTva(produit.getTva());
		product.setPromo(produit.isPromo());
		product.setAdd_date(produit.getAdd_date());
		product.setPhoto(produit.getPhoto());
		//product1.setCategorie(product.getCategorie());
		
		return produitRepository.save(product);
	}

	@Override
	public Produit updateProduit(Produit produit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Object> deleteProduit(Long prodId) {
		if(!produitRepository.existsById(prodId)) {
			throw new ResourceNotFoundException("Produit that id is" + prodId + "not found");
		}
		
		produitRepository.deleteById(prodId);
		
		return ResponseEntity.ok().build();
	}

	@Override
	public boolean createPdf(List<Produit> produits, ServletContext context, 
			HttpServletRequest request,
			HttpServletResponse response) {
		Document document = new Document(PageSize.A4,15, 15, 45, 30);
		try {
			String filePath = context.getRealPath("/resources/reports");
			File file = new File(filePath);
			boolean exist = new File(filePath).exists();
			if (!exist) {
				new File(filePath).mkdirs();
			}
			
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file+"/"+"articles"+".pdf"));
			document.open();
			
			Font mainFont = FontFactory.getFont("Arial", 14, BaseColor.BLACK);
			
			Paragraph paragraph = new Paragraph("LISTE DES ARTICLES", mainFont);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setIndentationLeft(50);
			paragraph.setIndentationRight(50);
			paragraph.setSpacingAfter(10);
			document.add(paragraph);
			
			PdfPTable table = new PdfPTable(4);
			table.setWidthPercentage(100);
			table.setSpacingBefore(10f);
			table.setSpacingAfter(10);
			
			Font tableHeader = FontFactory.getFont("Arial", 10, BaseColor.BLACK);
			Font tableBody = FontFactory.getFont("Arial", 9, BaseColor.BLACK);
			
			PdfPCell reference = new PdfPCell(new Paragraph("Reference", tableHeader));
			reference.setBorderColor(BaseColor.BLACK);
			reference.setPaddingLeft(10);
			reference.setHorizontalAlignment(Element.ALIGN_CENTER);
			reference.setVerticalAlignment(Element.ALIGN_CENTER);
			reference.setBackgroundColor(BaseColor.GRAY);
			reference.setExtraParagraphSpace(5f);
			table.addCell(reference);
			
			PdfPCell designation = new PdfPCell(new Paragraph("Designation", tableHeader));
			designation.setBorderColor(BaseColor.BLACK);
			designation.setPaddingLeft(10);
			designation.setHorizontalAlignment(Element.ALIGN_CENTER);
			designation.setVerticalAlignment(Element.ALIGN_CENTER);
			designation.setBackgroundColor(BaseColor.GRAY);
			designation.setExtraParagraphSpace(5f);
			table.addCell(designation);

			PdfPCell scategorie = new PdfPCell(new Paragraph("Scategorie", tableHeader));
			scategorie.setBorderColor(BaseColor.BLACK);
			scategorie.setPaddingLeft(10);
			scategorie.setHorizontalAlignment(Element.ALIGN_CENTER);
			scategorie.setVerticalAlignment(Element.ALIGN_CENTER);
			scategorie.setBackgroundColor(BaseColor.GRAY);
			scategorie.setExtraParagraphSpace(5f);
			table.addCell(scategorie);


			PdfPCell categorie = new PdfPCell(new Paragraph("Categorie", tableHeader));
			categorie.setBorderColor(BaseColor.BLACK);
			categorie.setPaddingLeft(10);
			categorie.setHorizontalAlignment(Element.ALIGN_CENTER);
			categorie.setVerticalAlignment(Element.ALIGN_CENTER);
			categorie.setBackgroundColor(BaseColor.GRAY);
			categorie.setExtraParagraphSpace(5f);
			table.addCell(categorie);
			
			/*
			 * PdfPCell prixAchat = new PdfPCell(new Paragraph("P.Achat", tableHeader));
			 * prixAchat.setBorderColor(BaseColor.BLACK); prixAchat.setPaddingLeft(10);
			 * prixAchat.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * prixAchat.setVerticalAlignment(Element.ALIGN_CENTER);
			 * prixAchat.setBackgroundColor(BaseColor.GRAY);
			 * prixAchat.setExtraParagraphSpace(5f); table.addCell(prixAchat);
			 * 
			 * PdfPCell prixVente = new PdfPCell(new Paragraph("P.Unitaire", tableHeader));
			 * prixVente.setBorderColor(BaseColor.BLACK); prixVente.setPaddingLeft(10);
			 * prixVente.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * prixVente.setVerticalAlignment(Element.ALIGN_CENTER);
			 * prixVente.setBackgroundColor(BaseColor.GRAY);
			 * prixVente.setExtraParagraphSpace(5f); table.addCell(prixVente);
			 * 
			 * PdfPCell stockInitial = new PdfPCell(new Paragraph("Stock", tableHeader));
			 * stockInitial.setBorderColor(BaseColor.BLACK);
			 * stockInitial.setPaddingLeft(10);
			 * stockInitial.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * stockInitial.setVerticalAlignment(Element.ALIGN_CENTER);
			 * stockInitial.setBackgroundColor(BaseColor.GRAY);
			 * stockInitial.setExtraParagraphSpace(5f); table.addCell(stockInitial);
			 * 
			 * PdfPCell add_date = new PdfPCell(new Paragraph("Date Ajout", tableHeader));
			 * add_date.setBorderColor(BaseColor.BLACK); add_date.setPaddingLeft(10);
			 * add_date.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * add_date.setVerticalAlignment(Element.ALIGN_CENTER);
			 * add_date.setBackgroundColor(BaseColor.GRAY);
			 * add_date.setExtraParagraphSpace(5f); table.addCell(add_date);
			 */
			
			for (Produit prod: produits) {
				PdfPCell referenceValue = new PdfPCell(new Paragraph(prod.getReference(), tableBody));
				referenceValue.setBorderColor(BaseColor.BLACK);
				referenceValue.setPaddingLeft(10);
				referenceValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				referenceValue.setVerticalAlignment(Element.ALIGN_CENTER);
				referenceValue.setBackgroundColor(BaseColor.WHITE);
				referenceValue.setExtraParagraphSpace(5f);
				table.addCell(referenceValue);
				
				PdfPCell designationValue = new PdfPCell(new Paragraph(prod.getDesignation(), tableBody));
				designationValue.setBorderColor(BaseColor.BLACK);
				designationValue.setPaddingLeft(10);
				designationValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				designationValue.setVerticalAlignment(Element.ALIGN_CENTER);
				designationValue.setBackgroundColor(BaseColor.WHITE);
				designationValue.setExtraParagraphSpace(5f);
				table.addCell(designationValue);
				
				PdfPCell scategorieValue = new PdfPCell(new Paragraph(prod.getScategorie().getLibelle(), tableBody));
				scategorieValue.setBorderColor(BaseColor.BLACK);
				scategorieValue.setPaddingLeft(10);
				scategorieValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				scategorieValue.setVerticalAlignment(Element.ALIGN_CENTER);
				scategorieValue.setBackgroundColor(BaseColor.WHITE);
				scategorieValue.setExtraParagraphSpace(5f);
				table.addCell(scategorieValue);

				PdfPCell categorieValue = new PdfPCell(new Paragraph(prod.getCategorie().getDesignation(), tableBody));
				categorieValue.setBorderColor(BaseColor.BLACK);
				categorieValue.setPaddingLeft(10);
				categorieValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				categorieValue.setVerticalAlignment(Element.ALIGN_CENTER);
				categorieValue.setBackgroundColor(BaseColor.WHITE);
				categorieValue.setExtraParagraphSpace(5f);
				table.addCell(categorieValue);
				
				/*
				 * PdfPCell prixAchatValue = new PdfPCell(new Paragraph(prod.getPrixAchat(),
				 * tableBody)); prixAchatValue.setBorderColor(BaseColor.BLACK);
				 * prixAchatValue.setPaddingLeft(10);
				 * prixAchatValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * prixAchatValue.setVerticalAlignment(Element.ALIGN_CENTER);
				 * prixAchatValue.setBackgroundColor(BaseColor.WHITE);
				 * prixAchatValue.setExtraParagraphSpace(5f); table.addCell(prixAchatValue);
				 */
				
				/*
				 * PdfPCell prixVenteValue = new PdfPCell(new Paragraph((prod.getPrixVente()),
				 * tableBody)); prixVenteValue.setBorderColor(BaseColor.BLACK);
				 * prixVenteValue.setPaddingLeft(10);
				 * prixVenteValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * prixVenteValue.setVerticalAlignment(Element.ALIGN_CENTER);
				 * prixVenteValue.setBackgroundColor(BaseColor.WHITE);
				 * prixVenteValue.setExtraParagraphSpace(5f); table.addCell(prixVenteValue);
				 * 
				 * PdfPCell stockInitialValue = new PdfPCell(new
				 * Paragraph(prod.getStockInitial(), tableBody));
				 * stockInitialValue.setBorderColor(BaseColor.BLACK);
				 * stockInitialValue.setPaddingLeft(10);
				 * stockInitialValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * stockInitialValue.setVerticalAlignment(Element.ALIGN_CENTER);
				 * stockInitialValue.setBackgroundColor(BaseColor.WHITE);
				 * stockInitialValue.setExtraParagraphSpace(5f);
				 * table.addCell(stockInitialValue);
				 * 
				 * PdfPCell addDateValue = new PdfPCell(new Paragraph(prod.getAdd_date(),
				 * tableBody)); addDateValue.setBorderColor(BaseColor.BLACK);
				 * addDateValue.setPaddingLeft(10);
				 * addDateValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * addDateValue.setVerticalAlignment(Element.ALIGN_CENTER);
				 * addDateValue.setBackgroundColor(BaseColor.WHITE);
				 * addDateValue.setExtraParagraphSpace(5f); table.addCell(addDateValue);
				 */
				
			}
			
			document.add(table);
			document.close();
			writer.close();
			return true;
			
		} catch (Exception e) {
			return false;
		}
		
	}
	
	
	public boolean createExcel(List<Produit> produitList, ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
		String filePath = context.getRealPath("/resources/reports");
		File file = new File(filePath);
		boolean exists = new File(filePath).exists();
		if (!exists) {
			new File(filePath).mkdirs();
		}
		try {
			FileOutputStream outputStream = new FileOutputStream(file+"/"+"articles"+".xlsx");
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet workSheet = workbook.createSheet("Articles");
			workSheet.setDefaultColumnWidth(30);
			
			HSSFCellStyle headerCellStyle = workbook.createCellStyle();
			//headerCellStyle.setFillForegroundColor(HSSFColor.BLUE.index);
			//headerCellStyle.setFillPattern();
			
			HSSFRow headerRow = workSheet.createRow(0);
			
			HSSFCell reference = headerRow.createCell(0);
			reference.setCellValue("Reference");
			reference.setCellStyle(headerCellStyle);
			
			HSSFCell designation = headerRow.createCell(1);
			designation.setCellValue("Designation");
			designation.setCellStyle(headerCellStyle);

			/*
			HSSFCell scategorie = headerRow.createCell(2);
			scategorie.setCellValue("Scategorie");
			scategorie.setCellStyle(headerCellStyle);

			HSSFCell categorie = headerRow.createCell(3);
			categorie.setCellValue("Categorie");
			categorie.setCellStyle(headerCellStyle);

			*/
			int i = 1;
			for (Produit prod: produitList) {
				HSSFRow bodyRow = workSheet.createRow(i);
				
				HSSFCellStyle bodyCellStyle = workbook.createCellStyle();
				//bodyCellStyle.setFillBackgroundColor(HSSFColor.);
				
				HSSFCell referenceValue = bodyRow.createCell(0);
				referenceValue.setCellValue(prod.getReference());
				referenceValue.setCellStyle(bodyCellStyle);
				
				HSSFCell designationValue = bodyRow.createCell(1);
				designationValue.setCellValue(prod.getDesignation());
				designationValue.setCellStyle(bodyCellStyle);
				/*
				HSSFCell scategorieValue = bodyRow.createCell(2);
				scategorieValue.setCellValue(prod.getScategorie().getLibelle());
				scategorieValue.setCellStyle(bodyCellStyle);

				HSSFCell categorieValue = bodyRow.createCell(3);
				categorieValue.setCellValue(prod.getCategorie().getDesignation());
				categorieValue.setCellStyle(bodyCellStyle);

				*/
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

	@Override
	public ResponseEntity<List<Produit>> importExcelFile(MultipartFile files) {
		
        return null;
   
	}



}
