package com.library.services.impl;

import java.util.List;
import java.util.Optional;


import com.library.services.VersementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.library.entities.Versement;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.VersementRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VersementServiceImpl implements VersementService {
	
	@Autowired
	private VersementRepository versementRepository;
	
	@Override
	public List<Versement> findAllVersements() {
		return versementRepository.findAll();
	}

	@Override
	public Optional<Versement> findVersementById(Long id) {
		if (!versementRepository.existsById(id)) {
			throw new ResourceNotFoundException("Versement N° " + id + "not found");
		}
		
		return versementRepository.findById(id); 
	}

	@Override
	public Versement findByNumVersement(String numVersement) {
		return versementRepository.findByNumVersement(numVersement);
	}

	@Override
	public Versement findByNature(String nature) {
		return versementRepository.findByNature(nature);
	}

	@Override
	public List<Versement> findListVersementByNumVersement(String numVersement) {
		return versementRepository.findListVersementByNumVersement(numVersement);
	}
	
	@Override
	public List<Versement> findListVersementByNature(String nature) {
		return versementRepository.findListVersementNature(nature);
	}

	@Override
	public List<Versement> findVersementByEmployeId(Long empId) {
		return versementRepository.findVersementByEmployeId(empId);
	}

	@Override
	public Versement saveVersement(Long empId, Versement versement) {
		return null;
	}

	@Override
	public Versement saveVersement(Versement versement) {
		return versementRepository.save(versement);
	}

	@Override
	public Versement updateVersement(Long id, Versement versement) {
		if (!versementRepository.existsById(id)) {
			throw new ResourceNotFoundException("Versement N° " + id + "not found");
		}
		Optional<Versement> versment = versementRepository.findById(id);
		if (!versment.isPresent()) {
			throw new ResourceNotFoundException("Versment N° " + id + "not found");
		}
		
		Versement versementResult = versment.get();
		
		versementResult.setNumVersement(versement.getNumVersement());
		versementResult.setNature(versement.getNature());
		versementResult.setMontantVersement(versement.getMontantVersement());
		versementResult.setDateVersement(versement.getDateVersement());
		
		return versementRepository.save(versementResult);
	}

	@Override
	public ResponseEntity<Object> deleteVersement(Long id) {
		if (!versementRepository.existsById(id)) {
			throw new ResourceNotFoundException("Versement N° " + id + "not found");
		}
		versementRepository.deleteById(id);
		
		return ResponseEntity.ok().build();
	}

	@Override
	public Page<Versement> findAllVersementsByPageable(Pageable page) {
		return versementRepository.findAllVersementsByPageable(page);
	}

	@Override
	public Page<Versement> findAllVersementsByEmploye(Long empId, Pageable pageable) {
		return versementRepository.findVersementByEmployeId(empId, pageable);
	}

	@Override
	public Page<Versement> findVersementByKeyWord(String mc, Pageable pageable) {
		return versementRepository.findVersementByKeyWord(mc, pageable);
	}

	

}
