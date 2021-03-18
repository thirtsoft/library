package com.library.services.impl;

import com.library.entities.LigneVente;
import com.library.entities.Produit;
import com.library.entities.Vente;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.VenteRepository;
import com.library.services.LigneVenteService;
import com.library.services.ProduitService;
import com.library.services.VenteService;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
            throw new ResourceNotFoundException("Vente Not found");
        }

        return venteRepository.findById(venteId);
    }

    public Vente saveVente(Vente vente) {
        List<LigneVente> ligneVente = vente.getLigneVentes();

        if (ligneVente == null || ligneVente.size() == 0) {
            throw new IllegalArgumentException("Vous devez au moins ajouter un produit");
        }
        for (LigneVente ligneV : ligneVente) {
            Produit produitInitial = produitService.findProduitById(ligneV.getProduit().getId()).get();
            if (ligneV.getQuantite() > produitInitial.getQtestock()) {
                throw new IllegalArgumentException("La Quantité de stock du produit est insuffusante");
            }
        }

        venteRepository.save(vente);

        List<LigneVente> ligneVentes = vente.getLigneVentes();

        double total = 0;
        for (LigneVente lvente : ligneVentes) {
            lvente.setVente(vente);
            lvente.setNumero(vente.getNumeroVente());

            ligneVenteService.saveLigneVente(lvente);

            Produit produit = produitService.findProduitById(lvente.getProduit().getId()).get();
            if (produit != null) {
                produit.setQtestock(produit.getQtestock() - lvente.getQuantite());
                produitService.updateProduit(produit.getId(), produit);
            }

            lvente.setPrixVente(produit.getPrixDetail());

            System.out.println(produit.getPrixDetail());
            System.out.println(lvente.getQuantite());
            System.out.println(lvente.getQuantite() * produit.getPrixDetail());

            total += (lvente.getQuantite() * produit.getPrixDetail());

        }

        vente.setTotalVente(total);
        vente.setStatus("valider");
        vente.setDateVente(new Date());

        return venteRepository.save(vente);

    }

    /**
     * Commenté le 19/10/2020
     *
     * @param vente
     * @return
     * @Override public Vente saveVente(Vente vente) {
     * venteRepository.save(vente);
     * <p>
     * List<LigneVente> ligneVentes = vente.getLigneVentes();
     * <p>
     * double total = 0;
     * for (LigneVente lvente : ligneVentes) {
     * lvente.setVente(vente);
     * Produit produit = produitService.findProduitById(lvente.getProduit().getId()).get();
     * lvente.setProduit(produit);
     * lvente.setPrixVente(lvente.getProduit().getPrixDetail());
     * ligneVenteService.saveLigneVente(lvente);
     * <p>
     * total += lvente.getQuantite() * lvente.getProduit().getPrixDetail();
     * <p>
     * }
     * <p>
     * vente.setTotalVente(total);
     * // vente.setNumeroVente("Vente " + 20 + (int) (Math.random() * 100));
     * vente.setDateVente(new Date());
     * <p>
     * <p>
     * return venteRepository.save(vente);
     * <p>
     * }
     */

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
    public int getNombreVentes(Date d1, Date d2) {
        return venteRepository.countBetween(d1, d2);
    }

    @Override
    public int getNumberOfVente() {
        return venteRepository.countNumberOfVente();
    }

    @Override
    public long generateNumeroVente() {
        final String FORMAT = "yyyyMMddHHmmss";
        return Long.parseLong(DateTimeFormat.forPattern(FORMAT).print(LocalDateTime.now()));
    }

    @Override
    public BigDecimal countSumsOfVentess() {
        return venteRepository.sumTotalOfVentes();
    }

    @Override
    public Vente findVenteByNumeroVente(long numeroVente) {
        return venteRepository.findByNumeroVente(numeroVente);
    }

    @Override
    public Vente findByStatus(String status) {
        return venteRepository.findByStatus(status);
    }

    @Override
    public ResponseEntity<Object> deleteVenteClient(Long id) {
        if (!venteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vente Not found");
        }
        venteRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @Override
    public void deleteVente(Long id) {
        Optional<Vente> venteInfo = venteRepository.findById(id);
        if (venteInfo.isPresent()) {
            Vente vente = venteInfo.get();
            ligneVenteService.deleteLventeByNumero(vente.getNumeroVente());
            venteRepository.delete(vente);
        }
    }

    @Override
    public List<Vente> findVenteWithParticularDayAndMonth() {
        return venteRepository.findVenteWithParticularDayAndMonth();
    }

    @Override
    public BigDecimal sumTotalOfVenteByDay() {
        return venteRepository.sumTotalOfVenteByDay();
    }

    @Override
    public Integer countNumberOfVenteByDay() {
        return venteRepository.countNumberOfVenteByDay();
    }

    @Override
    public List<?> countNumberTotalOfVenteByMonth() {
        return venteRepository.countNumberOfVenteByMonth();
    }


    @Override
    public List<?> sumTotalOfVenteByMonth() {
        return venteRepository.sumTotalOfVenteByMonth();
    }

    @Override
    public List<?> sumTotalOfVenteByYears() {
        return venteRepository.sumTotalOfVenteByYears();
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
