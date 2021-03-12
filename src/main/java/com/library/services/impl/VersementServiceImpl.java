package com.library.services.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.multipart.MultipartFile;

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
	public Versement findByNumeroRecu(String numeroRecu) {
		return versementRepository.findByNumeroRecu(numeroRecu);
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
	public Versement saveVersement(Versement versement) {
		if (versementRepository.findByNumVersement(versement.getNumVersement()) !=null) {
			throw new IllegalArgumentException("Cet Versement existe");
		}
		versement.setDateVersement(new Date());
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
		versementResult.setNumeroRecu(versement.getNumeroRecu());
		versementResult.setMontantVersement(versement.getMontantVersement());
		versementResult.setNomBank(versement.getNomBank());
		versementResult.setMotif(versement.getMotif());
		versementResult.setDateVersement(versement.getDateVersement());

		return versementRepository.save(versementResult);
	}

	@Override
	public void deleteVersement(Long id) {
		if (!versementRepository.existsById(id)) {
			throw new ResourceNotFoundException("Versement not found");
		}
		versementRepository.deleteById(id);
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

	@Override
	public Versement createVersement(String versement, MultipartFile fileVersement) throws JsonParseException, JsonMappingException, IOException {

		Versement versement_1 = new ObjectMapper().readValue(versement, Versement.class);
		System.out.println(versement_1);

		versement_1.setFileVersement(fileVersement.getOriginalFilename());

		return versementRepository.save(versement_1);

	}


}
