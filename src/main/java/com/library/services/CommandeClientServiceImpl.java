package com.library.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.library.entities.Client;
import com.library.entities.CommandeClient;
import com.library.entities.LigneCmdClient;
import com.library.entities.Produit;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.ClientRepository;
import com.library.repository.CommandeClientRepository;
import com.library.repository.LigneCmdClientRepository;
import com.library.repository.ProduitRepository;

@Service
@Transactional
public class CommandeClientServiceImpl implements CommandeClientService {

    @Autowired
    private CommandeClientRepository commandeClientRepository;

    @Autowired
    private LigneCmdClientRepository ligneCmdClientRepository;

    @Autowired
    private LigneCmdClientService ligneCmdClientService;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private ProduitService produitService;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<CommandeClient> findAllCommandeClient() {
        return commandeClientRepository.findAll();
    }

    @Override
    public Optional<CommandeClient> findCommandeClientById(Long comId) {
        if (!commandeClientRepository.existsById(comId)) {
            throw new ResourceNotFoundException("CommandeClient that id is" + comId + "not found");
        }

        return commandeClientRepository.findById(comId);
    }

    @Override
    public CommandeClient createCommande(CommandeClient commandeClient) {
//		CommandeForm orderForm = new CommandeForm();
//		Client client = new Client();
//		client.setRaisonSocial(orderForm.getClient().getRaisonSocial());
//		client.setChefService(orderForm.getClient().getChefService());
//		client.setAdresse(orderForm.getClient().getAdresse());
//		client.setTelephone(orderForm.getClient().getTelephone());
//		client.setEmail(orderForm.getClient().getEmail());

//		client = clientRepository.save(client);
//
//		System.out.println(client.getId());
//
//		commandeClient.setClient(client);

//		commandeClientRepository.save(commandeClient);

        List<LigneCmdClient> lcmdClient = commandeClient.getLigneCmdClients();
        ligneCmdClientRepository.saveAll(lcmdClient);

//        CommandeClient saveCom = commandeClientRepository.save(commandeClient);


        double total = 0;
        for (int i = 0; i < lcmdClient.size(); i++) {

            LigneCmdClient lcdClient = lcmdClient.get(i);
            Produit produit = produitService.findProduitById(lcdClient.getProduit().getId()).get();
            lcdClient.setProduit(produit);
//			lcdClient.setPrix(produit.getPrixVente());
            ligneCmdClientRepository.save(lcdClient);

            total += lcdClient.getQuantite() * lcdClient.getProduit().getPrixVente();
        }

        commandeClient.setTotalCommande(total);
        commandeClient.setLigneCmdClients(lcmdClient);
        return commandeClientRepository.save(commandeClient);
    }

    @Override
    public CommandeClient saveCommandeClient(CommandeClient commande) {

        Produit produit = new Produit();
        produitRepository.save(produit);

        Client client = new Client();
        clientRepository.save(client);

        commande.setClient(client);
        commande.setNumCommande("Cmd " + 15 + (int) (Math.random() * 100));

        commande = commandeClientRepository.save(commande);

        List<LigneCmdClient> ligneCmdClients = commande.getLigneCmdClients();

        double total = 0;
        for (LigneCmdClient lcmdClt : ligneCmdClients) {
            lcmdClt.setNumero(commande.getNumCommande());
            lcmdClt.setProduit(produit);
            lcmdClt.setQuantite(produit.getQtestock());
            lcmdClt.setPrix(produit.getPrixVente());
            ligneCmdClientRepository.save(lcmdClt);

            total = produit.getQtestock() * produit.getPrixVente();

        }

        commande.setTotalCommande(total);
        commande.setStatus("valider");
        commande.setDateCommande(new Date());
        return commandeClientRepository.save(commande);


    }

    @Override
    public CommandeClient saveCommandeCliente(CommandeClient commande) {
        CommandeForm orderForm = new CommandeForm();
        Client client = new Client();
        client.setRaisonSocial(orderForm.getClient().getRaisonSocial());
        client.setChefService(orderForm.getClient().getChefService());
        client.setAdresse(orderForm.getClient().getAdresse());
        client.setTelephone(orderForm.getClient().getTelephone());
        client.setEmail(orderForm.getClient().getEmail());

        client = clientRepository.save(client);

        System.out.println(client.getId());

        commande.setClient(client);

        commandeClientRepository.save(commande);

        double total = 0;
        for (OrderProduct p : orderForm.getProducts()) {
            LigneCmdClient lcmdClient = new LigneCmdClient();

            lcmdClient.setCommande(commande);
            Produit produit = produitRepository.findById(p.getId()).get();
            lcmdClient.setProduit(produit);
            lcmdClient.setPrix(produit.getPrixVente());
            ;
            lcmdClient.setQuantite(p.getQuantity());
            ligneCmdClientRepository.save(lcmdClient);
            total += p.getQuantity() * produit.getPrixVente();
        }

        commande.setTotalCommande(total);
        commande.setNumCommande("Cmd " + 15 + (int) (Math.random() * 100));
        commande.setStatus("valider");

        return commandeClientRepository.save(commande);
    }

    @Override
    public CommandeClient updateCommandeClient(Long comId, CommandeClient commande) {
        if (!commandeClientRepository.existsById(comId)) {
            throw new ResourceNotFoundException("Commande that id is" + comId + "not found");
        }

        Optional<CommandeClient> cmdClient = commandeClientRepository.findById(comId);

        if (!cmdClient.isPresent()) {
            throw new ResourceNotFoundException("Commande that id is" + comId + "not found");
        }

        CommandeClient cmdClientResult = cmdClient.get();

        cmdClientResult.setNumCommande(commande.getNumCommande());
        cmdClientResult.setDateCommande(commande.getDateCommande());
        cmdClientResult.setClient(commande.getClient());
        cmdClientResult.setTotalCommande(commande.getTotalCommande());
        cmdClientResult.setStatus(commande.getStatus());

        return commandeClientRepository.save(cmdClientResult);
    }

    @Override
    public ResponseEntity<Object> deleteCommandeClient(Long id) {
        if (!commandeClientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Commande that id is" + id + "not found");
        }

        commandeClientRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @Override
    public CommandeClient saveCommandeClient(Long num, Date date, boolean valide, Client clt) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getNombreCommandes(Date d1, Date d2) {
        return commandeClientRepository.countBetween(d1, d2);
    }

    @Override
    public CommandeClient findByNumCommande(String numCommande) {
        return commandeClientRepository.findByNumCommande(numCommande);
    }

    @Override
    public CommandeClient findByStatus(String status) {
        return commandeClientRepository.findByStatus(status);
    }

    @Override
    public List<CommandeClient> findListCommandeClientByNumCommande(String numCommande) {
        return commandeClientRepository.ListCommandeClientByNumCommande(numCommande);
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
    public Page<CommandeClient> findAllCommandeClientByPageable(Pageable pageable) {
        return commandeClientRepository.findAllCommandeClientByPageable(pageable);
    }

    @Override
    public Page<CommandeClient> findAllCommandeClientByClient(Long clientId, Pageable pageable) {
        return commandeClientRepository.findCommandeClientByClientId(clientId, pageable);
    }

    @Override
    public Page<CommandeClient> findCommandeClientByKeyWord(String mc, Pageable pageable) {
        return commandeClientRepository.findCommandeClientByKeyWord(mc, pageable);
    }


}
