package com.library.services.impl;

import com.library.services.CreanceService;
import org.springframework.stereotype.Service;
import com.library.entities.Creance;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.CreanceRepository;
import com.library.services.CreanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
@Transactional
public class CreanceServiceImpl implements CreanceService {

    @Autowired
    private CreanceRepository creanceRepository;

    @Override
    public List<Creance> findAllCreances() {
        return creanceRepository.findAll();
    }

    @Override
    public Optional<Creance> findCreanceById(Long id) {
        if (!creanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Creance N° " + id + "not found");
        }

        return creanceRepository.findById(id);
    }

    @Override
    public Creance findByReference(String reference) {
        return creanceRepository.findByReference(reference);
    }

    @Override
    public List<Creance> findListCreanceByReference(String reference) {
        return creanceRepository.findListContratByReference(reference);
    }

    @Override
    public Creance findByLibelle(String libelle) {
        return creanceRepository.findByLibelle(libelle);
    }

    @Override
    public List<Creance> findListCreanceByLibelle(String libelle) {
        return creanceRepository.findListContratByLibelle(libelle);
    }

    @Override
    public List<Creance> findCreanceByClientId(Long clientId) {
        return creanceRepository.findLitCreanceByClientId(clientId);
    }

    @Override
    public Creance saveCreance(Creance creance) {
        return creanceRepository.save(creance);
    }

    @Override
    public Creance updateCreance(Long id, Creance creance) {
        if (!creanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Creance N° " + id + "not found");
        }

        Optional<Creance> creanceOptional = creanceRepository.findById(id);
        if (!creanceOptional.isPresent()) {
            throw new ResourceNotFoundException("Creance N° " + id + "not found");
        }
        Creance creanceResultat = creanceOptional.get();
        creanceResultat.setReference(creance.getReference());
        creanceResultat.setLibelle(creance.getLibelle());
        creanceResultat.setSoldeCreance(creance.getSoldeCreance());
        creanceResultat.setNbreJours(creance.getNbreJours());

        return creanceRepository.save(creanceResultat);
    }

    @Override
    public ResponseEntity<Object> deleteCreance(Long id) {
        if (!creanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Creance N° " + id + "not found");
        }
        creanceRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @Override
    public Page<Creance> findAllCreancesByPageable(Pageable page) {
        return creanceRepository.findAllCreancesByPageable(page);
    }

    @Override
    public Page<Creance> findAllCreancesByClient(Long clientId, Pageable pageable) {
        return creanceRepository.findCreanceByClientPageable(clientId, pageable);
    }

    @Override
    public Page<Creance> findCreanceByKeyWord(String mc, Pageable pageable) {
        return creanceRepository.findCreancesByKeyWord(mc, pageable);
    }

}
