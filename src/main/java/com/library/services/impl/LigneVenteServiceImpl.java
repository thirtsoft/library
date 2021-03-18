package com.library.services.impl;

import com.library.entities.LigneVente;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.LigneVenteRepository;
import com.library.services.LigneVenteService;
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
public class LigneVenteServiceImpl implements LigneVenteService {

    @Autowired
    private LigneVenteRepository ligneVenteRepository;

    @Override
    public List<LigneVente> findAllLigneVentes() {
        return ligneVenteRepository.findAll();
    }

    @Override
    public Optional<LigneVente> findLigneVenteById(Long lventeId) {
        if (!ligneVenteRepository.existsById(lventeId)) {
            throw new ResourceNotFoundException("Ligne Vente Not found");
        }
        return ligneVenteRepository.findById(lventeId);
    }

    @Override
    public LigneVente saveLigneVente(LigneVente ligneVente) {
        return ligneVenteRepository.save(ligneVente);
    }

    @Override
    public LigneVente updateLigneVente(Long lventeId, LigneVente ligneVente) {
        if (!ligneVenteRepository.existsById(lventeId)) {
            throw new ResourceNotFoundException("Ligne Vente Not found");
        }
        Optional<LigneVente> lignevente = ligneVenteRepository.findById(lventeId);
        if (!lignevente.isPresent()) {
            throw new ResourceNotFoundException("Ligne Vente Not found");
        }

        LigneVente ligneVenteResultat = lignevente.get();

        ligneVenteResultat.setNumero(ligneVente.getNumero());
        ligneVenteResultat.setPrixVente(ligneVente.getProduit().getPrixDetail());
        ligneVenteResultat.setQuantite(ligneVente.getQuantite());
        ligneVenteResultat.setProduit(ligneVente.getProduit());
        ligneVenteResultat.setVente(ligneVente.getVente());

        return ligneVenteRepository.save(ligneVenteResultat);
    }

    @Override
    public ResponseEntity<Object> deleteLigneVente(Long ligneVenteId) {
        if (!ligneVenteRepository.existsById(ligneVenteId)) {
            throw new ResourceNotFoundException("Linge Vente Not found");
        }
        ligneVenteRepository.deleteById(ligneVenteId);

        return ResponseEntity.ok().build();
    }

    @Override
    public void deleteLventeByNumero(long numero) {
        ligneVenteRepository.deleteByNumero(numero);
    }

    @Override
    public List<LigneVente> findAllLventeByNumero(long numero) {
        return ligneVenteRepository.findAllByNumero(numero);
    }

    @Override
    public List<LigneVente> findLigneVenteByProduitId(Long prodId) {
        return null ;
    }

    @Override
    public List<LigneVente> findLigneVenteByVenteId(Long venteId) {
        return ligneVenteRepository.ListLigneVenteByVenteId(venteId);
    }

    @Override
    public Page<LigneVente> findAllLigneVenteByPageable(Pageable pageable) {
        return ligneVenteRepository.findAllLigneVenteByPageable(pageable);
    }

    @Override
    public Page<LigneVente> findAllLigneVenteByVente(Long venteId, Pageable pageable) {
        return ligneVenteRepository.findLigneVenteByVentePageable(venteId, pageable);
    }

    @Override
    public Page<LigneVente> findAllLigneVenteByProduit(Long prodId, Pageable pageable) {
        return ligneVenteRepository.findLigneVenteByProduitPageable(prodId, pageable);
    }
}
