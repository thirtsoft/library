package com.library.services;

import com.library.entities.HistoriqueCommande;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface HistoriqueCommandeService {

    HistoriqueCommande saveHistoriqueCommande(HistoriqueCommande historiqueCommande);

    HistoriqueCommande saveHistoriqueCommandeWithConnectedUserAndComID(HistoriqueCommande historiqueCommande,
                                                                       Long userId, Long comId);

    Optional<HistoriqueCommande> findHistoriqueCommandeById(Long id);

    List<HistoriqueCommande> findAllHistoriqueCommandes();

    List<HistoriqueCommande> findAllHistoriqueCommandesOrderDesc();

    BigDecimal countNumberOfHistoriqueCommandes();

    void deleteHistoriqueCommande(Long id);

}
