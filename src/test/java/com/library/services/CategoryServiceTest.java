package com.library.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.library.entities.Category;
import com.library.repository.CategoryRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {
	
	@Autowired
	private CategoryService categoryService;
	
	@MockBean
	private CategoryRepository categoryRepository;
	
	@Test
	public void testCreateCategory() {
		Category category = new Category();
		category.setCode("Mobile");
		category.setDesignation("Samsung A10s");
		
		Mockito.when(categoryRepository.save(category)).thenReturn(category);
		
		assertThat(categoryService.saveCategory(category)).isEqualTo(category);
	}
	
	@Test
	public void testFindCategoryById() {
		
		Category category = new Category();
		category.setId((long) 1);
		category.setCode("CCC");
		category.setDesignation("DESC");
		
		categoryRepository.save(category);
		
		boolean isCategory = categoryRepository.findById(category.getId()).isPresent();
		  
		boolean isCat = categoryService.findCategoryById(category.getId()).isPresent();
		
		//Mockito.when(isCategory).thenReturn(isCategory);
		//assertTrue(isCategory);
		//assertTrue(isCat);
		
		//assertThat(isCategory).isEqualTo(isCat);
	}
	
	
	@Test
	public void testFindCategoryByCode() {
		
		Category category = new Category(null, "CAT", "CATACAT");
		
		when(categoryRepository.findByCode(category.getCode())).thenReturn(category);
		
		Category cat = categoryService.findByCode(category.getCode());
		
		assertNotNull(cat);
		assertThat(cat.getCode()).isEqualTo(category.getCode());
		
	}
	
	@Test
	public void testFindCategoryByDesignation() {
		
		Category category = new Category(null, "CAT", "DESIGNATION");
		
		when(categoryRepository.findByDesignation(category.getDesignation())).thenReturn(category);
		
		Category cat = categoryService.findByDesignation(category.getDesignation());
		
		assertNotNull(cat);
		assertThat(cat.getDesignation()).isEqualTo(category.getDesignation());
		
	}
	
	
	@Test
	public void testFindAllCategories() {
		when(categoryRepository.findAll()).thenReturn(Stream
				.of(new Category(null, "Alcatel", "Alcatel 101"), new Category(null, "Samsung", "Samsung 120")).collect(Collectors.toList()));
		assertEquals(2, categoryService.findAllCategory().size());
	}
	
	@Test
	public void testUpdateCategory() {
		
		Category category = new Category(null, "cat8", "Informatique");
		categoryRepository.save(category);
		
		Category cat = new Category();
		
		cat.setId(category.getId());
		cat.setCode(category.getCode());
		
		categoryRepository.save(cat);
	
		Category catUpdate = categoryService.findByCode(cat.getCode());
		
		assertThat(catUpdate.getCode()).isEqualTo(category.getCode());
		
	}
	
	 @Test 
	 public void testDelete() {    
		 Category userTodelete = new Category(1L,"Dupont", "password"); 
		 Mockito.doNothing().when(categoryRepository).deleteById(userTodelete.getId());     
		 categoryService.deleteCategory(userTodelete.getId());   
		// verify(categoryRepository).delete(any(Long.class); 
     } 
	
	
	

}
