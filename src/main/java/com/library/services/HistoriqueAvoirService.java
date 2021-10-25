package com.library.services;

import com.library.entities.HistoriqueAvoir;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface HistoriqueAvoirService {

    HistoriqueAvoir saveHistoriqueAvoir(HistoriqueAvoir historiqueAvoir);

    HistoriqueAvoir saveHistoriqueAvoirWithConnectedUserAndAvoirID(HistoriqueAvoir historiqueAvoir,
                                                                                           Long userId, Long avoirId);

    Optional<HistoriqueAvoir> findHistoriqueAvoirById(Long id);

    List<HistoriqueAvoir> findAllHistoriqueAvoirs();

    List<HistoriqueAvoir> findAllHistoriqueAvoirsOrderDesc();

    BigDecimal countNumberOfHistoriqueAvoirs();

    void deleteHistoriqueAvoir(Long id);

}
