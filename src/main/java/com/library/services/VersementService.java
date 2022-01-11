package com.library.services;

import com.library.entities.Versement;

import java.util.List;
import java.util.Optional;


public interface VersementService {

    List<Versement> findAllVersements();

    List<Versement> findAllVersementsOrderDesc();

    Optional<Versement> findVersementById(Long id);

    Versement findByNumVersement(String numVersement);

    Versement findByNumeroRecu(String numeroRecu);

    List<Versement> findListVersementByNumVersement(String numVersement);

    List<Versement> findVersementByEmployeId(Long empId);

    Versement saveVersement(Versement versement);

    Versement updateVersement(Long id, Versement versement);

    void deleteVersement(Long id);


}
