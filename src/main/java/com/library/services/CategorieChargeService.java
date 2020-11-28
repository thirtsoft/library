package com.library.services;

import com.library.entities.CategorieCharge;
import com.library.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;


public interface CategorieChargeService {

    List<CategorieCharge> findAllCategorieCharges();

    CategorieCharge saveCategorieCharge(CategorieCharge categorieCharge);

    Optional<CategorieCharge> findCategorieChargeById(Long catId);

    CategorieCharge updateCategorieCharge(Long catId, CategorieCharge categorieCharge);

    void deleteCategorieCharge(Long catId);

    CategorieCharge findByCodeCategorieCharge(String codeCategorieCharge);

    CategorieCharge findByNomCategorieCharge(String nomCategorieCharge);

    List<CategorieCharge> ListCategoryByCodeCategorieCharge(String codeCategorieCharge);

    List<CategorieCharge> ListCategoryByNomCategorieCharge(String nomCategorieCharge);


}
