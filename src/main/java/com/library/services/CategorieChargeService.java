package com.library.services;

import com.library.entities.CategorieCharge;

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
