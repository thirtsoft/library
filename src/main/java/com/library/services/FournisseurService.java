package com.library.services;

import com.library.entities.Fournisseur;

import java.util.List;
import java.util.Optional;


public interface FournisseurService {

    Optional<Fournisseur> findProduitById(Long id);

    Fournisseur saveFournisseur(Fournisseur fournisseur);

    Fournisseur updateFournisseurt(Long id, Fournisseur fournisseur);

    void deleteFournisseur(Long id);

    Fournisseur findByCode(String code);

    Fournisseur findByRaisonSociale(String raisonSociale);

    Fournisseur findByEmail(String email);

    Integer countNumberOfFournisseurs();

    List<Fournisseur> findAllFournisseurs();

    List<Fournisseur> findAllFournisseursOrderDesc();

    List<Fournisseur> findListFournisseurByCode(String code);

    List<Fournisseur> findListFournisseurByRaisonSociale(String raisonSociale);


}
