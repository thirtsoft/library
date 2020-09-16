package com.library.services;

import com.library.entities.LigneApprovisionnement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface LigneApprovisionnementService {

    public List<LigneApprovisionnement> findAllLigneApprovisionnements();
    public Optional<LigneApprovisionnement> findLigneApprovisionnementById(Long lApproId);
    public LigneApprovisionnement saveLigneApprovisionnement(LigneApprovisionnement ligneApprovisionnement);
    public LigneApprovisionnement updateLigneApprovisionnement(Long lApproId, LigneApprovisionnement ligneApprovisionnement);
    public ResponseEntity<Object> deleteLigneApprovisionnement(Long lApproid);

    public List<LigneApprovisionnement> findListLigneApprovisionnementByProduitId(Long prodId);
    public List<LigneApprovisionnement> findListLigneApprovisionnementByApprovisionnementId(Long approId);

    public Page<LigneApprovisionnement> findAllLigneApprovisionnementByPageable(Pageable pageable);
    public Page<LigneApprovisionnement> findAllLigneApprovisionnementByApproviosionnement(Long approId, Pageable pageable);
    public Page<LigneApprovisionnement> findAllLigneApprovisionnementByProduit(Long prodId, Pageable pageable);

}
