package com.library.repository;

import com.library.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCode(String code);

    @Query("select c from Category c where c.code like :c")
    List<Category> ListCategoryByCode(@Param("c") String code);

    Category findByDesignation(String designation);

    @Query("select c from Category c where c.designation like :des")
    List<Category> ListCategoryByDesignation(@Param("des") String designation);


}
