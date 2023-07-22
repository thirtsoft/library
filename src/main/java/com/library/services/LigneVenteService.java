package com.library.services;

import com.library.entities.LigneVente;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface LigneVenteService {

    List<LigneVente> findAllLigneVentes();

    List<LigneVente> findAllLigneVentesOrderDesc();

    Optional<LigneVente> findLigneVenteById(Long lventeId);

    Optional<LigneVente> findLigneVenteByCode(String code);

    LigneVente saveLigneVente(LigneVente ligneVente);

    LigneVente updateLigneVente(Long lventeId, LigneVente ligneVente);

    ResponseEntity<Object> deleteLigneVente(Long ligneVenteId);

    // void deleteLventeByNumero(long numero);
    void deleteLventeByNumero(Long numero);

    List<LigneVente> findAllLventeByNumero(Long numero);

    List<LigneVente> findAllLventeByCodeCode(String code);

    List<LigneVente> findLigneVenteByProduitId(Long prodId);

    List<LigneVente> findLigneVenteByVenteId(Long venteId);

    List<LigneVente> findTop100ByOrderByIdDesc();
}
