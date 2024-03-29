package com.library.services;

import com.library.entities.Avoir;

import java.util.List;
import java.util.Optional;


public interface AvoirService {

    List<Avoir> findAllAvoirs();

    List<Avoir> findAllAvoirsOrderDesc();

    Optional<Avoir> findAvoirById(Long id);

    Avoir findAvoirByReference(Long reference);

    List<Avoir> findListAvoirByReference(Long reference);

    Avoir findAvoirByLibelle(String libelle);

    List<Avoir> findListAvoirByLibelle(String libelle);


    List<Avoir> findListAvoirByFournisseurId(Long fourId);

    Avoir saveAvoir(Avoir avoir);

    Avoir updateAvoir(Long id, Avoir avoir);

    Avoir findByReference(Long reference);

    Avoir findByStatus(String status);

    List<Avoir> findListAvoirByStatus(String status);

    void deleteAvoir(Long id);

    long generateReferenceAvoir();

}
