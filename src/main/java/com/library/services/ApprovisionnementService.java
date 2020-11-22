package com.library.services;

import com.library.entities.Approvisionnement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ApprovisionnementService {

    public List<Approvisionnement> findAllApprovisionnements();
    public Optional<Approvisionnement> findApprovisionnementById(Long approId);
    public Approvisionnement saveApprovisionnement(Approvisionnement approvisionnement);

    public Approvisionnement updateApprovisionnement(Long approId, Approvisionnement approvisionnement);
    public ResponseEntity<Object> deleteApprovisionnement(Long id);

    Approvisionnement findApprovisionnementByCode(int code);

   // public List<Approvisionnement> findListApprovisionnementByCode(String code);
    public List<Approvisionnement> findListApprovisionnementByFournisseurId(Long fourId);

    public Page<Approvisionnement> findAllApprovisionnementByPageable(Pageable pageable);
    public Page<Approvisionnement> findAllApprovisionnementByFournisseur(Long fourId, Pageable pageable);

    public Page<Approvisionnement> findApprovisionnementByKeyWord(String mc, Pageable pageable);

    void deleteAppro(Long id);

}
