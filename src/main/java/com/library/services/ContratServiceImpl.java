package com.library.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.library.entities.Contrat;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.ContratRepository;

@Service
@Transactional
public class ContratServiceImpl implements ContratService {
	
	@Autowired
	private ContratRepository contratRepository;

	@Override
	public List<Contrat> findAllContrats() {
		return contratRepository.findAll();
	}

	@Override
	public Optional<Contrat> findContrattById(Long id) {
		if (!contratRepository.existsById(id)) {
			throw new ResourceNotFoundException("Contrat N° " + id + "not found");
		}
		return contratRepository.findById(id);
	}

	@Override
	public Contrat findByReference(String reference) {
		return contratRepository.findByReference(reference);
	}

	@Override
	public List<Contrat> findListContratByReference(String reference) {
		return contratRepository.findListContratByReference(reference);
	}

	@Override
	public Contrat findByNature(String nature) {
		return contratRepository.findByNature(nature);
	}

	@Override
	public List<Contrat> findListContratByNature(String nature) {
		return contratRepository.findListContratByNature(nature);
	}

	@Override
	public List<Contrat> findContratByClientId(Long clientId) {
		return contratRepository.findLitContratByClientId(clientId);
	}

	@Override
	public Contrat saveContrat(Contrat contrat) {
		return contratRepository.save(contrat);
	}

	@Override
	public Contrat updateProduit(Long id, Contrat contrat) {
		if (!contratRepository.existsById(id)) {
			throw new ResourceNotFoundException("Contrat N° " + id + "not found");
		}
		
		Optional<Contrat> cont = contratRepository.findById(id);
		if (!cont.isPresent()) {
			throw new ResourceNotFoundException("Contrat N° " + id + "not found");
		}
		
		Contrat contratResult = cont.get();
		
		contratResult.setReference(contrat.getReference());
		contratResult.setNature(contrat.getNature());
		contratResult.setDescription(contrat.getDescription());
		contratResult.setDateContrat(contrat.getDateContrat());
		
		return contratRepository.save(contratResult);
	}

	@Override
	public ResponseEntity<Object> deleteContrat(Long id) {
		if (!contratRepository.existsById(id)) {
			throw new ResourceNotFoundException("Contrat N° " + id + "not found");
		}
		contratRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@Override
	public Page<Contrat> findAllContratsByPageable(Pageable page) {
		return contratRepository.findAllContratsByPageable(page);
	}

	@Override
	public Page<Contrat> findAllContratsByClient(Long clientId, Pageable pageable) {
		return contratRepository.findContratByClientPageable(clientId, pageable);
	}

	@Override
	public Page<Contrat> findContratByKeyWord(String mc, Pageable pageable) {
		return contratRepository.findContratsByKeyWord(mc, pageable);
	}

	@Override
	public boolean createPdf(List<Contrat> contrats, ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createExcel(List<Contrat> contrats, ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		return false;
	}

}
