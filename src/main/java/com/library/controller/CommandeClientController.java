package com.library.controller;

import com.library.entities.CommandeClient;
import com.library.entities.LigneCmdClient;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.ClientService;
import com.library.services.CommandeClientService;
import com.library.services.LigneCmdClientService;
import com.library.services.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/prodApi")
public class CommandeClientController {

    @Autowired
    private CommandeClientService commandeClientService;

    @Autowired
    private LigneCmdClientService ligneCmdClientService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ReportService reportCommande;

    private Double total = 0.0;

    @GetMapping("/commandes")
    public List<CommandeClient> getAllCommandeClients() {
        return commandeClientService.findAllCommandeClient();

    }

    @GetMapping("/commandes/{id}")
    public ResponseEntity<CommandeClient> getCommandeClientById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        CommandeClient commandeClient = commandeClientService.findCommandeClientById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande not found"));
        return ResponseEntity.ok().body(commandeClient);

    }

    @GetMapping("/searchCommandeByNumeroCommande")
    public CommandeClient getCommandeClientByNumeroCommande(@RequestParam("num") long numeroCommande) {
        return commandeClientService.findByNumeroCommande(numeroCommande);
    }

    @GetMapping("/NumberOfCommande")
    public int getNumberOfCommandes() {
        return commandeClientService.getNumberOfCommande();
    }

    @GetMapping("/NumbersOfCommandes")
    public BigDecimal getNumbersOfCommandes() {
        return commandeClientService.countNumbersOfCommandes();
    }

    @GetMapping("/SumsOfCommandesByMonth")
    public BigDecimal sumTotalOfCommandesByMonth() {
        return commandeClientService.sumTotalOfCommandesByMonth();
    }

    @GetMapping("/SumsOfCommandesByYear")
    public BigDecimal sumTotalOfCommandesByYear() {
        return commandeClientService.sumTotalOfCommandesByYear();
    }

    @GetMapping("/searchCommandeClientByStatus")
    public CommandeClient getCommandeClientByStatus(@RequestParam("status") String status) {
        return commandeClientService.findByStatus(status);
    }

    @GetMapping("/searchListCommandeClientByStatus")
    public List<CommandeClient> getAllCommandeClientByStatus(@RequestParam("status") String status) {
        return commandeClientService.findListCommandeClientByStatus(status);
    }

    @GetMapping("/searchListCommandeClientByClientId")
    public List<CommandeClient> getAllCommandeClientByClientId(@RequestParam("clientId") Long clientId) {
        return commandeClientService.findCommandeClientByClientId(clientId);
    }

    @GetMapping("/getAllCommandewithdate")
    public List<CommandeClient> getAllCommandesWithDatet(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateCommande) {

        return commandeClientService.findCommandeByDate(dateCommande);
    }


    /**
     * @param commandeClient
     * @return Methode qui marche ce 06/09/2020
     */
    @PostMapping("/comms")
    public ResponseEntity<CommandeClient> enregistrerCommande(@RequestBody CommandeClient commandeClient) {
        commandeClientService.createCommande(commandeClient);

        List<LigneCmdClient> lcomms = commandeClient.getLcomms();
        for (LigneCmdClient lc : lcomms) {
            lc.setNumero(commandeClient.getNumeroCommande());
            ligneCmdClientService.saveLigneCmdClient(lc);

        }

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PostMapping("/commandes")
    public ResponseEntity<CommandeClient> createCommande(@RequestBody CommandeClient commandeClient) {

        commandeClientService.createCommande(commandeClient);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/commandesClientes")
    public ResponseEntity<CommandeClient> createCommandeClient(@RequestBody CommandeClient commandeClient) {

        return new ResponseEntity<CommandeClient>(commandeClientService.saveCommandeClient(commandeClient), HttpStatus.CREATED);
    }

    @PutMapping("/commandes/{id}")
    public ResponseEntity<CommandeClient> updateLigneCmdClient(@PathVariable(value = "id") Long id, @RequestBody CommandeClient commandeClient) throws Exception {
        commandeClient.setId(id);
        return new ResponseEntity<>(commandeClientService.saveCommandeClient(commandeClient), HttpStatus.OK);

    }

    @DeleteMapping("/commandes/{id}")
    public ResponseEntity<?> deleteCommande(@PathVariable(value = "id") Long id) {
        commandeClientService.deleteCommande(id);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/searchNumberOfCommandeByMonth")
    public List<?> getNumberTotalOfCommandeByMonth() {
        return commandeClientService.countNumberTotalOfCommandeByMonth();
    }

    @GetMapping("/searchSumCommandeByMonth")
    public List<?> getSumTotalOfCommandeByMonth() {
        return commandeClientService.sumTotalOfCommandeByMonth();
    }


    @PostMapping(path = "/createOrder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUserRequest(@RequestBody CommandeClient CcmmandeClient) {
        return commandeClientService.createOrder(CcmmandeClient);
    }

    @GetMapping("/report/pdf/{id}")
    public String generateReport(@PathVariable Long id) throws FileNotFoundException, JRException {
        return reportCommande.exportReport(id);
    }

    @GetMapping("/generateCodeCommand")
    public long generateCodeCommand() {
        return commandeClientService.generateCodeCommand();
    }
}
