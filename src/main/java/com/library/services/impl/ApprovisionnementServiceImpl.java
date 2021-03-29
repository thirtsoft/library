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

    @Override
    public Page<Approvisionnement> findAllApprovisionnementByPageable(Pageable pageable) {
        return approvisionnementRepository.findAllApprovisionnementByPageable(pageable);
    }

    @Override
    public Page<Approvisionnement> findAllApprovisionnementByFournisseur(Long fourId, Pageable pageable) {
        return approvisionnementRepository.findApprovisionnementByFournisseurId(fourId, pageable);
    }

    @Override
    public Page<Approvisionnement> findApprovisionnementByKeyWord(String mc, Pageable pageable) {
        return approvisionnementRepository.findApprovisionnementByKeyWord(mc, pageable);
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
        approvisionnement.setStatus("valider");
        // commande.setNumCommande("Cmd " + 15 + (int) (Math.random() * 100));
        approvisionnement.setDateApprovisionnement(new Date());

        return approvisionnementRepository.save(approvisionnement);

    }

    /**
     * Commenté le 18/10/2020
     *
     * @param
     * @return
     * @Override public Approvisionnement saveApprovisionnement(Approvisionnement approvisionnement) {
     * approvisionnementRepository.save(approvisionnement);
     * List<LigneApprovisionnement> ligneApprovisionnements = approvisionnement.getLigneApprovisionnements();
     * double total = 0;
     * for (LigneApprovisionnement lAppro: ligneApprovisionnements) {
     * lAppro.setApprovisionnement(approvisionnement);
     * //Produit produit = produitService.findProduitById(lAppro.getProduit().getId()).get();
     * //lAppro.setProduit(produit);
     * lAppro.setPrix(lAppro.getProduit().getPrixAchat());
     * <p>
     * ligneApprovisionnementService.saveLigneApprovisionnement(lAppro);
     * <p>
     * total += lAppro.getQuantite() * lAppro.getProduit().getPrixAchat();
     * }
     * <p>
     * approvisionnement.setTotalAppro(total);
     * approvisionnement.setCode("Appro " + 20 + (int) (Math.random() * 100));
     * approvisionnement.setStatus("complète");
     * approvisionnement.setDateApprovisionnement(new Date());
     * <p>
     * return approvisionnementRepository.save(approvisionnement);
     * }
     */

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
    public ResponseEntity<Object> deleteApprovisionnement(Long approId) {
        if (!approvisionnementRepository.existsById(approId)) {
            throw new ResourceNotFoundException("Approvionnement N ° " + approId + "not found");
        }
        approvisionnementRepository.deleteById(approId);

        return ResponseEntity.ok().build();
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
