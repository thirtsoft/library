package com.library.services.impl;

import com.library.entities.Devis;
import com.library.entities.LigneDevis;
import com.library.entities.Produit;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.DevisRepository;
import com.library.services.DevisService;
import com.library.services.LigneDevisService;
import com.library.services.ProduitService;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DevisServiceImpl implements DevisService {

    Logger logger = LoggerFactory.getLogger(DevisServiceImpl.class);

    @Autowired
    private DevisRepository devisRepository;

    @Autowired
    private LigneDevisService ligneDevisService;

    @Autowired
    private ProduitService produitService;

    @Override
    public List<Devis> findAllDevis() {
        return devisRepository.findAll();
    }

    @Override
    public List<Devis> findAllDevissOrderDesc() {
        return devisRepository.findByOrderByIdDesc();
    }

    @Override
    public Optional<Devis> findDevisById(Long devId) {
        if (!devisRepository.existsById(devId)) {
            throw new ResourceNotFoundException("Devis that not found");
        }

        return devisRepository.findById(devId);
    }

    @Override
    public Devis saveDevis(Devis devis) {
        logger.info("Devis {}", devis);
        List<LigneDevis> ligneDevis = devis.getLdevis();
        if (ligneDevis == null || ligneDevis.size() == 0) {
            throw new IllegalArgumentException("Vous devez au moins commander un article");
        }
        if ((devis.getClient().getId() == null)) {
            throw new IllegalArgumentException("Vous devez selectionner un client");
        }

        for (LigneDevis ldevis : ligneDevis) {
            Produit produitInitial = produitService.findProduitById(ldevis.getProduit().getId()).get();
            if (ldevis.getQuantite() > produitInitial.getQtestock()) {
                throw new IllegalArgumentException("La Quantité de stock du article est insuffusante");
            }
        }

        devisRepository.save(devis);

        List<LigneDevis> lignedevisClients = devis.getLdevis();
        double total = 0;
        for (LigneDevis ldevisClt : lignedevisClients) {
            ldevisClt.setDevis(devis);
            ldevisClt.setNumero(devis.getNumeroDevis());
            ligneDevisService.saveLigneDevis(ldevisClt);

            Produit produit = produitService.findProduitById(ldevisClt.getProduit().getId()).get();

            ldevisClt.setPrix(produit.getPrixVente());

            System.out.println(produit.getPrixVente());
            System.out.println(ldevisClt.getQuantite());
            System.out.println(ldevisClt.getQuantite() * produit.getPrixVente());

            total += (ldevisClt.getQuantite() * ldevisClt.getPrixDevis());

        }

        devis.setTotalDevis(total);
        devis.setStatus("valider");
        devis.setDateDevis(new Date());

        return devisRepository.save(devis);

    }

    @Override
    public Devis updateDevis(Long devId, Devis devis) {
        if (!devisRepository.existsById(devId)) {
            throw new ResourceNotFoundException("Devis not found");
        }

        Optional<Devis> devisClient = devisRepository.findById(devId);

        if (!devisClient.isPresent()) {
            throw new ResourceNotFoundException("Devis not found");
        }

        Devis devisResult = devisClient.get();

        devisResult.setNumeroDevis(devis.getNumeroDevis());
        devisResult.setDateDevis(devis.getDateDevis());
        devisResult.setClient(devis.getClient());
        devisResult.setTotalDevis(devis.getTotalDevis());
        devisResult.setStatus(devis.getStatus());

        return devisRepository.save(devisResult);
    }

    @Override
    public void deleteDevis(Long id) {
        Optional<Devis> devisInfo = devisRepository.findById(id);
        if (devisInfo.isPresent()) {
            Devis devis = devisInfo.get();
            ligneDevisService.deleteLigneDevisByNumero(devis.getNumeroDevis());
            devisRepository.delete(devis);
        }

    }

    @Override
    public int getNombreDevis(Date d1, Date d2) {
        return devisRepository.countBetween(d1, d2);
    }

    @Override
    public int getNumberOfDevis() {
        return devisRepository.countNumberOfDevis();
    }

    @Override
    public BigDecimal countNumbersOfDevis() {
        return devisRepository.countNumbersOfDevis();
    }

    @Override
    public Devis findByNumeroDevis(Long numeroDevis) {
        return devisRepository.findByNumeroDevis(numeroDevis);
    }

    @Override
    public List<Devis> findDevisByClientId(Long clientId) {
        return devisRepository.ListDevisByClientId(clientId);
    }

    @Override
    public List<Devis> findDevisByDate(Date dateDevis) {
        return devisRepository.findAllByDateDevis(dateDevis);
    }

    @Override
    public List<?> countNumberTotalOfDevisByMonth() {
        return devisRepository.countNumberOfDevisByMonth();
    }

    @Override
    public List<?> sumTotalOfDevisByMonth() {
        return devisRepository.sumTotalOfDevisByMonth();
    }

    @Override
    public long generateNumeroDevis() {
        final String FORMAT = "yyyyMMddHHmmss";
        return Long.parseLong(DateTimeFormat.forPattern(FORMAT).print(LocalDateTime.now()));
    }
}
