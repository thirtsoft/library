package com.library.services.impl;

import com.library.entities.LigneCmdClient;
import com.library.entities.LigneCreance;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.LigneCmdClientRepository;
import com.library.repository.LigneCreanceRepository;
import com.library.services.LigneCmdClientService;
import com.library.services.LigneCreanceService;
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
public class LigneCreanceServiceImpl implements LigneCreanceService {

    @Autowired
    private LigneCreanceRepository ligneCreanceRepository;


    @Override
    public List<LigneCreance> findAllLigneCreances() {
        return ligneCreanceRepository.findAll();
    }

    @Override
    public Optional<LigneCreance> findLigneCreanceById(Long lCreanceId) {
        if (!ligneCreanceRepository.existsById(lCreanceId)) {
            throw new ResourceNotFoundException("Ligne Comande N° " + lCreanceId + "not found");
        }

        return ligneCreanceRepository.findById(lCreanceId);
    }

    @Override
    public LigneCreance saveLigneCreance(LigneCreance ligneCreance) {
        return ligneCreanceRepository.save(ligneCreance);
    }

    @Override
    public LigneCreance updateLigneCreance(Long lCreanceId, LigneCreance ligneCreance) {
        if (!ligneCreanceRepository.existsById(lCreanceId)) {
            throw new ResourceNotFoundException("Ligne Creance N° " + lCreanceId + "not found");
        }
        Optional<LigneCreance> lCreance = ligneCreanceRepository.findById(lCreanceId);
        if (!lCreance.isPresent()) {
            throw new ResourceNotFoundException("Ligne Creance N°" + lCreanceId + "not found");
        }

        LigneCreance lcreanceClientResult = lCreance.get();

        lcreanceClientResult.setProduit(ligneCreance.getProduit());
        lcreanceClientResult.setCreance(ligneCreance.getCreance());
        lcreanceClientResult.setQuantite(ligneCreance.getQuantite());

        return ligneCreanceRepository.save(lcreanceClientResult);
    }

    @Override
    public ResponseEntity<Object> deleteLigneCreance(Long creanceId) {
        return null;
    }

    @Override
    public void deleteLcreanceByNumero(long numero) {
        ligneCreanceRepository.deleteByNumero(numero);
    }

    @Override
    public List<LigneCreance> findLigneCreanceByProduitId(Long prodId) {
        return ligneCreanceRepository.ListLigneCreanceByProduitId(prodId);
    }

    @Override
    public List<LigneCreance> findLigneCreanceByCreanceId(Long creanceId) {
        return ligneCreanceRepository.ListLigneCreanceByCreanceId(creanceId);
    }

    @Override
    public List<LigneCreance> findAllLcreanceByNumero(long numero) {
        return ligneCreanceRepository.findAllByNumero(numero);
    }
}
