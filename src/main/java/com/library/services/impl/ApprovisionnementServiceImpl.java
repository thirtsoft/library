package com.library.services.impl;

import com.library.entities.Approvisionnement;
import com.library.entities.LigneApprovisionnement;
import com.library.entities.Produit;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.ApprovisionnementRepository;
import com.library.services.ApprovisionnementService;
import com.library.services.LigneApprovisionnementService;
import com.library.services.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
            throw new ResourceNotFoundException("Approvisionnement N° " + approId + "not found");
        }

        return approvisionnementRepository.findById(approId);
    }

    @Override
    public Approvisionnement findApprovisionnementByCode(String code) {
        return approvisionnementRepository.findApprovisionnementByCode(code);
    }

    @Override
    public List<Approvisionnement> findListApprovisionnementByCode(String code) {
        return approvisionnementRepository.findListApprovisionnementByCode(code);
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


    @Override
    public Approvisionnement saveApprovisionnement(Approvisionnement approvisionnement) {
        approvisionnementRepository.save(approvisionnement);
        List<LigneApprovisionnement> LigneApprovisionnements = approvisionnement.getLigneApprovisionnements();
        double total = 0;
        for (LigneApprovisionnement lAppro: LigneApprovisionnements) {
            lAppro.setApprovisionnement(approvisionnement);
            Produit produit = produitService.findProduitById(lAppro.getProduit().getId()).get();
            lAppro.setProduit(produit);
            lAppro.setPrix(lAppro.getProduit().getPrixAchat());

            ligneApprovisionnementService.saveLigneApprovisionnement(lAppro);

            total += lAppro.getQuantite() * lAppro.getProduit().getPrixAchat();
        }

        approvisionnement.setTotalAppro(total);
        approvisionnement.setCode("Appro " + 20 + (int) (Math.random() * 100));
        approvisionnement.setStatus("complète");
        approvisionnement.setDateApprovisionnement(new Date());

        return approvisionnementRepository.save(approvisionnement);
    }

    @Override
    public Approvisionnement updateApprovisionnement(Long approId, Approvisionnement approvisionnement) {
        if (!approvisionnementRepository.existsById(approId)) {
            throw new ResourceNotFoundException("Approvisionnement N° " + approId + "not found");
        }
        Optional<Approvisionnement> Appro = approvisionnementRepository.findById(approId);
        if (!Appro.isPresent()) {
            throw new ResourceNotFoundException("Approvisionnement N ° " + approId + "not found");
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

}
