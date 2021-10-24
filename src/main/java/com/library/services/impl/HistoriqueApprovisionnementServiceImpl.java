package com.library.services.impl;

import com.library.entities.Approvisionnement;
import com.library.entities.HistoriqueApprovisionnement;
import com.library.entities.Utilisateur;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.HistoriqueApprovisionnementRepository;
import com.library.services.ApprovisionnementService;
import com.library.services.HistoriqueApprovisionnementService;
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
public class HistoriqueApprovisionnementServiceImpl implements HistoriqueApprovisionnementService {

    private final HistoriqueApprovisionnementRepository historiqueApprovisionnementRepository;

    private final UtilisateurService utilisateurService;

    private final ApprovisionnementService approvisionnementService;

    @Autowired
    public HistoriqueApprovisionnementServiceImpl(HistoriqueApprovisionnementRepository historiqueApprovisionnementRepository,
                                                  UtilisateurService utilisateurService,
                                                  ApprovisionnementService approvisionnementService) {
        this.historiqueApprovisionnementRepository = historiqueApprovisionnementRepository;
        this.utilisateurService = utilisateurService;
        this.approvisionnementService = approvisionnementService;
    }


    @Override
    public HistoriqueApprovisionnement saveHistoriqueApprovisionnement(HistoriqueApprovisionnement historiqueApprovisionnement) {
        return historiqueApprovisionnementRepository.save(historiqueApprovisionnement);
    }

    @Override
    public HistoriqueApprovisionnement saveHistoriqueApprovisionnementWithConnectedUserAndApproID(HistoriqueApprovisionnement historiqueApprovisionnement,
                                                                                                  Long userId, Long approId) {
        Optional<Utilisateur> optionalUtilisateur = utilisateurService.findUtilisateurById(userId);
        Utilisateur utilisateur = optionalUtilisateur.get();

        Optional<Approvisionnement> optionalApprovisionnement = approvisionnementService.findApprovisionnementById(approId);
        Approvisionnement approvisionnement = optionalApprovisionnement.get();

        historiqueApprovisionnement.setUtilisateur(utilisateur);
        historiqueApprovisionnement.setApprovisionnement(approvisionnement);
        historiqueApprovisionnement.setCreatedDate(new Date());

        return historiqueApprovisionnementRepository.save(historiqueApprovisionnement);
    }

    @Override
    public Optional<HistoriqueApprovisionnement> findHistoriqueApprovisionnementById(Long id) {
        if (!historiqueApprovisionnementRepository.existsById(id)) {
            throw new ResourceNotFoundException("HistoriqueApprovisionnement that id is" + id + "not found");
        }
        return historiqueApprovisionnementRepository.findById(id);
    }

    @Override
    public List<HistoriqueApprovisionnement> findAllHistoriqueApprovisionnements() {
        return historiqueApprovisionnementRepository.findAll();
    }

    @Override
    public List<HistoriqueApprovisionnement> findAllHistoriqueApprovisionnementsOrderDesc() {
        return historiqueApprovisionnementRepository.findByOrderByIdDesc();
    }

    @Override
    public BigDecimal countNumberOfHistoriqueApprovisionnements() {
        return historiqueApprovisionnementRepository.countNumberOfHistoriqueApprovisionnements();
    }

    @Override
    public void deleteHistoriqueApprovisionnement(Long id) {
        if (!historiqueApprovisionnementRepository.existsById(id)) {
            throw new ResourceNotFoundException("HistoriqueApprovisionnement is not found");
        }
        historiqueApprovisionnementRepository.deleteById(id);
    }
}
