package com.library.services.impl;

import com.library.entities.Avoir;
import com.library.entities.HistoriqueAvoir;
import com.library.entities.Utilisateur;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.HistoriqueAvoirRepository;
import com.library.services.AvoirService;
import com.library.services.HistoriqueAvoirService;
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
public class HistoriqueAvoirServiceImpl implements HistoriqueAvoirService {

    private final HistoriqueAvoirRepository historiqueAvoirRepository;

    private final UtilisateurService utilisateurService;

    private final AvoirService avoirService;

    @Autowired
    public HistoriqueAvoirServiceImpl(HistoriqueAvoirRepository historiqueAvoirRepository,
                                      UtilisateurService utilisateurService,
                                      AvoirService avoirService) {
        this.historiqueAvoirRepository = historiqueAvoirRepository;
        this.utilisateurService = utilisateurService;
        this.avoirService = avoirService;
    }


    @Override
    public HistoriqueAvoir saveHistoriqueAvoir(HistoriqueAvoir historiqueAvoir) {
        return historiqueAvoirRepository.save(historiqueAvoir);
    }

    @Override
    public HistoriqueAvoir saveHistoriqueAvoirWithConnectedUserAndAvoirID(HistoriqueAvoir historiqueAvoir, Long userId, Long avoirId) {

        Optional<Utilisateur> optionalUtilisateur = utilisateurService.findUtilisateurById(userId);
        Utilisateur utilisateur = optionalUtilisateur.get();

        Optional<Avoir> optionalAvoir = avoirService.findAvoirById(avoirId);
        Avoir avoir = optionalAvoir.get();

        historiqueAvoir.setUtilisateur(utilisateur);
        historiqueAvoir.setAvoir(avoir);
        historiqueAvoir.setCreatedDate(new Date());

        return historiqueAvoirRepository.save(historiqueAvoir);
    }

    @Override
    public Optional<HistoriqueAvoir> findHistoriqueAvoirById(Long id) {
        if (!historiqueAvoirRepository.existsById(id)) {
            throw new ResourceNotFoundException("HistoriqueAvoir that id is" + id + "not found");
        }
        return historiqueAvoirRepository.findById(id);
    }

    @Override
    public List<HistoriqueAvoir> findAllHistoriqueAvoirs() {
        return historiqueAvoirRepository.findAll();
    }

    @Override
    public List<HistoriqueAvoir> findAllHistoriqueAvoirsOrderDesc() {
        return historiqueAvoirRepository.findByOrderByIdDesc();
    }

    @Override
    public BigDecimal countNumberOfHistoriqueAvoirs() {
        return historiqueAvoirRepository.countNumberOfHistoriqueAvoirs();
    }

    @Override
    public void deleteHistoriqueAvoir(Long id) {
        if (!historiqueAvoirRepository.existsById(id)) {
            throw new ResourceNotFoundException("HistoriqueAvoir that id is" + id + "not found");
        }
        historiqueAvoirRepository.deleteById(id);
    }
}
