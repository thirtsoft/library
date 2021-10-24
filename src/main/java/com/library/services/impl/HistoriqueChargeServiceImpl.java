package com.library.services.impl;

import com.library.entities.Charge;
import com.library.entities.HistoriqueCharge;
import com.library.entities.Utilisateur;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.HistoriqueChargeRepository;
import com.library.services.ChargeService;
import com.library.services.HistoriqueChargeService;
import com.library.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HistoriqueChargeServiceImpl implements HistoriqueChargeService {

    private final HistoriqueChargeRepository historiqueChargeRepository;

    private final UtilisateurService utilisateurService;

    private final ChargeService chargeService;

    @Autowired
    public HistoriqueChargeServiceImpl(HistoriqueChargeRepository historiqueChargeRepository,
                                       UtilisateurService utilisateurService,
                                       ChargeService chargeService) {
        this.historiqueChargeRepository = historiqueChargeRepository;
        this.utilisateurService = utilisateurService;
        this.chargeService = chargeService;
    }


    @Override
    public HistoriqueCharge saveHistoriqueCharge(HistoriqueCharge historiqueCharge) {
        return historiqueChargeRepository.save(historiqueCharge);
    }

    @Override
    public HistoriqueCharge saveHistoriqueChargeWithConnectedUserAndChargeID(HistoriqueCharge historiqueCharge,
                                                                             Long userId, Long chargeId) {
        Optional<Utilisateur> optionalUtilisateur = utilisateurService.findUtilisateurById(userId);
        Utilisateur utilisateur = optionalUtilisateur.get();

        Optional<Charge> optionalCharge = chargeService.findChargeById(chargeId);
        Charge charge = optionalCharge.get();

        historiqueCharge.setUtilisateur(utilisateur);
        historiqueCharge.setCharge(charge);
        historiqueCharge.setCreatedDate(new Date());

        return historiqueChargeRepository.save(historiqueCharge);
    }

    @Override
    public Optional<HistoriqueCharge> findHistoriqueChargeById(Long id) {
        if (!historiqueChargeRepository.existsById(id)) {
            throw new ResourceNotFoundException("HistoriqueCharge that id is" + id + "not found");
        }
        return historiqueChargeRepository.findById(id);
    }

    @Override
    public List<HistoriqueCharge> findAllHistoriqueCharges() {
        return historiqueChargeRepository.findAll();
    }

    @Override
    public List<HistoriqueCharge> findAllHistoriqueChargesOrderDesc() {
        return historiqueChargeRepository.findByOrderByIdDesc();
    }

    @Override
    public BigDecimal countNumberOfHistoriqueCharges() {
        return historiqueChargeRepository.countNumberOfHistoriqueCharges();
    }

    @Override
    public void deleteHistoriqueCharge(Long id) {
        if (!historiqueChargeRepository.existsById(id)) {
            throw new ResourceNotFoundException("HistoriqueCharge that id is" + id + "not found");
        }
        historiqueChargeRepository.deleteById(id);
    }
}
