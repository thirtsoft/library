package com.library.services;

import com.library.entities.Vente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface VenteService {

    public List<Vente> findAllVentes();
    public Optional<Vente> findVenteById(Long venteId);
    public Vente saveVente(Vente vente);

    public Vente updateVente(Long venteId, Vente vente);
    public ResponseEntity<Object> deleteVente(Long id);

    public Vente findVenteByNumeroVente(String numeroVente);

    public List<Vente> findListVenteByNumeroVente(String numeroVente);

    public Page<Vente> findAllVenteByPageable(Pageable pageable);

    public Page<Vente> findVenteByKeyWord(String mc, Pageable pageable);

}
