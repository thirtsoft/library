package com.library.services.impl;

import com.library.entities.LigneAvoir;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.LigneAvoirRepository;
import com.library.services.LigneAvoirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LigneAvoirServiceImpl implements LigneAvoirService {

    @Autowired
    private LigneAvoirRepository ligneAvoirRepository;

    @Override
    public List<LigneAvoir> findAllLigneAvoirs() {
        return ligneAvoirRepository.findAll();
    }

    @Override
    public List<LigneAvoir> findAllLigneAvoirsOrderDesc() {
        return ligneAvoirRepository.findByOrderByIdDesc();
    }

    @Override
    public Optional<LigneAvoir> findLigneAvoirById(Long lAvoirId) {
        if (!ligneAvoirRepository.existsById(lAvoirId)) {
            throw new ResourceNotFoundException("This LigneAvoir is not found");
        }

        return ligneAvoirRepository.findById(lAvoirId);
    }

    @Override
    public LigneAvoir saveLigneAvoir(LigneAvoir ligneAvoir) {
        return ligneAvoirRepository.save(ligneAvoir);
    }

    @Override
    public LigneAvoir updateLigneAvoir(Long lAvoirId, LigneAvoir ligneAvoir) {
        if (!ligneAvoirRepository.existsById(lAvoirId)) {
            throw new ResourceNotFoundException("This LigneAvoir is not found");
        }
        Optional<LigneAvoir> lavoir = ligneAvoirRepository.findById(lAvoirId);
        if (!lavoir.isPresent()) {
            throw new ResourceNotFoundException("This LigneAvoir is not found");
        }

        LigneAvoir ligneAvoirResult = lavoir.get();

        ligneAvoirResult.setNumero(ligneAvoir.getNumero());
        ligneAvoirResult.setAvoir(ligneAvoir.getAvoir());
        ligneAvoirResult.setProduit(ligneAvoir.getProduit());
        ligneAvoirResult.setQuantite(ligneAvoir.getQuantite());

        return ligneAvoirRepository.save(ligneAvoirResult);
    }

    @Override
    public void deleteLavoirByNumero(Long numero) {
        ligneAvoirRepository.deleteByNumero(numero);
    }

    @Override
    public List<LigneAvoir> findLigneAvoirByProduitId(Long prodId) {
        return ligneAvoirRepository.ListLigneAvoirByProduitId(prodId);
    }

    @Override
    public List<LigneAvoir> findLigneAvoirByAvoirId(Long avoirId) {
        return ligneAvoirRepository.ListLigneAvoirByAvoirId(avoirId);
    }

    @Override
    public List<LigneAvoir> findAllLavoirByNumero(Long numero) {
        return ligneAvoirRepository.findAllByNumero(numero);
    }
}
