package com.library.services;

import com.library.entities.LigneApprovisionnement;

import java.util.List;
import java.util.Optional;

public interface LigneApprovisionnementService {

    List<LigneApprovisionnement> findAllLigneApprovisionnements();

    Optional<LigneApprovisionnement> findLigneApprovisionnementById(Long lApproId);

    LigneApprovisionnement saveLigneApprovisionnement(LigneApprovisionnement ligneApprovisionnement);

    LigneApprovisionnement updateLigneApprovisionnement(Long lApproId, LigneApprovisionnement ligneApprovisionnement);

    //  ResponseEntity<Object> deleteLigneApprovisionnement(Long lApproid);

    void deleteLApproByNumero(Long numero);

    List<LigneApprovisionnement> findAllLApproByNumero(Long numero);

    List<LigneApprovisionnement> findListLigneApprovisionnementByProduitId(Long prodId);

    List<LigneApprovisionnement> findListLigneApprovisionnementByApprovisionnementId(Long approId);


}
