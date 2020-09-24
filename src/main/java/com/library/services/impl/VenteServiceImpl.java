package com.library.services.impl;

import com.library.entities.LigneVente;
import com.library.entities.Produit;
import com.library.entities.Vente;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.VenteRepository;
import com.library.services.LigneVenteService;
import com.library.services.ProduitService;
import com.library.services.VenteService;
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
public class VenteServiceImpl implements VenteService {

    @Autowired
    private VenteRepository venteRepository;

    @Autowired
    private LigneVenteService ligneVenteService;

    @Autowired
    private ProduitService produitService;

    @Override
    public List<Vente> findAllVentes() {
        return venteRepository.findAll();
    }

    @Override
    public Optional<Vente> findVenteById(Long venteId) {
        if (!venteRepository.existsById(venteId)) {
            throw new ResourceNotFoundException("Vente N ° " + venteId + "not found");
        }

        return venteRepository.findById(venteId);
    }

    @Override
    public Vente saveVente(Vente vente) {
        venteRepository.save(vente);

        List<LigneVente> ligneVentes = vente.getLigneVentes();

        double total = 0;
        for (LigneVente lvente : ligneVentes) {
            lvente.setVente(vente);
            Produit produit = produitService.findProduitById(lvente.getProduit().getId()).get();
            lvente.setProduit(produit);
            lvente.setPrixVente(lvente.getProduit().getPrixDetail());
            ligneVenteService.saveLigneVente(lvente);

            total += lvente.getQuantite() * lvente.getProduit().getPrixDetail();

        }

        vente.setTotalVente(total);
        vente.setNumeroVente("Vente " + 20 + (int) (Math.random() * 100));
        vente.setDateVente(new Date());


        return venteRepository.save(vente);

    }

    @Override
    public Vente updateVente(Long venteId, Vente vente) {
        if (!venteRepository.existsById(venteId)) {
            throw new ResourceNotFoundException("Vente N° " + venteId + "not found");
        }
        Optional<Vente> venteProd = venteRepository.findById(venteId);
        if (!venteProd.isPresent()) {
            throw new ResourceNotFoundException("Vente N ° " + venteId + "not found");
        }
        Vente venteResultat = venteProd.get();

        venteResultat.setNumeroVente(vente.getNumeroVente());
        venteResultat.setTotalVente(vente.getTotalVente());
        venteResultat.setDateVente(new Date());

        return venteRepository.save(venteResultat);
    }

    @Override
    public ResponseEntity<Object> deleteVente(Long id) {
        if (!venteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vente N ° " + id + "not found");
        }
        venteRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @Override
    public Vente findVenteByNumeroVente(String numeroVente) {
        return venteRepository.findByNumeroVente(numeroVente);
    }

    @Override
    public List<Vente> findListVenteByNumeroVente(String numeroVente) {
        return venteRepository.findListVenteByNumeroVente(numeroVente);
    }

    @Override
    public Page<Vente> findAllVenteByPageable(Pageable pageable) {
        return venteRepository.findAllVenteByPageable(pageable);
    }

    @Override
    public Page<Vente> findVenteByKeyWord(String mc, Pageable pageable) {
        return venteRepository.findVenteByKeyWord(mc, pageable);
    }


}
