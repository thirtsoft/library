package com.library.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.library.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	/*
	 * @Query("select c from Category c where c.ref like :ref") public Category
	 * findByReference(@Param("ref") String reference);
	 * 
	 * @Query("select c from Category c") public Page<Category>
	 * findCategory(Pageable pageable);
	 * 
	 * @Query("select c from Category c where c.designation like :mc")
	 * Page<Category> findCategoryByKeyWord(@Param("mc") String mc, Pageable page);
	 * 
	 * @Query("select c from Category c where c.designation like :design") public
	 * List<Category> findByDesignation(@Param("design") String designation);
	 * 
	 * @Query("select c from Category c where c.designation like :des") public
	 * Category findByCategory(@Param("des") String designation);
	 */
}
