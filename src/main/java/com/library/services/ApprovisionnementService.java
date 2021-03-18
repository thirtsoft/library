package com.library.services;

import com.library.entities.Approvisionnement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ApprovisionnementService {

    List<Approvisionnement> findAllApprovisionnements();

    Optional<Approvisionnement> findApprovisionnementById(Long approId);

    Approvisionnement saveApprovisionnement(Approvisionnement approvisionnement);

    Approvisionnement updateApprovisionnement(Long approId, Approvisionnement approvisionnement);

    ResponseEntity<Object> deleteApprovisionnement(Long id);

    Approvisionnement findApprovisionnementByCode(long code);

    // List<Approvisionnement> findListApprovisionnementByCode(String code);
    List<Approvisionnement> findListApprovisionnementByFournisseurId(Long fourId);

    Page<Approvisionnement> findAllApprovisionnementByPageable(Pageable pageable);

    Page<Approvisionnement> findAllApprovisionnementByFournisseur(Long fourId, Pageable pageable);

    Page<Approvisionnement> findApprovisionnementByKeyWord(String mc, Pageable pageable);

    void deleteAppro(Long id);

    long generateCodeApprovisionnement();

}
