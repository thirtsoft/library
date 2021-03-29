package com.library.services;

import com.library.entities.LigneVente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface LigneVenteService {

    List<LigneVente> findAllLigneVentes();

    Optional<LigneVente> findLigneVenteById(Long lventeId);

    LigneVente saveLigneVente(LigneVente ligneVente);

    LigneVente updateLigneVente(Long lventeId, LigneVente ligneVente);

    ResponseEntity<Object> deleteLigneVente(Long ligneVenteId);

   // void deleteLventeByNumero(long numero);
    void deleteLventeByNumero(Long numero);

   // List<LigneVente> findAllLventeByNumero(long numero);
    List<LigneVente> findAllLventeByNumero(Long numero);

    List<LigneVente> findLigneVenteByProduitId(Long prodId);

    List<LigneVente> findLigneVenteByVenteId(Long venteId);

    Page<LigneVente> findAllLigneVenteByPageable(Pageable pageable);

    Page<LigneVente> findAllLigneVenteByVente(Long venteId, Pageable pageable);

    Page<LigneVente> findAllLigneVenteByProduit(Long prodId, Pageable pageable);

}
