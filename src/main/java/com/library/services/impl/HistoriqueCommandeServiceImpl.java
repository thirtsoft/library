package com.library.services.impl;

import com.library.entities.CommandeClient;
import com.library.entities.HistoriqueCommande;
import com.library.entities.Utilisateur;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.HistoriqueCommandeRepository;
import com.library.services.CommandeClientService;
import com.library.services.HistoriqueCommandeService;
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
public class HistoriqueCommandeServiceImpl implements HistoriqueCommandeService {

    private final HistoriqueCommandeRepository historiqueCommandeRepository;

    private final UtilisateurService utilisateurService;

    private final CommandeClientService commandeClientService;

    @Autowired
    public HistoriqueCommandeServiceImpl(HistoriqueCommandeRepository historiqueCommandeRepository,
                                         UtilisateurService utilisateurService,
                                         CommandeClientService commandeClientService) {
        this.historiqueCommandeRepository = historiqueCommandeRepository;
        this.utilisateurService = utilisateurService;
        this.commandeClientService = commandeClientService;
    }

    @Override
    public HistoriqueCommande saveHistoriqueCommande(HistoriqueCommande historiqueCommande) {
        return historiqueCommandeRepository.save(historiqueCommande);
    }

    @Override
    public HistoriqueCommande saveHistoriqueCommandeWithConnectedUserAndComID(HistoriqueCommande historiqueCommande,
                                                                              Long userId, Long comId) {
        Optional<Utilisateur> optionalUtilisateur = utilisateurService.findUtilisateurById(userId);
        Utilisateur utilisateur = optionalUtilisateur.get();

        Optional<CommandeClient> optionalCommandeClient = commandeClientService.findCommandeClientById(comId);
        CommandeClient commandeClient = optionalCommandeClient.get();

        historiqueCommande.setUtilisateur(utilisateur);
        historiqueCommande.setCommandeClient(commandeClient);
        historiqueCommande.setCreatedDate(new Date());

        return historiqueCommandeRepository.save(historiqueCommande);
    }

    @Override
    public Optional<HistoriqueCommande> findHistoriqueCommandeById(Long id) {
        if (!historiqueCommandeRepository.existsById(id)) {
            throw new ResourceNotFoundException("HistoriqueCommande that id is" + id + "not found");
        }
        return historiqueCommandeRepository.findById(id);
    }

    @Override
    public List<HistoriqueCommande> findAllHistoriqueCommandes() {
        return historiqueCommandeRepository.findAll();
    }

    @Override
    public List<HistoriqueCommande> findAllHistoriqueCommandesOrderDesc() {
        return historiqueCommandeRepository.findByOrderByIdDesc();
    }

    @Override
    public BigDecimal countNumberOfHistoriqueCommandes() {
        return historiqueCommandeRepository.countNumberOfHistoriqueCommandes();
    }

    @Override
    public void deleteHistoriqueCommande(Long id) {
        if (!historiqueCommandeRepository.existsById(id)) {
            throw new ResourceNotFoundException("HistoriqueCommande that id is" + id + "not found");
        }
        historiqueCommandeRepository.deleteById(id);
    }
}
