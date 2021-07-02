package com.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.entities.Category;
import com.library.services.CategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //	@MockBean
    @Mock
    private CategoryService categoryService;

//	@MockBean
//	private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryController categoryController;

    private Category category;

    private List<Category> categoryList;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setup() {
        category = new Category(1L, "PC", "HP ProBooks");
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @AfterEach
    void tearDown() {
        category = null;
    }


    @Test
    public void PostMappingOfCategory() throws Exception {
        when(categoryService.saveCategory(any())).thenReturn(category);
        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(category)))
                .andExpect(status().isOk());
        verify(categoryService, times(1)).saveCategory(any());
    }

}
