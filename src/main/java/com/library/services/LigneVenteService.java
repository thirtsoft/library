package com.library.services;

import com.library.entities.LigneVente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface LigneVenteService {

    public List<LigneVente> findAllLigneVentes();
    public Optional<LigneVente> findLigneVenteById(Long lventeId);
    public LigneVente saveLigneVente(LigneVente ligneVente);
    public LigneVente updateLigneVente(Long lventeId, LigneVente ligneVente);
    public ResponseEntity<Object> deleteLigneVente(Long ligneVenteId);

    public List<LigneVente> findLigneVenteByProduitId(Long prodId);
    public List<LigneVente> findLigneVenteByVenteId(Long venteId);

    public Page<LigneVente> findAllLigneVenteByPageable(Pageable pageable);
    public Page<LigneVente> findAllLigneVenteByVente(Long venteId, Pageable pageable);
    public Page<LigneVente> findAllLigneVenteByProduit(Long prodId, Pageable pageable);

}
