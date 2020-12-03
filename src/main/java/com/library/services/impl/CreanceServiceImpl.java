package com.library.services.impl;

import com.library.entities.LigneCreance;
import com.library.entities.Produit;
import com.library.services.CreanceService;
import com.library.services.LigneCreanceService;
import com.library.services.ProduitService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.library.entities.Creance;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.CreanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@Transactional
public class CreanceServiceImpl implements CreanceService {

    Logger logger = LoggerFactory.getLogger(CreanceServiceImpl.class);


    @Autowired
    private CreanceRepository creanceRepository;

    @Autowired
    private LigneCreanceService ligneCreanceService;

    @Autowired
    private ProduitService produitService;

    @Override
    public List<Creance> findAllCreances() {
        return creanceRepository.findAll();
    }

    @Override
    public Optional<Creance> findCreanceById(Long creanceId) {
        if (!creanceRepository.existsById(creanceId)) {
            throw new ResourceNotFoundException("CommandeClient that id is" + creanceId + "not found");
        }

        return creanceRepository.findById(creanceId);
    }


    /**
     * @param creance
     * @return methode permettant d'ajouter d'abord une commande
     * puis les lignes de commandes correspondantes
     */

    @Override
    public Creance saveCreance(Creance creance) {

        logger.info("Creance {}", creance);
        creanceRepository.save(creance);


        List<LigneCreance> lcreances = creance.getLcreances();

        double total = 0;
        for (LigneCreance lcreance : lcreances) {
            lcreance.setCreance(creance);
            lcreance.setNumero(creance.getReference());

            ligneCreanceService.saveLigneCreance(lcreance);

            Produit produit = produitService.findProduitById(lcreance.getProduit().getId()).get();
            if (produit != null) {
                produit.setQtestock(produit.getQtestock() - lcreance.getQuantite());
                produitService.saveProduit(produit);
            }

            lcreance.setPrix(produit.getPrixVente());

            System.out.println(produit.getPrixVente());
            System.out.println(lcreance.getQuantite());
            System.out.println(lcreance.getQuantite() * produit.getPrixVente());

            total += (lcreance.getQuantite() * produit.getPrixVente());

        }

        creance.setTotalCreance(total);
        creance.setStatus("valider");
        // commande.setNumCommande("Cmd " + 15 + (int) (Math.random() * 100));
       // commande.setDateCommande(new Date());


        return creanceRepository.save(creance);

    }



    @Override
    public void deleteCreance(Long id) {
        Optional<Creance> creanceInfo = creanceRepository.findById(id);
        if (creanceInfo.isPresent()) {
            Creance creance = creanceInfo.get();
            ligneCreanceService.deleteLcreanceByNumero(creance.getReference());
            creanceRepository.delete(creance);
        }
    }


    @Override
    public Creance updateCreance(Long creanceId, Creance creance) {
        if (!creanceRepository.existsById(creanceId)) {
            throw new ResourceNotFoundException("Creance that id is" + creanceId + "not found");
        }

        Optional<Creance> creanceClient = creanceRepository.findById(creanceId);

        if (!creanceClient.isPresent()) {
            throw new ResourceNotFoundException("Commande that id is" + creanceId + "not found");
        }

        Creance creanceResult = creanceClient.get();

        creanceResult.setReference(creance.getReference());
        creanceResult.setLibelle(creance.getLibelle());
        creanceResult.setClient(creance.getClient());
        creanceResult.setSoldeCreance(creance.getSoldeCreance());
        creanceResult.setNbreJours(creance.getNbreJours());

        creanceResult.setTotalCreance(creance.getTotalCreance());
        creanceResult.setStatus(creance.getStatus());

        return creanceRepository.save(creanceResult);
    }


    @Override
    public int getNumberOfCreances() {
        return creanceRepository.countNumberOfCreance();
    }

    @Override
    public BigDecimal countNumbersOfCreances() {
        return creanceRepository.countNumbersOfCommandes();
    }

    @Override
    public Creance findByReference(int reference) {
        return creanceRepository.findByNumeroCreance(reference);
    }

    @Override
    public List<Creance> findListCreanceByReference(int reference) {
        return null;
    }

    @Override
    public Creance findByStatus(String status) {
        return creanceRepository.findByStatus(status);
    }


    @Override
    public List<Creance> findListCreanceByStatus(String status) {
        return creanceRepository.ListCreanceByStatus(status);
    }

    @Override
    public Creance findByLibelle(String libelle) {
        return null;
    }

    @Override
    public List<Creance> findListCreanceByLibelle(String libelle) {
        return null;
    }

    @Override
    public List<Creance> findCreanceByClientId(Long clientId) {
        return creanceRepository.ListCreanceClientByClientId(clientId);
    }



}
