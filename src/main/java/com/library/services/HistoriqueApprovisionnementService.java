package com.library.services;

import com.library.entities.HistoriqueApprovisionnement;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface HistoriqueApprovisionnementService {

    HistoriqueApprovisionnement saveHistoriqueApprovisionnement(HistoriqueApprovisionnement historiqueApprovisionnement);

    HistoriqueApprovisionnement saveHistoriqueApprovisionnementWithConnectedUserAndApproID(HistoriqueApprovisionnement historiqueApprovisionnement,
                                                                                           Long userId, Long approId);

    Optional<HistoriqueApprovisionnement> findHistoriqueApprovisionnementById(Long id);

    List<HistoriqueApprovisionnement> findAllHistoriqueApprovisionnements();

    List<HistoriqueApprovisionnement> findAllHistoriqueApprovisionnementsOrderDesc();

    BigDecimal countNumberOfHistoriqueApprovisionnements();

    void deleteHistoriqueApprovisionnement(Long id);

}
