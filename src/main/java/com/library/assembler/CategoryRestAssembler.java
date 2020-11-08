package com.library.assembler;

import com.library.controller.model.CategoryModel;
import com.library.entities.Category;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryRestAssembler {

    public CategoryModel assembledEntityToModel(Category category) {
        return CategoryModel.builder()
                .id(category.getId())
                .code(category.getCode())
                .designation(category.getDesignation())
                .build();
    }

    public List<CategoryModel> assembledEntityToModel(List<Category> categories) {
        List<CategoryModel> categoryModels = new ArrayList<>();
        for (Category category : categories) {
            categoryModels.add(assembledEntityToModel(category));
        }
        return categoryModels;
    }
}