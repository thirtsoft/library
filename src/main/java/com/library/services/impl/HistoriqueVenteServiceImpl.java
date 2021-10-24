package com.library.services.impl;

import com.library.entities.HistoriqueVente;
import com.library.entities.Utilisateur;
import com.library.entities.Vente;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.HistoriqueVenteRepository;
import com.library.services.HistoriqueVenteService;
import com.library.services.UtilisateurService;
import com.library.services.VenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HistoriqueVenteServiceImpl implements HistoriqueVenteService {

    private final HistoriqueVenteRepository historiqueVenteRepository;

    private final UtilisateurService utilisateurService;

    private final VenteService venteService;

    @Autowired
    public HistoriqueVenteServiceImpl(HistoriqueVenteRepository historiqueVenteRepository,
                                      UtilisateurService utilisateurService, VenteService venteService) {
        this.historiqueVenteRepository = historiqueVenteRepository;
        this.utilisateurService = utilisateurService;
        this.venteService = venteService;
    }

    @Override
    public HistoriqueVente saveHistoriqueVente(HistoriqueVente historiqueVente) {
        return historiqueVenteRepository.save(historiqueVente);
    }

    @Override
    public HistoriqueVente saveHistoriqueVenteWithConnectedUserAndVenteID(HistoriqueVente historiqueVente,
                                                                          Long userId, Long venteId) {
        Optional<Utilisateur> optionalUtilisateur = utilisateurService.findUtilisateurById(userId);
        Utilisateur utilisateur = optionalUtilisateur.get();

        Optional<Vente> optionalVente = venteService.findVenteById(venteId);
        Vente vente = optionalVente.get();

        historiqueVente.setUtilisateur(utilisateur);
        historiqueVente.setVente(vente);
        historiqueVente.setCreatedDate(new Date());

        return historiqueVenteRepository.save(historiqueVente);
    }

    @Override
    public Optional<HistoriqueVente> findHistoriqueVenteById(Long id) {
        if (!historiqueVenteRepository.existsById(id)) {
            throw new ResourceNotFoundException("HistoriqueVente that id is" + id + "not found");

        }
        return historiqueVenteRepository.findById(id);
    }

    @Override
    public List<HistoriqueVente> findAllHistoriqueVentes() {
        return historiqueVenteRepository.findAll();
    }

    @Override
    public List<HistoriqueVente> findAllHistoriqueVentesOrderDesc() {
        return historiqueVenteRepository.findByOrderByIdDesc();
    }

    @Override
    public BigDecimal countNumberOfHistoriqueVentes() {
        return historiqueVenteRepository.countNumberOfHistoriqueVentes();
    }

    @Override
    public void deleteHistoriqueVente(Long id) {
        if (!historiqueVenteRepository.existsById(id)) {
            throw new ResourceNotFoundException("HistoriqueVente that id is" + id + "not found");

        }
        historiqueVenteRepository.deleteById(id);
    }
}
