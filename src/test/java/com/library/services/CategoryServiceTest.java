package com.library.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
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
	public void testAllCategories() {
		when(categoryRepository.findAll()).thenReturn(Stream
				.of(new Category(null, "Alcatel", "Alcatel 101"), new Category(null, "Samsung", "Samsung 120")).collect(Collectors.toList()));
		assertEquals(2, categoryService.findAllCategory().size());
	}
	
	

}
