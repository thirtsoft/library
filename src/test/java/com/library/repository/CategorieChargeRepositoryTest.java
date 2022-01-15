package com.library.repository;

import com.library.entities.CategorieCharge;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategorieChargeRepositoryTest {
/*
    @Autowired
    private CategorieChargeRepository categorieChargeRepository;

    @Test
    @Rollback(false)
    public void testCreateCategorieCharge() {
        CategorieCharge categorieCharge = new CategorieCharge(1L, "Bureau", "Chaise Roulante");

        CategorieCharge saveCategorieCharge = categorieChargeRepository.save(categorieCharge);

        assertNotNull(saveCategorieCharge);
    }*/

/*    @Test
    public void testFindCategoryById() {
        long catId = 2;
        boolean isCategory = categoryRepository.findById(catId).isPresent();

        assertTrue(isCategory);
    }

    @Test
    public void testFindCategoryByCode() {
        String code = "CAT";
        Category category = categoryRepository.findByCode(code);

        assertThat(category.getCode()).isEqualTo(code);
    }

    @Test
    public void testFindCategoryByCodeNotExist() {
        String code = "CAT7";
        Category category = categoryRepository.findByCode(code);

        assertNull(category);
    }

    @Test
    public void testFindCategoryByDesignation() {
        String designation = "PROD1";
        Category category = categoryRepository.findByDesignation(designation);

        assertThat(category.getDesignation()).isEqualTo(designation);
    }

    @Test
    @Rollback(false)
    public void TestUpdateCategory() {
        String categoryDesignation = "PAPIER RAM";
        Category cat = new Category(null, "Bureau", categoryDesignation);
        cat.setId((long) 16);
        categoryRepository.save(cat);

        Category categoryUpdate = categoryRepository.findByDesignation(categoryDesignation);

        assertThat(categoryUpdate.getDesignation()).isEqualTo(categoryDesignation);

    }

    @Test
    public void testListCategories() {
        List<Category> categories = categoryRepository.findAll();

        for (Category category: categories) {
            System.out.println(category);
        }
        assertThat(categories).size().isGreaterThan(0);
    }*/

   /* @Test
    @Rollback(false)
    public void testDeleteCategory() {
        Long id = (long) 7;

        boolean isExistBeforeDelete = categoryRepository.findById(id).isPresent();

        categoryRepository.deleteById(id);

        boolean notExistAfterDelete = categoryRepository.findById(id).isPresent();

        assertTrue(isExistBeforeDelete);

        assertFalse(notExistAfterDelete);
    }*/


}
