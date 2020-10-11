package com.library.controller;

import java.io.*;
import java.util.List;
import java.util.Locale;

import com.library.entities.Produit;
import com.library.message.ResponseMessage;
import com.library.services.ExcelService;
import com.library.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

import com.library.entities.Category;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.CategoryService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin("*")
@RequestMapping("/alAmine")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ExcelService excelService;
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ServletContext context;
	
	@GetMapping("/categories")
	public List<Category> getAllCategory() {
		return categoryService.findAllCategory();
		
	}
	
	@GetMapping("/categories/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable(value = "id") Long id)
			throws ResourceNotFoundException{
		Category category = categoryService.findCategoryById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Category that id is" + id + "not found"));
		return ResponseEntity.ok().body(category);
		
	}
	
	@GetMapping("/searchCategoryByCode")
	public Category getCategoryByCode(@RequestParam(value = "code") String code) {
		return categoryService.findByCode(code);
		
	}
	
	@GetMapping("/searchListCategoryByCode")
	public List<Category> getAllCategoryByCode(@RequestParam(value = "c") String code) {
		
		return categoryService.ListCategoryByDesignation("%"+code+"%");
		
	}
	
	@GetMapping("/searchCategoryByDesignation")
	public Category getCategoryByDesignation(@RequestParam(value = "des") String designation) {
		return categoryService.findByDesignation(designation);
		
	}
	
	@GetMapping("/searchListCategoryByDesignation")
	public List<Category> getAllCategoryByDesignation(@RequestParam(value = "des") String designation) {
		
		return categoryService.ListCategoryByDesignation("%"+designation+"%");
		
	}
	@GetMapping("/searchListCategoryByPageable")
	public Page<Category> getAllCategoryByPageable(@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size) {
		return categoryService.findAllCategoryByPage(PageRequest.of(page, size));
	}
	
	@GetMapping(value = "/searchListCategoryByPageableParMotCle")
	public Page<Category> findCategoryByKeyWord(@RequestParam(name="mc", defaultValue="") String mc, 
			@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size) {
		return categoryService.findCategoryByKeyWord("%"+mc+"%", PageRequest.of(page, size));
	}
	
	
	@PostMapping("/categories") 
	public Category createCategory(@RequestBody Category category) {
		return categoryService.saveCategory(category);	
	}
	
	@PutMapping("/categories/{catId}")
	public ResponseEntity<Category>  updateCategory(@PathVariable(value = "catId") Long catId, @RequestBody Category category) {
		category.setId(catId);
		return new ResponseEntity<>(categoryService.saveCategory(category), HttpStatus.OK);
		
	}
	@DeleteMapping("/categories/{id}")
	public ResponseEntity<Object> deleteCategory(@PathVariable(value = "id") Long id) {
		return categoryService.deleteCategory(id);
		
	}

	@GetMapping(value = "/createCategoriePdf")
	public void createCategoriePdf(HttpServletRequest request, HttpServletResponse response) {
		List<Category> categories = categoryService.findAllCategory();
		boolean isFlag = categoryService.createCategoriePdf(categories, context, request, response);

		if (isFlag) {
			String fullPath = request.getServletContext().getRealPath("/resources/reports/" + "categories" + ".pdf");
			filedownload(fullPath, response, "categories.pdf");
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
				response.setHeader("content-disposition", "attachment; filename=" + fileName);
				OutputStream outputStream = response.getOutputStream();
				byte[] buffer = new byte[BUFFER_SIZE];
				int byteRead = -1;
				while ((byteRead = inputStream.read(buffer)) != -1) {
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


	@PostMapping(value = "/uploadCategorie")
	public ResponseEntity<ResponseMessage> uploadExcelCategorie(@RequestParam("file") MultipartFile file) {
		String message;
		if (ExcelUtils.isExcelFile(file)) {
			try {
				excelService.storeCategorieFile(file);
				message = messageSource.getMessage("message.upload.success", null, Locale.getDefault()) + file.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(message));
			} catch (Exception e) {
				message = messageSource.getMessage("message.upload.fail", null, Locale.getDefault()) + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		}
		message = messageSource.getMessage("message.upload.notExcelFile", null, Locale.getDefault());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}

	@GetMapping(value = "/download/categories.xlsx")
	public ResponseEntity<InputStreamResource> excelCategoriesReport() throws IOException {
		List<Category> categories = (List<Category>) categoryService.findAllCategory();

		ByteArrayInputStream in = ExcelUtils.CategoriesToExcel(categories);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=categories.xlsx");

		return ResponseEntity
				.ok()
				.headers(headers)
				.body(new InputStreamResource(in));
	}


}
