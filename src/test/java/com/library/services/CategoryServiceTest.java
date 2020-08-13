package com.library.services;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.library.entities.Category;
import com.library.repository.CategoryRepository;


@SpringBootTest
public class CategoryServiceTest {
	
	@Autowired
	private CategoryService categoryService;
	
	@MockBean
	private CategoryRepository categoryRepository;
	
	@Test
	public void testCreateTicket() {
		Category category = new Category();
		category.setCode("Mobile");
		category.setDesignation("Samsung A10s");
		
		Mockito.when(categoryRepository.save(category)).thenReturn(category);
		
		assertThat(categoryService.saveCategory(category)).isEqualTo(category);
	}

}
