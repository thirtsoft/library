package com.library.services.impl;

import com.library.entities.Avoir;
import com.library.entities.LigneAvoir;
import com.library.entities.Produit;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.AvoirRepository;
import com.library.services.AvoirService;
import com.library.services.LigneAvoirService;
import com.library.services.ProduitService;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AvoirServiceImpl implements AvoirService {

    Logger logger = LoggerFactory.getLogger(CommandeClientServiceImpl.class);


    @Autowired
    private AvoirRepository avoirRepository;

    @Autowired
    private LigneAvoirService ligneAvoirService;

    @Autowired
    private ProduitService produitService;

    @Override
    public List<Avoir> findAllAvoirs() {
        return avoirRepository.findAll();
    }

    @Override
    public Optional<Avoir> findAvoirById(Long comId) {
        if (!avoirRepository.existsById(comId)) {
            throw new ResourceNotFoundException("CommandeClient that id is" + comId + "not found");
        }

        return avoirRepository.findById(comId);
    }

    /**
     * @param avoir
     * @return methode permettant d'ajouter d'abord une commande
     * puis les lignes de commandes correspondantes
     */

    @Override
    public Avoir saveAvoir(Avoir avoir) {

        logger.info("Avoir {}", avoir);
        avoirRepository.save(avoir);

        List<LigneAvoir> ligneAvoirs = avoir.getLavoirs();

        double total = 0;
        for (LigneAvoir lavoir  : ligneAvoirs) {
            lavoir.setAvoir(avoir);
            lavoir.setNumero(avoir.getReference());

            ligneAvoirService.saveLigneAvoir(lavoir);

            Produit produit = produitService.findProduitById(lavoir.getProduit().getId()).get();
            /*
            if (produit != null) {
                produit.setQtestock(produit.getQtestock() + lavoir.getQuantite());
                produitService.saveProduit(produit);
            }
            */
            lavoir.setPrix(produit.getPrixVente());

            System.out.println(produit.getPrixVente());
            System.out.println(lavoir.getQuantite());
            System.out.println(lavoir.getQuantite() * produit.getPrixVente());

            total += (lavoir.getQuantite() * produit.getPrixVente());

        }

        avoir.setTotalAvoir(total);
        avoir.setStatus("valider");
        avoir.setDateAvoir(new Date());

        return avoirRepository.save(avoir);

    }

    @Override
    public void deleteAvoir(Long id) {
        Optional<Avoir> avoirInfo = avoirRepository.findById(id);
        if (avoirInfo.isPresent()) {
            Avoir avoir = avoirInfo.get();
            ligneAvoirService.deleteLavoirByNumero(avoir.getReference());
            avoirRepository.delete(avoir);
        }
    }

    @Override
    public long generateRefereceAvoir() {
        final String FORMAT = "yyyyMMddHHmmss";
        return Long.parseLong(DateTimeFormat.forPattern(FORMAT).print(LocalDateTime.now()));
    }


    @Override
    public Avoir updateAvoir(Long avoirId, Avoir avoir) {
        if (!avoirRepository.existsById(avoirId)) {
            throw new ResourceNotFoundException("Commande that id is" + avoirId + "not found");
        }

        Optional<Avoir> avoirInfo = avoirRepository.findById(avoirId);

        if (!avoirInfo.isPresent()) {
            throw new ResourceNotFoundException("Avoir that id is" + avoirInfo + "not found");
        }

        Avoir avoirResult = avoirInfo.get();

        avoirResult.setReference(avoir.getReference());
       // cmdClientResult.setDateCommande(commande.getDateCommande());
        avoirResult.setFournisseur(avoir.getFournisseur());
        avoirResult.setTotalAvoir(avoir.getTotalAvoir());
        avoirResult.setStatus(avoir.getStatus());

        return avoirRepository.save(avoirResult);
    }

    @Override
    public Avoir findByReference(long reference) {
        return avoirRepository.findByReference(reference);
    }


    @Override
    public Avoir findAvoirByReference(long reference) {
        return avoirRepository.findByReference(reference);
    }

    @Override
    public List<Avoir> findListAvoirByReference(long reference) {
        return avoirRepository.findListAvoirByReference(reference);
    }

    @Override
    public Avoir findAvoirByLibelle(String libelle) {
        return avoirRepository.findByLibelle(libelle);
    }

    @Override
    public List<Avoir> findListAvoirByLibelle(String libelle) {
        return avoirRepository.findListAvoirByLibelle(libelle);
    }

    @Override
    public Avoir findByStatus(String status) {
        return avoirRepository.findByStatus(status);
    }

    @Override
    public List<Avoir> findListAvoirByStatus(String status) {
        return avoirRepository.ListAvoirByStatus(status);
    }

    @Override
    public List<Avoir> findListAvoirByFournisseurId(Long fourId) {
        return avoirRepository.findLitAvoirByFournisseurId(fourId);
    }


}
