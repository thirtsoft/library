package com.library.services.impl;

import com.library.entities.Charge;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.ChargeRepository;
import com.library.services.ChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ChargeServiceImpl implements ChargeService {

    @Autowired
    private ChargeRepository chargeRepository;

    @Override
    public List<Charge> findAllCharges() {
        return chargeRepository.findAll();
    }

    @Override
    public Optional<Charge> findChargeById(Long id) {
        if (!chargeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Charge N° " + id + "not found");
        }
        return chargeRepository.findById(id);
    }

    @Override
    public Charge findChargeByCodeCharge(String codeCharge) {
        return chargeRepository.findByCodeCharge(codeCharge);
    }

    @Override
    public List<Charge> findListChargeByCodeCharge(String codeCharge) {
        return chargeRepository.findListCodeCharge(codeCharge);
    }

    @Override
    public Charge findChargeByNature(String nature) {
        return chargeRepository.findByNature(nature);
    }

    @Override
    public List<Charge> findListChargeByNature(String nature) {
        return chargeRepository.findListCodeCharge(nature);
    }

    @Override
    public Charge saveCharge(Charge charge) {
        return chargeRepository.save(charge);
    }

    @Override
    public Charge updateCharge(Long id, Charge charge) {
        if (!chargeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Charge N° " + id + "not found");
        }
        Optional <Charge> charg = chargeRepository.findById(id);
        if (charg.isPresent()) {
            throw new ResourceNotFoundException("Charge N° " + id + "not found");
        }

        Charge chargeResult = charg.get();
        chargeResult.setCodeCharge(charge.getCodeCharge());
        chargeResult.setNature(charge.getNature());
        chargeResult.setMontantCharge(charge.getMontantCharge());
        chargeResult.setDate(charge.getDate());

        return chargeRepository.save(chargeResult);

    }

    @Override
    public ResponseEntity<Object> deleteCharge(Long id) {
        if (!chargeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Charge N° " + id + "not found");
        }

        chargeRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @Override
    public Page<Charge> findAllChargesByPageable(Pageable page) {
        return chargeRepository.findAllChargesByPageable(page);
    }

    @Override
    public Page<Charge> findChargesByKeyWord(String mc, Pageable pageable) {
        return chargeRepository.findChargesByKeyWord(mc, pageable);
    }
}
