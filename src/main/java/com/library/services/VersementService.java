package com.library.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.library.entities.Contrat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.library.entities.Versement;
import org.springframework.web.multipart.MultipartFile;


public interface VersementService {
	
	List<Versement> findAllVersements();
	Optional<Versement> findVersementById(Long id);
	
	Versement findByNumVersement(String numVersement);
	Versement findByNumeroRecu(String numeroRecu);
	Versement findByNature(String nature);
	
	List<Versement> findListVersementByNumVersement(String numVersement);
	List<Versement> findListVersementByNature(String nature);
	List<Versement> findVersementByEmployeId(Long empId);
	
	
//	Versement saveVersement(Long empId, Versement versement);
	Versement saveVersement(Versement versement);
	
	Versement updateVersement(Long id, Versement versement);
	void deleteVersement(Long id);
	
	
	Page<Versement> findAllVersementsByPageable(Pageable page);
	Page<Versement>findAllVersementsByEmploye(Long empId, Pageable pageable);
	
	Page<Versement> findVersementByKeyWord(String mc, Pageable pageable);

	Versement createVersement(String versement, MultipartFile fileVersement) throws JsonParseException, JsonMappingException, IOException;
	
	
}
