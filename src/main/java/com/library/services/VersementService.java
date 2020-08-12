package com.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.library.entities.Versement;


public interface VersementService {
	
	public List<Versement> findAllVersements();
	public Optional<Versement> findVersementById(Long id);
	
	public Versement findByNumVersement(String numVersement);
	public Versement findByNature(String nature);
	
	public List<Versement> findListVersementByNumVersement(String numVersement);
	public List<Versement> findListVersementByNature(String nature);
	public List<Versement> findVersementByEmployeId(Long empId);
	
	
	public Versement saveVersement(Long empId, Versement versement);
	public Versement saveVersement(Versement versement);
	
	public Versement updateVersement(Long id, Versement versement);
	public ResponseEntity<Object> deleteVersement(Long id);
	
	
	public Page<Versement> findAllVersementsByPageable(Pageable page);
	public Page<Versement>findAllVersementsByEmploye(Long empId, Pageable pageable);
	
	public Page<Versement> findVersementByKeyWord(String mc, Pageable pageable);
	
	
}
