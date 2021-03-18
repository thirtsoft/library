package com.library.services.impl;

import com.library.entities.LigneApprovisionnement;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.LigneApprovisionnementRepository;
import com.library.services.LigneApprovisionnementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LigneApprovisionnementServiceImpl implements LigneApprovisionnementService {

    @Autowired
    private LigneApprovisionnementRepository ligneApprovisionnementRepository;


    @Override
    public List<LigneApprovisionnement> findAllLigneApprovisionnements() {
        return ligneApprovisionnementRepository.findAll();
    }

    @Override
    public Optional<LigneApprovisionnement> findLigneApprovisionnementById(Long lApproId) {
        if (!ligneApprovisionnementRepository.existsById(lApproId)) {
            throw new ResourceNotFoundException("Ligne Approvisionement Not found");
        }
        return ligneApprovisionnementRepository.findById(lApproId);
    }

    @Override
    public List<LigneApprovisionnement> findListLigneApprovisionnementByProduitId(Long prodId) {
        return ligneApprovisionnementRepository.findListLigneApprovisionnementByProduitId(prodId);
    }

    @Override
    public List<LigneApprovisionnement> findListLigneApprovisionnementByApprovisionnementId(Long approId) {
        return ligneApprovisionnementRepository.findListLigneApprovisionnementByApprovisionnementId(approId);
    }

    @Override
    public Page<LigneApprovisionnement> findAllLigneApprovisionnementByPageable(Pageable pageable) {
        return ligneApprovisionnementRepository.findAllLigneApprovisionnementByPageable(pageable);
    }

    @Override
    public Page<LigneApprovisionnement> findAllLigneApprovisionnementByApproviosionnement(Long approId, Pageable pageable) {
        return ligneApprovisionnementRepository.findLigneApprovisionnementByApprovisionnementPageable(approId, pageable);
    }

    @Override
    public Page<LigneApprovisionnement> findAllLigneApprovisionnementByProduit(Long prodId, Pageable pageable) {
        return ligneApprovisionnementRepository.findLigneApprovisionnementByProduitPageable(prodId, pageable);
    }

    @Override
    public LigneApprovisionnement saveLigneApprovisionnement(LigneApprovisionnement ligneApprovisionnement) {
        return ligneApprovisionnementRepository.save(ligneApprovisionnement);
    }

    @Override
    public LigneApprovisionnement updateLigneApprovisionnement(Long lApproId, LigneApprovisionnement ligneApprovisionnement) {
        if (!ligneApprovisionnementRepository.existsById(lApproId)) {
            throw new ResourceNotFoundException("Ligne Approvisionnement N° " + lApproId + "not found");
        }
        Optional<LigneApprovisionnement>  lAppro = ligneApprovisionnementRepository.findById(lApproId);
        if (!lAppro.isPresent()) {
            throw new ResourceNotFoundException("Ligne Approvisionnement N° " + lApproId + "not found");
        }
        LigneApprovisionnement lApproResultat = lAppro.get();

        lApproResultat.setNumero(ligneApprovisionnement.getNumero());
        lApproResultat.setPrix(ligneApprovisionnement.getPrix());
        lApproResultat.setQuantite(ligneApprovisionnement.getQuantite());
        lApproResultat.setApprovisionnement(ligneApprovisionnement.getApprovisionnement());
        lApproResultat.setProduit(ligneApprovisionnement.getProduit());

        return ligneApprovisionnementRepository.save(lApproResultat);
    }

    @Override
    public ResponseEntity<Object> deleteLigneApprovisionnement(Long lApproid) {
        if (!ligneApprovisionnementRepository.existsById(lApproid)) {
            throw new ResourceNotFoundException("Ligne Approvisionnement N° " + lApproid + "not found");
        }
        ligneApprovisionnementRepository.deleteById(lApproid);

        return ResponseEntity.ok().build();
    }

    @Override
    public void deleteLApproByNumero(long numero) {
        ligneApprovisionnementRepository.deleteByNumero(numero);
    }

    @Override
    public List<LigneApprovisionnement> findAllLApproByNumero(long numero) {
        return ligneApprovisionnementRepository.findAllByNumero(numero);
    }
}
