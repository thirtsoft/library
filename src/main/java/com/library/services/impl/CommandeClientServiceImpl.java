package com.library.services.impl;

import com.library.entities.CommandeClient;
import com.library.entities.LigneCmdClient;
import com.library.entities.Produit;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.CommandeClientRepository;
import com.library.services.CommandeClientService;
import com.library.services.LigneCmdClientService;
import com.library.services.ProduitService;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommandeClientServiceImpl implements CommandeClientService {

    Logger logger = LoggerFactory.getLogger(CommandeClientServiceImpl.class);


    @Autowired
    private CommandeClientRepository commandeClientRepository;

    @Autowired
    private LigneCmdClientService ligneCmdClientService;

    @Autowired
    private ProduitService produitService;

    @Override
    public List<CommandeClient> findAllCommandeClient() {
        return commandeClientRepository.findAll();
    }

    @Override
    public List<CommandeClient> findAllCommandeClientsOrderDesc() {
        return commandeClientRepository.findByOrderByIdDesc();
    }

    @Override
    public List<CommandeClient> findListCommandeClientOf3LatestMonth() {
        return commandeClientRepository.findListCommandeClientOf3LatestMonth();
    }

    @Override
    public List<CommandeClient> findTop500OfCommandeClientOrderByIdDesc() {
        return commandeClientRepository.findTop500ByOrderByIdDesc();
    }

    @Override
    public Optional<CommandeClient> findCommandeClientById(Long comId) {
        if (!commandeClientRepository.existsById(comId)) {
            throw new ResourceNotFoundException("CommandeClient not found");
        }

        return commandeClientRepository.findById(comId);
    }

    @Override
    public CommandeClient createCommande(CommandeClient commandeClient) {

        List<LigneCmdClient> lcmdClient = commandeClient.getLcomms();
        ligneCmdClientService.saveListLigneCmd(lcmdClient);

        double total = 0;
        for (int i = 0; i < lcmdClient.size(); i++) {

            LigneCmdClient lcdClient = lcmdClient.get(i);
            Produit produit = produitService.findProduitById(lcdClient.getProduit().getId()).get();
            lcdClient.setProduit(produit);
//			lcdClient.setPrix(produit.getPrixVente());
            ligneCmdClientService.saveLigneCmdClient(lcdClient);

            total += lcdClient.getQuantite() * lcdClient.getProduit().getPrixVente();
        }

        commandeClient.setTotalCommande(total);
        commandeClient.setLcomms(lcmdClient);
        return commandeClientRepository.save(commandeClient);
    }

    /**
     * @param commande
     * @return methode permettant d'ajouter d'abord une commande
     * puis les lignes de commandes correspondantes
     */

    @Override
    public CommandeClient saveCommandeClient(CommandeClient commande) {

        System.out.println("Initial Numero Commande " + commande.getNumeroCommande());

        logger.info("Commande {}", commande);
        List<LigneCmdClient> ligneCmd = commande.getLcomms();
        if (ligneCmd == null || ligneCmd.size() == 0) {
            throw new IllegalArgumentException("Vous devez au moins commander un produit");
        }
        if ((commande.getClient().getId() == null)) {
            throw new IllegalArgumentException("Vous devez selectionner un client");
        }

        for (LigneCmdClient lcom : ligneCmd) {
            Produit produitInitial = produitService.findProduitById(lcom.getProduit().getId()).get();
            if (lcom.getQuantite() > produitInitial.getQtestock()) {
                throw new IllegalArgumentException("La Quantité de stock du produit est insuffusante");
            }
        }

        commandeClientRepository.save(commande);
        System.out.println("1er Numero Commande " + commande.getNumeroCommande());

        List<LigneCmdClient> ligneCmdClients = commande.getLcomms();
        double total = 0;
        for (LigneCmdClient lcmdClt : ligneCmdClients) {
            lcmdClt.setCommande(commande);
            lcmdClt.setNumero(commande.getNumeroCommande());
            ligneCmdClientService.saveLigneCmdClient(lcmdClt);

            Produit produit = produitService.findProduitById(lcmdClt.getProduit().getId()).get();
            if (produit != null) {
                produit.setQtestock(produit.getQtestock() - lcmdClt.getQuantite());
                produitService.updateProduit(produit.getId(), produit);
            }
            lcmdClt.setPrix(produit.getPrixVente());

            System.out.println(produit.getPrixVente());
            System.out.println(lcmdClt.getQuantite());
            System.out.println(lcmdClt.getQuantite() * produit.getPrixVente());

            total += (lcmdClt.getQuantite() * lcmdClt.getPrixCommande());

        }

        commande.setTotalCommande(total);
        commande.setStatus("valider");
        commande.setDateCommande(new Date());

        System.out.println("Dernier Numero Commande " + commande.getNumeroCommande());

        return commandeClientRepository.save(commande);


    }


    @Override
    public ResponseEntity<String> createOrder(CommandeClient commandeClient) {
        try {
            CommandeClient newCom = new CommandeClient();
            List<LigneCmdClient> lcoms = new ArrayList<LigneCmdClient>();
            lcoms = commandeClient.getLcomms();

            for (int i = 0; i < lcoms.size(); i++) {
                LigneCmdClient lcom = lcoms.get(i);
                Produit produit = lcom.getProduit();
                if (lcom.getQuantite() <= produit.getQtestock()) {
                    Produit newProduit = produitService.findProduitById(produit.getId()).get();
                    newProduit.setQtestock(produit.getQtestock() - lcom.getQuantite());
                    produitService.saveProduit(newProduit);

                } else return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("This order cannot be created[Empty inventory]!(" + HttpStatus.BAD_REQUEST + ")");
            }

            newCom.setNumeroCommande(commandeClient.getNumeroCommande());
            newCom.setClient(commandeClient.getClient());
            newCom.setTotalCommande(commandeClient.getTotalCommande());
            newCom.setStatus(commandeClient.getStatus());
            newCom.setDateCommande(new Date());

            newCom.setLcomms(ligneCmdClientService.saveListLigneCmd(lcoms));

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Order has been created!(" + HttpStatus.CREATED + ")");


        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "This order cannot be created!", e);
        }

    }

    @Override
    public void deleteCommande(Long id) {
        Optional<CommandeClient> commandeInfo = commandeClientRepository.findById(id);
        if (commandeInfo.isPresent()) {
            CommandeClient commandeClient = commandeInfo.get();
            ligneCmdClientService.deleteLcomByNumero(commandeClient.getNumeroCommande());
            commandeClientRepository.delete(commandeClient);
        }
    }

    @Override
    public List<?> countNumberTotalOfCommandeByMonth() {
        return commandeClientRepository.countNumberOfCommandeByMonth();
    }

    @Override
    public List<?> sumTotalOfCommandeByMonth() {
        return commandeClientRepository.sumTotalOfCommandeByMonth();
    }


    @Override
    public CommandeClient updateCommandeClient(Long comId, CommandeClient commande) {
        if (!commandeClientRepository.existsById(comId)) {
            throw new ResourceNotFoundException("Commande that id is" + comId + "not found");
        }

        Optional<CommandeClient> cmdClient = commandeClientRepository.findById(comId);

        if (!cmdClient.isPresent()) {
            throw new ResourceNotFoundException("Commande not found");
        }

        CommandeClient cmdClientResult = cmdClient.get();

        cmdClientResult.setNumeroCommande(commande.getNumeroCommande());
        cmdClientResult.setDateCommande(commande.getDateCommande());
        cmdClientResult.setClient(commande.getClient());
        cmdClientResult.setTotalCommande(commande.getTotalCommande());
        cmdClientResult.setStatus(commande.getStatus());

        return commandeClientRepository.save(cmdClientResult);
    }

    @Override
    public ResponseEntity<Object> deleteCommandeClient(Long id) {
        if (!commandeClientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Commande not found");
        }

        commandeClientRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }


    @Override
    public int getNombreCommandes(Date d1, Date d2) {
        return commandeClientRepository.countBetween(d1, d2);
    }

    @Override
    public int getNumberOfCommande() {
        return commandeClientRepository.countNumberOfCommande();
    }

    @Override
    public BigDecimal countNumbersOfCommandes() {
        return commandeClientRepository.countNumbersOfCommandes();
    }

    @Override
    public BigDecimal sumTotalOfCommandesByMonth() {
        return commandeClientRepository.sumTotalOfCommandesByMonth();
    }

    @Override
    public BigDecimal sumTotalOfCommandesByYear() {
        return commandeClientRepository.sumTotalOfCommandesByYear();
    }

    @Override
    public CommandeClient findByNumeroCommande(Long numeroCommande) {
        return commandeClientRepository.findByNumeroCommande(numeroCommande);
    }

    @Override
    public CommandeClient findByStatus(String status) {
        return commandeClientRepository.findByStatus(status);
    }

    @Override
    public List<CommandeClient> findListCommandeClientByStatus(String status) {
        return commandeClientRepository.ListCommandeClientByStatus(status);
    }

    @Override
    public List<CommandeClient> findCommandeClientByClientId(Long clientId) {
        return commandeClientRepository.ListCommandeClientByClientId(clientId);
    }

    @Override
    public List<CommandeClient> findCommandeByDate(Date dateCommande) {
        return commandeClientRepository.findAllByDateCommande(dateCommande);
    }

    @Override
    public long generateCodeCommand() {
        final String FORMAT = "yyyyMMddHHmmss";
        return Long.parseLong(DateTimeFormat.forPattern(FORMAT).print(LocalDateTime.now()));
    }
}
