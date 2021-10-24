package com.library.services;

import com.library.entities.HistoriqueCharge;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface HistoriqueChargeService {

    HistoriqueCharge saveHistoriqueCharge(HistoriqueCharge historiqueCharge);

    HistoriqueCharge saveHistoriqueChargeWithConnectedUserAndChargeID(HistoriqueCharge historiqueCharge,
                                                                                                  Long userId, Long chargeId);

    Optional<HistoriqueCharge> findHistoriqueChargeById(Long id);

    List<HistoriqueCharge> findAllHistoriqueCharges();

    List<HistoriqueCharge> findAllHistoriqueChargesOrderDesc();

    BigDecimal countNumberOfHistoriqueCharges();

    void deleteHistoriqueCharge(Long id);

}
