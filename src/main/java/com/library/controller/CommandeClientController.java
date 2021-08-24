package com.library.controller;

import com.library.entities.CommandeClient;
import com.library.entities.LigneCmdClient;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.ClientService;
import com.library.services.CommandeClientService;
import com.library.services.LigneCmdClientService;
import com.library.services.ReportService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/commandes", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des CommandeClient",
            notes = "Cette méthode permet de chercher et renvoyer la liste des CommandeClient", responseContainer = "List<CommandeClient>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des CommandeClient / une liste vide")
    })
    public List<CommandeClient> getAllCommandeClients() {
        return commandeClientService.findAllCommandeClient();

    }

    @GetMapping(value = "/commandes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un CommandeClient par ID",
            notes = "Cette méthode permet de chercher une CommandeClient par son ID", response = CommandeClient.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La CommandeClient a été trouver"),
            @ApiResponse(code = 404, message = "Aucun CommandeClient n'existe avec cette ID pas dans la BD")

    })
    public ResponseEntity<CommandeClient> getCommandeClientById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        CommandeClient commandeClient = commandeClientService.findCommandeClientById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande not found"));
        return ResponseEntity.ok().body(commandeClient);

    }

    @GetMapping(value = "/searchCommandeByNumeroCommande", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un CommandeClient par son Numero",
            notes = "Cette méthode permet de chercher une CommandeClient par son Numero", response = CommandeClient.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La CommandeClient a été trouver"),
            @ApiResponse(code = 404, message = "Aucun CommandeClient n'existe avec ce numero pas dans la BD")

    })
    public CommandeClient getCommandeClientByNumeroCommande(@RequestParam("num") long numeroCommande) {
        return commandeClientService.findByNumeroCommande(numeroCommande);
    }

    @GetMapping(value = "/NumberOfCommande")
    public int getNumberOfCommandes() {
        return commandeClientService.getNumberOfCommande();
    }

    @GetMapping(value = "/NumbersOfCommandes")
    @ApiOperation(value = "Compter le nombre de CommandeClient",
            notes = "Cette méthode permet de compter le nombre total de CommandeClient")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le nombre de CommandeClient / une liste vide")
    })
    public BigDecimal getNumbersOfCommandes() {
        return commandeClientService.countNumbersOfCommandes();
    }

    @GetMapping(value = "/SumsOfCommandesByMonth")
    @ApiOperation(value = "Additionner les commandes par mois",
            notes = "Cette méthode permet d'additionner le total des commandes par mois")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La somme des commandes par mois / une liste vide")
    })
    public BigDecimal sumTotalOfCommandesByMonth() {
        return commandeClientService.sumTotalOfCommandesByMonth();
    }

    @GetMapping(value = "/SumsOfCommandesByYear")
    @ApiOperation(value = "Additionner les commandes par annnées",
            notes = "Cette méthode permet d'additionner le total des commandes par annnées")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La somme des commandes par annnées / une liste vide")
    })
    public BigDecimal sumTotalOfCommandesByYear() {
        return commandeClientService.sumTotalOfCommandesByYear();
    }

    @GetMapping(value = "/searchCommandeClientByStatus")
    public CommandeClient getCommandeClientByStatus(@RequestParam("status") String status) {
        return commandeClientService.findByStatus(status);
    }

    @GetMapping(value = "/searchListCommandeClientByStatus")
    public List<CommandeClient> getAllCommandeClientByStatus(@RequestParam("status") String status) {
        return commandeClientService.findListCommandeClientByStatus(status);
    }

    @GetMapping(value = "/searchListCommandeClientByClientId")
    public List<CommandeClient> getAllCommandeClientByClientId(@RequestParam("clientId") Long clientId) {
        return commandeClientService.findCommandeClientByClientId(clientId);
    }

    @GetMapping(value = "/getAllCommandewithdate")
    public List<CommandeClient> getAllCommandesWithDatet(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateCommande) {

        return commandeClientService.findCommandeByDate(dateCommande);
    }


    /**
     * @param commandeClient
     * @return Methode qui marche ce 06/09/2020
     */
    @PostMapping(value = "/comms", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Ajouter une commande",
            notes = "Cette méthode permet d'ajouter une commande", response = CommandeClient.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La commande a été ajouté"),
            @ApiResponse(code = 400, message = "Aucun commande a été ajouté")

    })
    public ResponseEntity<CommandeClient> enregistrerCommande(@RequestBody CommandeClient commandeClient) {
        commandeClientService.createCommande(commandeClient);

        List<LigneCmdClient> lcomms = commandeClient.getLcomms();
        for (LigneCmdClient lc : lcomms) {
            lc.setNumero(commandeClient.getNumeroCommande());
            ligneCmdClientService.saveLigneCmdClient(lc);

        }

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PostMapping(value = "/commandes")
    public ResponseEntity<CommandeClient> createCommande(@RequestBody CommandeClient commandeClient) {

        commandeClientService.createCommande(commandeClient);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/commandesClientes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une commande",
            notes = "Cette méthode permet d'enregistrer et modifié une commande", response = CommandeClient.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "La commande a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun commande a été crée / modifié")

    })
    public ResponseEntity<CommandeClient> createCommandeClient(@RequestBody CommandeClient commandeClient) {

        return new ResponseEntity<CommandeClient>(commandeClientService.saveCommandeClient(commandeClient), HttpStatus.CREATED);
    }

    @PutMapping(value = "/commandes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier une commande",
            notes = "Cette méthode permet de modifier une commande", response = CommandeClient.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La commande a été modifié"),
            @ApiResponse(code = 400, message = "Aucun commande a été modifié")

    })
    public ResponseEntity<CommandeClient> updateLigneCmdClient(@PathVariable(value = "id") Long id, @RequestBody CommandeClient commandeClient) throws Exception {
        commandeClient.setId(id);
        return new ResponseEntity<>(commandeClientService.saveCommandeClient(commandeClient), HttpStatus.OK);

    }

    @DeleteMapping(value = "/commandes/{id}")
    @ApiOperation(value = "Supprimer une CommandeClient par son ID",
            notes = "Cette méthode permet de supprimer une CommandeClient par son ID", response = CommandeClient.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La Commande a été supprimé")
    })
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

    /*
    @GetMapping("/report/pdf/{id}")
    public String generateReport(@PathVariable Long id) throws FileNotFoundException, JRException {
        return reportCommande.exportReport(id);
    }
    */

    @GetMapping("/generateCodeCommand")
    public long generateCodeCommand() {
        return commandeClientService.generateCodeCommand();
    }
}
