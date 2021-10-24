package com.library.services;

import com.library.entities.Scategorie;
import com.library.entities.Scategorie;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public interface ScategorieService {

    List<Scategorie> findAllScategories();

    List<Scategorie> findAllScategoriesOrderDesc();

    Optional<Scategorie> findScategorieById(Long sCatId);

    Scategorie findByCode(String code);

    List<Scategorie> findListScategorieByCode(String code);

    Scategorie findByLibelle(String libelle);

    List<Scategorie> findListScategorieByLibelle(String libelle);

    List<Scategorie> findScategorieByCateoryId(Long catId);

    Scategorie saveScategorie(Scategorie sCategorie);

    Scategorie updateScategorie(Long sCatId, Scategorie sCategorie);

    void deleteScategorie(Long sCatId);

    //  boolean createScategoriePdf(List<Scategorie> scategories, ServletContext context, HttpServletRequest request, HttpServletResponse response);

    boolean createScategorieExcel(List<Scategorie> scategories, ServletContext context, HttpServletRequest request, HttpServletResponse response);


}
