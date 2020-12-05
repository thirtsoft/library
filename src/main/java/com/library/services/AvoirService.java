package com.library.services;
import com.library.entities.Avoir;
import com.library.entities.Creance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;


public interface AvoirService {

    List<Avoir> findAllAvoirs();
    Optional<Avoir> findAvoirById(Long id);

    Avoir findAvoirByReference(int reference);
    List<Avoir> findListAvoirByReference(int reference);

    Avoir findAvoirByLibelle(String libelle);
    List<Avoir> findListAvoirByLibelle(String libelle);


    List<Avoir> findListAvoirByFournisseurId(Long fourId);

    Avoir saveAvoir(Avoir avoir);
    Avoir updateAvoir(Long id, Avoir avoir);

    Avoir findByReference(int reference);
    Avoir findByStatus(String status);

    List<Avoir> findListAvoirByStatus(String status);

    void deleteAvoir(Long id);

}
