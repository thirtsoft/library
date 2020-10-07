package com.library.services;

import com.library.entities.Charge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ChargeService {

    public List<Charge> findAllCharges();
    public Optional<Charge> findChargeById(Long id);

    public Charge findChargeByCodeCharge(String codeCharge);
    public List<Charge> findListChargeByCodeCharge(String codeCharge);

    public Charge findChargeByNature(String nature);
    public List<Charge> findListChargeByNature(String nature);

    public Charge saveCharge(Charge charge);
    public Charge updateCharge(Long id, Charge charge);
    public ResponseEntity<Object> deleteCharge(Long id);

    public Page<Charge> findAllChargesByPageable(Pageable page);

    public Page<Charge> findChargesByKeyWord(String mc, Pageable pageable);

}
