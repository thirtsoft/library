package com.library.services.impl;

import com.library.entities.Approvisionnement;
import com.library.entities.LigneApprovisionnement;
import com.library.entities.Produit;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.ApprovisionnementRepository;
import com.library.services.ApprovisionnementService;
import com.library.services.LigneApprovisionnementService;
import com.library.services.ProduitService;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ApprovisionnementServiceImpl implements ApprovisionnementService {

    @Autowired
    private ApprovisionnementRepository approvisionnementRepository;

    @Autowired
    private LigneApprovisionnementService ligneApprovisionnementService;

    @Autowired
    private ProduitService produitService;


    @Override
    public List<Approvisionnement> findAllApprovisionnements() {
        return approvisionnementRepository.findAll();
    }

    @Override
    public List<Approvisionnement> findAllApprovisionnementsOrderDesc() {
        return approvisionnementRepository.findByOrderByIdDesc();
    }

    @Override
    public List<Approvisionnement> findListApprovisionnementOf3LatestMonth() {
        return approvisionnementRepository.findListApprovisionnementOf3LatestMonth();
    }

    @Override
    public List<Approvisionnement> findTop500OfApprovisionnementOrderByIdDesc() {
        return approvisionnementRepository.findTop500ByOrderByIdDesc();
    }

    @Override
    public Optional<Approvisionnement> findApprovisionnementById(Long approId) {
        if (!approvisionnementRepository.existsById(approId)) {
            throw new ResourceNotFoundException("Approvisionnement Not found");
        }

        return approvisionnementRepository.findById(approId);
    }

    @Override
    public Approvisionnement findApprovisionnementByCode(Long code) {
        return approvisionnementRepository.findByCode(code);
    }

    @Override
    public List<Approvisionnement> findListApprovisionnementByFournisseurId(Long fourId) {
        return approvisionnementRepository.findListApprovisionnementByFournisseurId(fourId);
    }

    public Approvisionnement saveApprovisionnement(Approvisionnement approvisionnement) {

        approvisionnementRepository.save(approvisionnement);

        List<LigneApprovisionnement> ligneApprovisionnements = approvisionnement.getLigneApprovisionnements();

        double total = 0;

        for (LigneApprovisionnement lAppro : ligneApprovisionnements) {
            lAppro.setApprovisionnement(approvisionnement);
            lAppro.setNumero(approvisionnement.getCode());

            ligneApprovisionnementService.saveLigneApprovisionnement(lAppro);

            Produit produit = produitService.findProduitById(lAppro.getProduit().getId()).get();
            if (produit != null) {
                produit.setQtestock(produit.getQtestock() + lAppro.getQuantite());
                produitService.updateProduit(produit.getId(), produit);
            }

            lAppro.setPrix(produit.getPrixAchat());

            System.out.println(produit.getPrixAchat());
            System.out.println(lAppro.getQuantite());
            System.out.println(lAppro.getQuantite() * produit.getPrixAchat());

            //   total += (lAppro.getQuantite() * produit.getPrixAchat());
            total += (lAppro.getQuantite() * lAppro.getPrixAppro());

        }

        approvisionnement.setTotalAppro(total);
        // commande.setNumCommande("Cmd " + 15 + (int) (Math.random() * 100));
        approvisionnement.setDateApprovisionnement(new Date());

        return approvisionnementRepository.save(approvisionnement);

    }

    @Override
    public Approvisionnement updateApprovisionnement(Long approId, Approvisionnement approvisionnement) {
        if (!approvisionnementRepository.existsById(approId)) {
            throw new ResourceNotFoundException("Approvisionnement Not found");
        }
        Optional<Approvisionnement> Appro = approvisionnementRepository.findById(approId);
        if (!Appro.isPresent()) {
            throw new ResourceNotFoundException("Approvisionnement Not found");
        }

        Approvisionnement ApproResult = Appro.get();
        ApproResult.setCode(approvisionnement.getCode());
        ApproResult.setStatus(approvisionnement.getStatus());
        ApproResult.setFournisseur(approvisionnement.getFournisseur());
        ApproResult.setDateApprovisionnement(approvisionnement.getDateApprovisionnement());

        return approvisionnementRepository.save(ApproResult);
    }

    @Override
    public void deleteAppro(Long id) {
        Optional<Approvisionnement> approInfo = approvisionnementRepository.findById(id);
        if (approInfo.isPresent()) {
            Approvisionnement approvisionnement = approInfo.get();
            ligneApprovisionnementService.deleteLApproByNumero(approvisionnement.getCode());
            approvisionnementRepository.delete(approvisionnement);
        }
    }

    @Override
    public long generateCodeApprovisionnement() {
        final String FORMAT = "yyyyMMddHHmmss";
        return Long.parseLong(DateTimeFormat.forPattern(FORMAT).print(LocalDateTime.now()));
    }

    @Override
    public Approvisionnement updateStatusAppro(String status, String id) {
        Optional<Approvisionnement> originalAppro = approvisionnementRepository.findById(Long.valueOf(id));
        Approvisionnement approvisionnement = originalAppro.get();
        approvisionnement.setStatus(status);
        return approvisionnementRepository.save(approvisionnement);

    }

    @Override
    public Approvisionnement updateMontantAvanceAppro(double montantAvance, String id) {
        Optional<Approvisionnement> originalAppro = approvisionnementRepository.findById(Long.valueOf(id));
        Approvisionnement approvisionnement = originalAppro.get();
        approvisionnement.setMontantAvance(montantAvance);
        return approvisionnementRepository.save(approvisionnement);

    }

}
