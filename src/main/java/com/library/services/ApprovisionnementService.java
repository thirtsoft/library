package com.library.services;

import com.library.entities.Approvisionnement;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ApprovisionnementService {

    List<Approvisionnement> findAllApprovisionnements();

    List<Approvisionnement> findAllApprovisionnementsOrderDesc();

    Optional<Approvisionnement> findApprovisionnementById(Long approId);

    Approvisionnement saveApprovisionnement(Approvisionnement approvisionnement);

    Approvisionnement updateApprovisionnement(Long approId, Approvisionnement approvisionnement);

    Approvisionnement findApprovisionnementByCode(Long code);

    List<Approvisionnement> findListApprovisionnementByFournisseurId(Long fourId);

    void deleteAppro(Long id);

    long generateCodeApprovisionnement();

    Approvisionnement updateStatusAppro(String status, String id);

    Approvisionnement updateMontantAvanceAppro(double montantAvance, String id);

}
