package com.library.services;

import com.library.entities.Category;
import com.library.repository.CategoryRepository;
import com.library.services.impl.CategoryServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    public void testFindAllCategory() {
        Category category = new Category();
        category.setCode("Mobile");
        category.setDesignation("Samsung A10s");
        when(categoryRepository.findAll()).thenReturn(singletonList(category));

        List<Category> categories = categoryService.findAllCategory();

        assertThat(categories).isNotNull();
        assertThat(categories.size()).isEqualTo(1);
        verify(categoryRepository).findAll();
        assertThat(categories.get(0)).isEqualTo(category);
    }

    @Test
    public void testCreateCategory() {

    }

    @Test
    public void testFindCategoryById() {

    }


    @Test
    public void testFindCategoryByCode() {

    }

    @Test
    public void testFindCategoryByDesignation() {

    }


    @Test
    public void testFindAllCategories() {

    }

    @Test
    public void testUpdateCategory() {


    }

    @Test
    public void testDelete() {

    }


}
