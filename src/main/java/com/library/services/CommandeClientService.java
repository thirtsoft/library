package com.library.services;

import com.library.entities.CommandeClient;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CommandeClientService {

    List<CommandeClient> findAllCommandeClient();

    Optional<CommandeClient> findCommandeClientById(Long comId);

    CommandeClient saveCommandeClient(CommandeClient commande);

    CommandeClient updateCommandeClient(Long comId, CommandeClient commande);

    ResponseEntity<Object> deleteCommandeClient(Long id);


    int getNombreCommandes(Date d1, Date d2);

    int getNumberOfCommande();

    BigDecimal countNumbersOfCommandes();

    BigDecimal sumTotalOfCommandesByMonth();

    BigDecimal sumTotalOfCommandesByYear();


    CommandeClient findByNumeroCommande(Long numeroCommande);

    CommandeClient findByStatus(String status);

    List<CommandeClient> findListCommandeClientByStatus(String status);

    List<CommandeClient> findCommandeClientByClientId(Long clientId);

    List<CommandeClient> findCommandeByDate(Date dateCommande);

    CommandeClient createCommande(CommandeClient commandeClient);

    ResponseEntity<String> createOrder(CommandeClient commandeClient);

    void deleteCommande(Long id);

    List<?> countNumberTotalOfCommandeByMonth();

    List<?> sumTotalOfCommandeByMonth();

    /**
     * Permet de generer le code de commande
     *
     * @return
     */
    long generateCodeCommand();

}
