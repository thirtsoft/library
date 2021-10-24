package com.library.services.impl;

import com.library.entities.LigneDevis;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.LigneDevisRepository;
import com.library.services.LigneDevisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LigneDevisServiceImpl implements LigneDevisService {

    @Autowired
    private LigneDevisRepository ligneDevisRepository;

    @Override
    public List<LigneDevis> findAllLigneDevis() {
        return ligneDevisRepository.findAll();
    }

    @Override
    public List<LigneDevis> findAllLigneDevissOrderDesc() {
        return ligneDevisRepository.findByOrderByIdDesc();
    }

    @Override
    public Optional<LigneDevis> findLigneDevisById(Long ldevId) {
        if (!ligneDevisRepository.existsById(ldevId)) {
            throw new ResourceNotFoundException("Ligne Devis Not found");
        }

        return ligneDevisRepository.findById(ldevId);
    }

    @Override
    public LigneDevis saveLigneDevis(LigneDevis ligneDevis) {
        return ligneDevisRepository.save(ligneDevis);
    }

    @Override
    public LigneDevis updateLigneDevis(Long ldevId, LigneDevis ligneDevis) {
        if (!ligneDevisRepository.existsById(ldevId)) {
            throw new ResourceNotFoundException("Ligne Commande N° not found");
        }
        Optional<LigneDevis> ldevis = ligneDevisRepository.findById(ldevId);
        if (!ldevis.isPresent()) {
            throw new ResourceNotFoundException("Ligne Commande Not found");
        }

        LigneDevis lignedevResult = ldevis.get();

        lignedevResult.setProduit(ligneDevis.getProduit());
        lignedevResult.setDevis(ligneDevis.getDevis());
        lignedevResult.setQuantite(ligneDevis.getQuantite());

        return ligneDevisRepository.save(lignedevResult);
    }

    @Override
    public void deleteLigneDevis(Long id) {
        if (!ligneDevisRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ligne Commande Not found");
        }

        ligneDevisRepository.deleteById(id);
    }

    @Override
    public void deleteLigneDevisByNumero(Long numero) {
        ligneDevisRepository.deleteByNumero(numero);
    }

    @Override
    public List<LigneDevis> findLigneDevisByProduitId(Long prodId) {
        return ligneDevisRepository.ListLigneDevisByProduitId(prodId);
    }

    @Override
    public List<LigneDevis> findLigneDevisByDevId(Long devId) {
        return ligneDevisRepository.ListLigneDevisByDevisId(devId);
    }

    @Override
    public List<LigneDevis> findAllLigneDevisByNumero(Long numero) {
        return ligneDevisRepository.findAllByNumero(numero);
    }

    @Override
    public List<LigneDevis> saveListLigneDevis(List<LigneDevis> ligneDevis) {
        return ligneDevisRepository.saveAll(ligneDevis);
    }
}
