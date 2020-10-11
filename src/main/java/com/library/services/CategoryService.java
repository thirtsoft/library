package com.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.library.entities.Category;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface CategoryService {
	public List<Category> findAllCategory();
	public Category saveCategory(Category category);
	public Optional<Category> findCategoryById(Long i);
	public Category updateCategory(Long catId, Category category);
	public ResponseEntity<Object> deleteCategory(Long catId);
	
	public Category findByCode(String code);
	public Category findByDesignation(String designation);
	
	public List<Category> ListCategoryByCode(String designation);
	public List<Category> ListCategoryByDesignation(String designation);
	
	public Page<Category> findAllCategoryByPage(Pageable page);
	public Page<Category> findCategoryByKeyWord(String mc, Pageable pageable);

	boolean createCategoriePdf(List<Category> categories, ServletContext context, HttpServletRequest request, HttpServletResponse response);

	boolean createCategorieExcel(List<Category> categories, ServletContext context, HttpServletRequest request, HttpServletResponse response);



}
