package com.library.services.impl;

import com.library.entities.Fournisseur;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.FournisseurRepository;
import com.library.services.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FournisseurServiceImpl implements FournisseurService {

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Override
    public List<Fournisseur> findAllFournisseurs() {
        return fournisseurRepository.findAll();
    }

    @Override
    public Optional<Fournisseur> findProduitById(Long id) {

        if (!fournisseurRepository.existsById(id)) {
            throw new ResourceNotFoundException("Fournisseur that id is" + id + "not found");
        }
        return fournisseurRepository.findById(id);
    }

    @Override
    public Fournisseur saveFournisseur(Fournisseur fournisseur) {
        if (fournisseurRepository.findByEmail(fournisseur.getEmail()) != null) {
            throw new IllegalArgumentException("Ce Founisseur existe");
        }
        return fournisseurRepository.save(fournisseur);
    }

    @Override
    public Fournisseur updateFournisseurt(Long id, Fournisseur fournisseur) {
        if (!fournisseurRepository.existsById(id)) {
            throw new ResourceNotFoundException("Fournisseur not found");
        }
        Optional<Fournisseur> four = fournisseurRepository.findById(id);
        if (!four.isPresent()) {
            throw new ResourceNotFoundException("Fournisseur not found");
        }

        Fournisseur fournisseurResult = four.get();
        fournisseurResult.setCode(fournisseur.getCode());
        fournisseurResult.setRaisonSociale(fournisseur.getRaisonSociale());
        fournisseurResult.setPrenom(fournisseur.getPrenom());
        fournisseurResult.setNom(fournisseur.getNom());
        fournisseurResult.setNomBank(fournisseur.getNomBank());
        fournisseurResult.setNumeroCompte(fournisseur.getNumeroCompte());
        fournisseurResult.setAdresse(fournisseur.getAdresse());
        fournisseurResult.setTelephone(fournisseur.getTelephone());
        fournisseurResult.setFax(fournisseur.getFax());
        fournisseurResult.setEmail(fournisseur.getEmail());

        return fournisseurRepository.save(fournisseurResult);
    }

    @Override
    public Fournisseur findByCode(String code) {
        return fournisseurRepository.findByCode(code);
    }

    @Override
    public Fournisseur findByNom(String nom) {
        return fournisseurRepository.findByNom(nom);
    }

    @Override
    public Fournisseur findByRaisonSociale(String raisonSociale) {
        return fournisseurRepository.findByRaisonSociale(raisonSociale);
    }

    @Override
    public Fournisseur findByNomBank(String nomBank) {
        return fournisseurRepository.findByNomBank(nomBank);
    }

    @Override
    public Fournisseur findByEmail(String email) {
        return fournisseurRepository.findByEmail(email);
    }

    @Override
    public Integer countNumberOfFournisseurs() {
        return fournisseurRepository.countNumberOfFournisseurs();
    }

    @Override
    public List<Fournisseur> findListFournisseurByCode(String code) {
        return fournisseurRepository.ListFournisseurByCode(code);
    }

    @Override
    public List<Fournisseur> findListFournisseurByNom(String nom) {
        return fournisseurRepository.ListFournisseurByNom(nom);
    }

    @Override
    public List<Fournisseur> findListFournisseurByRaisonSociale(String raisonSociale) {
        return fournisseurRepository.ListFournisseurByNom(raisonSociale);
    }

    @Override
    public void deleteFournisseur(Long id) {
        if (!fournisseurRepository.existsById(id)) {
            throw new ResourceNotFoundException("Fournisseur is not found");
        }
        fournisseurRepository.deleteById(id);

    }


}
