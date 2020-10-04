package com.library.services.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.library.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.library.entities.Category;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.CategoryRepository;

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

	

}
