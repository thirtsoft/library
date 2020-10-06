package com.library.services.impl;

import com.library.entities.Avoir;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.AvoirRepository;
import com.library.services.AvoirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AvoirServiceImpl implements AvoirService {

    @Autowired
    private AvoirRepository avoirRepository;

    @Override
    public List<Avoir> findAllAvoirs() {
        return avoirRepository.findAll();
    }

    @Override
    public Optional<Avoir> findAvoirById(Long id) {
        if (!avoirRepository.existsById(id)) {
            throw new ResourceNotFoundException("Avoir N° " + id + "not found");
        }

        return avoirRepository.findById(id);
    }

    @Override
    public Avoir findAvoirByReference(String reference) {
        return avoirRepository.findByReference(reference);
    }

    @Override
    public List<Avoir> findListAvoirByReference(String reference) {
        return avoirRepository.findListAvoirByReference(reference);
    }

    @Override
    public Avoir findAvoirByLibelle(String libelle) {
        return avoirRepository.findByLibelle(libelle);
    }

    @Override
    public List<Avoir> findListAvoirByLibelle(String libelle) {
        return avoirRepository.findListAvoirByLibelle(libelle);
    }

    @Override
    public List<Avoir> findListAvoirByFournisseurId(Long fourId) {
        return avoirRepository.findLitAvoirByFournisseurId(fourId);
    }

    @Override
    public Avoir saveAvoir(Avoir avoir) {
        return avoirRepository.save(avoir);
    }

    @Override
    public Avoir updateAvoir(Long id, Avoir avoir) {
        if (!avoirRepository.existsById(id)) {
            throw new ResourceNotFoundException("Avoir N° " + id + "not found");
        }
        Optional<Avoir> avoirOptional = avoirRepository.findById(id);
        if (!avoirOptional.isPresent()) {
            throw new ResourceNotFoundException("Avoir N° " + id + "not found");
        }

        Avoir avoirResultat = avoirOptional.get();
        avoirResultat.setReference(avoir.getReference());
        avoirResultat.setLibelle(avoir.getLibelle());
        avoirResultat.setSoldeAvoir(avoir.getSoldeAvoir());
        avoirResultat.setNbreJours(avoir.getNbreJours());
        avoirResultat.setFournisseur(avoir.getFournisseur());

        return avoirRepository.save(avoirResultat);
    }

    @Override
    public ResponseEntity<Object> deleteAvoir(Long id) {
        if (!avoirRepository.existsById(id)) {
            throw new ResourceNotFoundException("Avoir N° " + id + "not found");
        }
        avoirRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @Override
    public Page<Avoir> findAllAvoirsByPageable(Pageable page) {
        return avoirRepository.findAllAvoirsByPageable(page);
    }

    @Override
    public Page<Avoir> findAllAvoirsByFournisseur(Long fourId, Pageable pageable) {
        return avoirRepository.findAvoirByFournisseurByPageable(fourId, pageable);
    }

    @Override
    public Page<Avoir> findAvoirByKeyWord(String mc, Pageable pageable) {
        return avoirRepository.findAvoirsByKeyWord(mc, pageable);
    }

}
