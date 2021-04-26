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

    Fournisseur findByNom(String nom);

    Fournisseur findByRaisonSociale(String raisonSociale);

    Fournisseur findByNomBank(String nomBank);

    Fournisseur findByEmail(String email);

    Integer countNumberOfFournisseurs();

    List<Fournisseur> findAllFournisseurs();

    List<Fournisseur> findListFournisseurByCode(String code);

    List<Fournisseur> findListFournisseurByNom(String nom);

    List<Fournisseur> findListFournisseurByRaisonSociale(String raisonSociale);


}
