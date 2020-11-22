package com.library.services.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.library.services.CategoryService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.library.entities.Category;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.CategoryRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
		
		if(!categoryRepository.existsById(catId)) {
			throw new ResourceNotFoundException("Category that id is" + catId + "not found");
		}
		return categoryRepository.findById(catId);
		
	}
	
	@Override
	public Category saveCategory(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public Category updateCategory(Long catId, Category category) {
		
		if(!categoryRepository.existsById(catId)) {
			throw new ResourceNotFoundException("Category that id is" + catId + "not found"); 
		}
		Optional<Category> cat = categoryRepository.findById(catId);
		if(!cat.isPresent()) {
			throw new ResourceNotFoundException("Category that id is" + catId + "not found"); 
		}
		
		Category categorie = cat.get();
		categorie.setCode(category.getCode());
		categorie.setDesignation(category.getDesignation());
		
		return categoryRepository.save(categorie);
	}

	@Override
	public ResponseEntity<Object> deleteCategory(Long catId) {
		if (!categoryRepository.existsById(catId)) {
			throw new ResourceNotFoundException("Category that id is" + catId + "is not found");
		}
		categoryRepository.deleteById(catId);
		return ResponseEntity.ok().build();
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
	public Page<Category> findAllCategoryByPage(Pageable page) {

		return categoryRepository.findCategoryByPageable(page);
	}

	@Override
	public Page<Category> findCategoryByKeyWord(String mc, Pageable pageable) {
		return categoryRepository.findCategoryByKeyWord(mc, pageable);
		
	}

	@Override
	public boolean createCategoriePdf(List<Category> categories, ServletContext context, HttpServletRequest request, HttpServletResponse response) {
		Document document = new Document(PageSize.A4,15, 15, 45, 30);
		try {
			String filePath = context.getRealPath("/resources/reports");
			File file = new File(filePath);
			boolean exist = new File(filePath).exists();
			if (!exist) {
				new File(filePath).mkdirs();
			}

			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file+"/"+"categories"+".pdf"));
			document.open();

			Font mainFont = FontFactory.getFont("Arial", 14, BaseColor.BLACK);

			Paragraph paragraph = new Paragraph("LISTE DES CATEGORIES", mainFont);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setIndentationLeft(50);
			paragraph.setIndentationRight(50);
			paragraph.setSpacingAfter(10);
			document.add(paragraph);

			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100);
			table.setSpacingBefore(10f);
			table.setSpacingAfter(10);

			Font tableHeader = FontFactory.getFont("Arial", 10, BaseColor.BLACK);
			Font tableBody = FontFactory.getFont("Arial", 9, BaseColor.BLACK);

			PdfPCell code = new PdfPCell(new Paragraph("Code", tableHeader));
			code.setBorderColor(BaseColor.BLACK);
			code.setPaddingLeft(10);
			code.setHorizontalAlignment(Element.ALIGN_CENTER);
			code.setVerticalAlignment(Element.ALIGN_CENTER);
			code.setBackgroundColor(BaseColor.GRAY);
			code.setExtraParagraphSpace(5f);
			table.addCell(code);

			PdfPCell designation = new PdfPCell(new Paragraph("Designation", tableHeader));
			designation.setBorderColor(BaseColor.BLACK);
			designation.setPaddingLeft(10);
			designation.setHorizontalAlignment(Element.ALIGN_CENTER);
			designation.setVerticalAlignment(Element.ALIGN_CENTER);
			designation.setBackgroundColor(BaseColor.GRAY);
			designation.setExtraParagraphSpace(5f);
			table.addCell(designation);

			for (Category category: categories) {
				PdfPCell codeValue = new PdfPCell(new Paragraph(category.getCode(), tableBody));
				codeValue.setBorderColor(BaseColor.BLACK);
				codeValue.setPaddingLeft(10);
				codeValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				codeValue.setVerticalAlignment(Element.ALIGN_CENTER);
				codeValue.setBackgroundColor(BaseColor.WHITE);
				codeValue.setExtraParagraphSpace(5f);
				table.addCell(codeValue);

				PdfPCell designationValue = new PdfPCell(new Paragraph(category.getDesignation(), tableBody));
				designationValue.setBorderColor(BaseColor.BLACK);
				designationValue.setPaddingLeft(10);
				designationValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				designationValue.setVerticalAlignment(Element.ALIGN_CENTER);
				designationValue.setBackgroundColor(BaseColor.WHITE);
				designationValue.setExtraParagraphSpace(5f);
				table.addCell(designationValue);

			}

			document.add(table);
			document.close();
			writer.close();
			return true;

		} catch (Exception e) {
			return false;
		}

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
			FileOutputStream outputStream = new FileOutputStream(file+"/"+"categories"+".xlsx");
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet workSheet = workbook.createSheet("Categories");
			workSheet.setDefaultColumnWidth(30);

			HSSFCellStyle headerCellStyle = workbook.createCellStyle();
			//headerCellStyle.setFillForegroundColor(HSSFColor.BLUE.index);
			//headerCellStyle.setFillPattern();

			HSSFRow headerRow = workSheet.createRow(0);

			HSSFCell code = headerRow.createCell(0);
			code.setCellValue("Code");
			code.setCellStyle(headerCellStyle);

			HSSFCell designation = headerRow.createCell(1);
			designation.setCellValue("Designation");
			designation.setCellStyle(headerCellStyle);

			int i = 1;
			for (Category category: categories) {
				HSSFRow bodyRow = workSheet.createRow(i);

				HSSFCellStyle bodyCellStyle = workbook.createCellStyle();
				//bodyCellStyle.setFillBackgroundColor(HSSFColor.);

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
