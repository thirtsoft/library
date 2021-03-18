package com.library.services;

import com.library.entities.LigneAvoir;

import java.util.List;
import java.util.Optional;

public interface LigneAvoirService {

    List<LigneAvoir> findAllLigneAvoirs();

    Optional<LigneAvoir> findLigneAvoirById(Long lAvoirId);

    LigneAvoir saveLigneAvoir(LigneAvoir ligneAvoir);

    LigneAvoir updateLigneAvoir(Long lAvoirId, LigneAvoir ligneAvoir);

    void deleteLavoirByNumero(long numero);

    List<LigneAvoir> findLigneAvoirByProduitId(Long prodId);

    List<LigneAvoir> findLigneAvoirByAvoirId(Long comId);

    List<LigneAvoir> findAllLavoirByNumero(long numero);

}
