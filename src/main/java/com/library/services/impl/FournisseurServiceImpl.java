package com.library.services.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.library.services.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.library.entities.Fournisseur;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.FournisseurRepository;

@Service
@Transactional
public class FournisseurServiceImpl implements FournisseurService {
	
	@Autowired
	private FournisseurRepository fournisseurRepository;
	
	@Override
	public List<Fournisseur> findAllFournisseurs() {
		return fournisseurRepository.findAll();
	}

	@Override
	public Optional<Fournisseur> findProduitById(Long id) {

		if(!fournisseurRepository.existsById(id)) {
			throw new ResourceNotFoundException("Fournisseur that id is" + id + "not found");
		}
		return fournisseurRepository.findById(id);
	}

	@Override
	public Fournisseur saveFournisseur(Fournisseur fournisseur) {
		return fournisseurRepository.save(fournisseur);
	}

	@Override
	public Fournisseur updateFournisseurt(Long id, Fournisseur fournisseur) {
		if(!fournisseurRepository.existsById(id)) {
			throw new ResourceNotFoundException("Fournisseur that id is" + id + "not found"); 
		}
		Optional<Fournisseur> four = fournisseurRepository.findById(id);
		if(!four.isPresent()) {
			throw new ResourceNotFoundException("Fournisseur that id is" + id + "not found"); 
		}
		
		Fournisseur  fournisseurResult = four.get();
		fournisseurResult.setCode(fournisseur.getCode());
		fournisseurResult.setNom(fournisseur.getNom());
		fournisseurResult.setRaisonSociale(fournisseur.getRaisonSociale());
		fournisseurResult.setAdresse(fournisseur.getAdresse());
		fournisseurResult.setTelephone(fournisseur.getTelephone());
		fournisseurResult.setFax(fournisseur.getFax());
		fournisseurResult.setEmail(fournisseur.getEmail());
		
		return fournisseurRepository.save(fournisseurResult);
	}

	@Override
	public ResponseEntity<Object> deleteFournisseur(Long id) {
		if (!fournisseurRepository.existsById(id)) {
			throw new ResourceNotFoundException("Fournisseur that id is" + id + "is not found");
		}
		fournisseurRepository.deleteById(id);
		
		return ResponseEntity.ok().build();
	}

	@Override
	public Fournisseur findByCode(String code) {
		return fournisseurRepository.findByCode(code);
	}

	@Override
	public Fournisseur findByNom(String nom) {
		return fournisseurRepository.findByNom(nom);
	}

	@Override
	public Fournisseur findByRaisonSociale(String raisonSociale) {
		return fournisseurRepository.findByRaisonSociale(raisonSociale);
	}

	@Override
	public List<Fournisseur> findListFournisseurByCode(String code) {
		return fournisseurRepository.ListFournisseurByCode(code);
	}

	@Override
	public List<Fournisseur> findListFournisseurByNom(String nom) {
		return fournisseurRepository.ListFournisseurByNom(nom);
	}
	
	@Override
	public List<Fournisseur> findListFournisseurByRaisonSociale(String raisonSociale) {
		return fournisseurRepository.ListFournisseurByNom(raisonSociale);
	}


	@Override
	public Page<Fournisseur> findAllFournisseursByPageable(Pageable page) {
		return fournisseurRepository.findAll(page);
	}

	@Override
	public Page<Fournisseur> findFournisseurByKeyWord(String mc, Pageable pageable) {
		return fournisseurRepository.findFournisseurByKeyWord(mc, pageable);
	}

}
