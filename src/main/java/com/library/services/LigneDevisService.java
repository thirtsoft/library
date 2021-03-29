package com.library.services;

import com.library.entities.LigneDevis;

import java.util.List;
import java.util.Optional;

public interface LigneDevisService {

    List<LigneDevis> findAllLigneDevis();

    Optional<LigneDevis> findLigneDevisById(Long ldevId);

    LigneDevis saveLigneDevis(LigneDevis ligneDevis);

    LigneDevis updateLigneDevis(Long ldevId, LigneDevis ligneDevis);

    void deleteLigneDevis(Long id);

    //void deleteLigneDevisByNumero(long numero);
    void deleteLigneDevisByNumero(Long numero);

    List<LigneDevis> findLigneDevisByProduitId(Long prodId);

    List<LigneDevis> findLigneDevisByDevId(Long devId);

   // List<LigneDevis> findAllLigneDevisByNumero(long numero);
    List<LigneDevis> findAllLigneDevisByNumero(Long numero);


    List<LigneDevis> saveListLigneDevis(List<LigneDevis> ligneDevis);
}
