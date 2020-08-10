package com.library.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
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
	
	@SuppressWarnings("deprecation")
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

}
