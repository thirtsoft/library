package com.library.services.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.library.entities.CategorieCharge;
import com.library.entities.Category;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.CategorieChargeRepository;
import com.library.repository.CategoryRepository;
import com.library.services.CategorieChargeService;
import com.library.services.CategoryService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategorieChargeServiceImpl implements CategorieChargeService {
	
	@Autowired
	private CategorieChargeRepository catChargeRepository;


	@Override
	public List<CategorieCharge> findAllCategorieCharges() {
		return catChargeRepository.findAll();
	}

	@Override
	public CategorieCharge saveCategorieCharge(CategorieCharge categorieCharge) {
		if (catChargeRepository.findByCodeCategorieCharge(categorieCharge.getCodeCategorieCharge()) != null) {
			throw new IllegalArgumentException("Categorie de charge existe");
		}
		return catChargeRepository.save(categorieCharge);
	}

	@Override
	public Optional<CategorieCharge> findCategorieChargeById(Long catId) {
		if (!catChargeRepository.existsById(catId)) {
			throw new ResourceNotFoundException("This CategorieCharge is not found");
		}

		return catChargeRepository.findById(catId);
	}

	@Override
	public CategorieCharge updateCategorieCharge(Long catId, CategorieCharge categorieCharge) {
		if (!catChargeRepository.existsById(catId)) {
			throw new ResourceNotFoundException("This CategorieCharge is not found");
		}
		Optional<CategorieCharge> categorieChargeInfo = catChargeRepository.findById(catId);
		if (!categorieChargeInfo.isPresent()) {
			throw new ResourceNotFoundException("This CategorieCharge is not found");
		}

		CategorieCharge catCharge = categorieChargeInfo.get();
		catCharge.setCodeCategorieCharge(categorieCharge.getCodeCategorieCharge());
		catCharge.setNomCategorieCharge(categorieCharge.getNomCategorieCharge());

		return catChargeRepository.save(catCharge);
	}

	@Override
	public void deleteCategorieCharge(Long catId) {
		if (!catChargeRepository.existsById(catId)) {
			throw new ResourceNotFoundException("This CategorieCharge is not found");
		}

		catChargeRepository.deleteById(catId);

	}

	@Override
	public CategorieCharge findByCodeCategorieCharge(String codeCategorieCharge) {
		return catChargeRepository.findByCodeCategorieCharge(codeCategorieCharge);
	}

	@Override
	public CategorieCharge findByNomCategorieCharge(String nomCategorieCharge) {
		return catChargeRepository.findByNomCategorieCharge(nomCategorieCharge);
	}

	@Override
	public List<CategorieCharge> ListCategoryByCodeCategorieCharge(String codeCategorieCharge) {
		return catChargeRepository.ListCategorieChargeByCodeCategorieCharge(codeCategorieCharge);
	}

	@Override
	public List<CategorieCharge> ListCategoryByNomCategorieCharge(String nomCategorieCharge) {
		return catChargeRepository.ListCategoryByNomCategorieCharge(nomCategorieCharge);
	}
}
