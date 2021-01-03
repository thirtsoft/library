package com.library.repository;

import com.library.entities.CategorieCharge;
import com.library.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorieChargeRepository extends JpaRepository<CategorieCharge, Long> {
	
	@Query("select c from CategorieCharge c where c.codeCategorieCharge like :code")
	CategorieCharge findByCodeCategorieCharge(@Param("code") String codeCategorieCharge);
	
	@Query("select c from CategorieCharge c where c.codeCategorieCharge like :c")
	List<CategorieCharge> ListCategorieChargeByCodeCategorieCharge(@Param("c") String codeCategorieCharge);
	
	@Query("select c from CategorieCharge c where c.nomCategorieCharge like :des")
	CategorieCharge findByNomCategorieCharge(@Param("des") String nomCategorieCharge);
	
	@Query("select c from CategorieCharge c where c.nomCategorieCharge like :des")
	List<CategorieCharge> ListCategoryByNomCategorieCharge(@Param("des") String nomCategorieCharge);


}