package com.library.services;

import com.library.entities.Charge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ChargeService {

    List<Charge> findAllCharges();
    Optional<Charge> findChargeById(Long id);

    Charge findChargeByCodeCharge(String codeCharge);
    List<Charge> findListChargeByCodeCharge(String codeCharge);

    Charge findChargeByNature(String nature);
    List<Charge> findListChargeByNature(String nature);

    Charge saveCharge(Charge charge);
    Charge updateCharge(Long id, Charge charge);
    void deleteCharge(Long id);

    Page<Charge> findAllChargesByPageable(Pageable page);

    Page<Charge> findChargesByKeyWord(String mc, Pageable pageable);

}
