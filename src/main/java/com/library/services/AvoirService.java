package com.library.services;
import com.library.entities.Avoir;
import com.library.entities.Creance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;


public interface AvoirService {

    public List<Avoir> findAllAvoirs();
    public Optional<Avoir> findAvoirById(Long id);

    public Avoir findAvoirByReference(String reference);
    public List<Avoir> findListAvoirByReference(String reference);

    public Avoir findAvoirByLibelle(String libelle);
    public List<Avoir> findListAvoirByLibelle(String libelle);


    public List<Avoir> findListAvoirByFournisseurId(Long fourId);

    public Avoir saveAvoir(Avoir avoir);
    public Avoir updateAvoir(Long id, Avoir avoir);
    public ResponseEntity<Object> deleteAvoir(Long id);


    public Page<Avoir> findAllAvoirsByPageable(Pageable page);
    public Page<Avoir>findAllAvoirsByFournisseur(Long fourId, Pageable pageable);

    public Page<Avoir> findAvoirByKeyWord(String mc, Pageable pageable);
}
