package com.library.services;

import com.library.entities.LigneApprovisionnement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface LigneApprovisionnementService {

    List<LigneApprovisionnement> findAllLigneApprovisionnements();

    Optional<LigneApprovisionnement> findLigneApprovisionnementById(Long lApproId);

    LigneApprovisionnement saveLigneApprovisionnement(LigneApprovisionnement ligneApprovisionnement);

    LigneApprovisionnement updateLigneApprovisionnement(Long lApproId, LigneApprovisionnement ligneApprovisionnement);

    ResponseEntity<Object> deleteLigneApprovisionnement(Long lApproid);

    void deleteLApproByNumero(long numero);

    List<LigneApprovisionnement> findAllLApproByNumero(long numero);

    List<LigneApprovisionnement> findListLigneApprovisionnementByProduitId(Long prodId);

    List<LigneApprovisionnement> findListLigneApprovisionnementByApprovisionnementId(Long approId);

    Page<LigneApprovisionnement> findAllLigneApprovisionnementByPageable(Pageable pageable);

    Page<LigneApprovisionnement> findAllLigneApprovisionnementByApproviosionnement(Long approId, Pageable pageable);

    Page<LigneApprovisionnement> findAllLigneApprovisionnementByProduit(Long prodId, Pageable pageable);

}
