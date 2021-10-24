package com.library.services;

import com.library.entities.Charge;

import java.util.List;
import java.util.Optional;

public interface ChargeService {

    List<Charge> findAllCharges();

    Optional<Charge> findChargeById(Long id);

    Charge findChargeByCodeCharge(String codeCharge);

    List<Charge> findListChargeByCodeCharge(String codeCharge);

    Charge findChargeByNature(String nature);

    List<Charge> findListChargeByNature(String nature);

    List<?> sumTotalOfChargeByMonth();

    Charge saveCharge(Charge charge);

    Charge updateCharge(Long id, Charge charge);

    void deleteCharge(Long id);

}
