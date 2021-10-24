package com.library.services.impl;

import com.library.entities.LigneApprovisionnement;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.LigneApprovisionnementRepository;
import com.library.services.LigneApprovisionnementService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<LigneApprovisionnement> findAllLigneApprovisionnementsOrderDesc() {
        return ligneApprovisionnementRepository.findByOrderByIdDesc();
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
    public LigneApprovisionnement saveLigneApprovisionnement(LigneApprovisionnement ligneApprovisionnement) {
        return ligneApprovisionnementRepository.save(ligneApprovisionnement);
    }

    @Override
    public LigneApprovisionnement updateLigneApprovisionnement(Long lApproId, LigneApprovisionnement ligneApprovisionnement) {
        if (!ligneApprovisionnementRepository.existsById(lApproId)) {
            throw new ResourceNotFoundException("Ligne Approvisionnement N° " + lApproId + "not found");
        }
        Optional<LigneApprovisionnement> lAppro = ligneApprovisionnementRepository.findById(lApproId);
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
    public void deleteLApproByNumero(Long numero) {
        ligneApprovisionnementRepository.deleteByNumero(numero);
    }

    @Override
    public List<LigneApprovisionnement> findAllLApproByNumero(Long numero) {
        return ligneApprovisionnementRepository.findAllByNumero(numero);
    }
}
