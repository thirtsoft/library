package com.library.services.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.library.services.ScategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.library.entities.Scategorie;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.ScategorieRepository;

@Service
@Transactional
public class ScategorieServiceImpl implements ScategorieService {
	
	@Autowired
	private ScategorieRepository scategorieRepository;

	@Override
	public List<Scategorie> findAllScategories() {
		return scategorieRepository.findAll();
	}

	@Override
	public Optional<Scategorie> findScategorieById(Long sCatId) {
		if (!scategorieRepository.existsById(sCatId)) {
			throw new ResourceNotFoundException("Scategorie N° " + sCatId + "not found");
		}
		return scategorieRepository.findById(sCatId);
	}

	@Override
	public Scategorie findByCode(String code) {
		return scategorieRepository.findByCode(code);
	}

	@Override
	public List<Scategorie> findListScategorieByCode(String code) {
		return scategorieRepository.findListScategorieByCode(code);
	}

	@Override
	public Scategorie findByLibelle(String libelle) {
		return scategorieRepository.findByLibelle(libelle);
	}

	@Override
	public List<Scategorie> findListScategorieByLibelle(String libelle) {
		return scategorieRepository.findListScategorieByLibelle(libelle);
	}

	@Override
	public List<Scategorie> findScategorieByCateoryId(Long catId) {
		return scategorieRepository.findScategorieByCateoryId(catId);
	}

	@Override
	public Scategorie saveScategorie(Scategorie sCategorie) {
		return scategorieRepository.save(sCategorie);
	}

	@Override
	public Scategorie updateScategorie(Long sCatId, Scategorie sCategorie) {
		if (!scategorieRepository.existsById(sCatId)) {
			throw new ResourceNotFoundException("Scategorie N° " + sCatId + "not found");
		}
		Optional<Scategorie> Scat = scategorieRepository.findById(sCatId);
		
		if (!Scat.isPresent()) {
			throw new ResourceNotFoundException("Scategorie N° " + sCatId + "not found");
		}
		
		Scategorie sCat = Scat.get();
		sCat.setCode(sCategorie.getCode());
		sCat.setLibelle(sCategorie.getLibelle());
		
		return scategorieRepository.save(sCat);
	}

	@Override
	public ResponseEntity<Object> deleteScategorie(Long sCatId) {
		if (!scategorieRepository.existsById(sCatId)) {
			throw new ResourceNotFoundException("sCategorie N° " + sCatId + "not found");
		}
		scategorieRepository.deleteById(sCatId);
		
		return ResponseEntity.ok().build();
	}

	@Override
	public Page<Scategorie> findAllScategorietsByPageable(Pageable page) {
		return scategorieRepository.findAllScategoriesByPageable(page);
	}

	@Override
	public Page<Scategorie> findAllScategoriesByCategory(Long catId, Pageable pageable) {
		return scategorieRepository.findScategorieByCateoryPageable(catId, pageable);
	}

	@Override
	public Page<Scategorie> findScategorieByKeyWord(String mc, Pageable pageable) {
		return scategorieRepository.findScategorieByKeyWord(mc, pageable);
	}

}
