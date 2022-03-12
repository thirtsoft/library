package com.library.controller;

import com.library.entities.CommandeClient;
import com.library.entities.HistoriqueCommande;
import com.library.entities.LigneCmdClient;
import com.library.entities.Utilisateur;
import com.library.exceptions.ResourceNotFoundException;
import com.library.security.services.UserPrinciple;
import com.library.services.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.library.utils.Constants.APP_ROOT;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CommandeClientController {

    @Autowired
    private CommandeClientService commandeClientService;

    @Autowired
    private LigneCmdClientService ligneCmdClientService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private HistoriqueCommandeService historiqueCommandeService;

    @Autowired
    private ReportService reportCommande;

    @GetMapping(value = APP_ROOT + "/commandes/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des CommandeClient",
            notes = "Cette méthode permet de chercher et renvoyer la liste des CommandeClient", responseContainer = "List<CommandeClient>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des CommandeClient / une liste vide")
    })
    public List<CommandeClient> getAllCommandeClients() {
        return commandeClientService.findAllCommandeClient();
    }

    @GetMapping(value = APP_ROOT + "/commandes/allCommandeClientOrderDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des CommandeClient",
            notes = "Cette méthode permet de chercher et renvoyer la liste des CommandeClient",
            responseContainer = "List<CommandeClient>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des CommandeClient / une liste vide")
    })
    ResponseEntity<List<CommandeClient>> getAllCommandeClientOrderDesc() {
        List<CommandeClient> commandeClientList = commandeClientService.findAllCommandeClientsOrderDesc();
        return new ResponseEntity<>(commandeClientList, HttpStatus.OK);
    }

    @GetMapping(value = APP_ROOT + "/commandes/allCommandeClientOf3LatestMonths", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des CommandeClient des trois derniers mois",
            notes = "Cette méthode permet de chercher et renvoyer la liste des CommandeClient des trois derniers mois",
            responseContainer = "List<CommandeClient>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des CommandeClient / une liste vide")
    })
    ResponseEntity<List<CommandeClient>> getAllCommandeClientOf3LatestMonths() {
        List<CommandeClient> commandeClientList = commandeClientService.findListCommandeClientOf3LatestMonth();
        return new ResponseEntity<>(commandeClientList, HttpStatus.OK);
    }

    @GetMapping(value = APP_ROOT + "/commandes/findTop500OfCommandeClientOrderByIdDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des 500 derniers CommandeClient",
            notes = "Cette méthode permet de chercher et renvoyer la liste des 500 derniers CommandeClient",
            responseContainer = "List<CommandeClient>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des CommandeClient / une liste vide")
    })
    ResponseEntity<List<CommandeClient>> getTop500OfCommandeClientOrderByIdDesc() {
        List<CommandeClient> commandeClientList = commandeClientService.findTop500OfCommandeClientOrderByIdDesc();
        return new ResponseEntity<>(commandeClientList, HttpStatus.OK);
    }

    @GetMapping(value = APP_ROOT + "/commandes/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping(value = APP_ROOT + "/commandes/searchCommandeByNumeroCommande", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping(value = APP_ROOT + "/commandes/NumberOfCommande")
    public int getNumberOfCommandes() {
        return commandeClientService.getNumberOfCommande();
    }

    @GetMapping(value = APP_ROOT + "/commandes/NumbersOfCommandes")
    @ApiOperation(value = "Compter le nombre de CommandeClient",
            notes = "Cette méthode permet de compter le nombre total de CommandeClient")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le nombre de CommandeClient / une liste vide")
    })
    public BigDecimal getNumbersOfCommandes() {
        return commandeClientService.countNumbersOfCommandes();
    }

    @GetMapping(value = APP_ROOT + "/commandes/SumsOfCommandesByMonth")
    @ApiOperation(value = "Additionner les commandes par mois",
            notes = "Cette méthode permet d'additionner le total des commandes par mois")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La somme des commandes par mois / une liste vide")
    })
    public BigDecimal sumTotalOfCommandesByMonth() {
        return commandeClientService.sumTotalOfCommandesByMonth();
    }

    @GetMapping(value = APP_ROOT + "/commandes/SumsOfCommandesByYear")
    @ApiOperation(value = "Additionner les commandes par annnées",
            notes = "Cette méthode permet d'additionner le total des commandes par annnées")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La somme des commandes par annnées / une liste vide")
    })
    public BigDecimal sumTotalOfCommandesByYear() {
        return commandeClientService.sumTotalOfCommandesByYear();
    }

    @GetMapping(value = APP_ROOT + "/commandes/searchCommandeClientByStatus")
    public CommandeClient getCommandeClientByStatus(@RequestParam("status") String status) {
        return commandeClientService.findByStatus(status);
    }

    @GetMapping(value = "/searchListCommandeClientByStatus")
    public List<CommandeClient> getAllCommandeClientByStatus(@RequestParam("status") String status) {
        return commandeClientService.findListCommandeClientByStatus(status);
    }

    @GetMapping(value = APP_ROOT + "/commandes/searchListCommandeClientByClientId")
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
    @PostMapping(value = APP_ROOT + "/comms", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Ajouter une commande",
            notes = "Cette méthode permet d'ajouter une commande", response = CommandeClient.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La commande a été ajouté"),
            @ApiResponse(code = 400, message = "Aucun commande a été ajouté")

    })
    public ResponseEntity<CommandeClient> enregistrerCommande(@RequestBody CommandeClient commandeClient, @RequestParam Long id) {

        CommandeClient commandeClientResultat;

        Utilisateur userInfo = utilisateurService.findUtilisateurById(id).get();
        commandeClient.setUtilisateur(userInfo);
        commandeClient.setNumeroCommande(this.generateCodeCommand());

        commandeClientResultat = commandeClientService.createCommande(commandeClient);

        HistoriqueCommande historiqueCommande = new HistoriqueCommande();

        historiqueCommande.setUtilisateur(userInfo);
        historiqueCommande.setCommandeClient(commandeClientResultat);
        historiqueCommande.setAction("AJOUT COMMANDE");
        historiqueCommande.setCreatedDate(new Date());

        historiqueCommandeService.saveHistoriqueCommande(historiqueCommande);

        List<LigneCmdClient> lcomms = commandeClient.getLcomms();
        for (LigneCmdClient lc : lcomms) {
            lc.setNumero(commandeClient.getNumeroCommande());
            ligneCmdClientService.saveLigneCmdClient(lc);

        }

        return new ResponseEntity<>(commandeClientResultat, HttpStatus.CREATED);

    }

    @PostMapping(value = APP_ROOT + "/commandes")
    public ResponseEntity<CommandeClient> createCommande(@RequestBody CommandeClient commandeClient, @RequestParam Long id) {

        CommandeClient commandeClientResultat;

        HistoriqueCommande historiqueCommande = new HistoriqueCommande();

        Utilisateur utilisateur = utilisateurService.findUtilisateurById(id).get();

        commandeClient.setUtilisateur(utilisateur);

        commandeClientResultat = commandeClientService.createCommande(commandeClient);

        historiqueCommande.setUtilisateur(utilisateur);
        historiqueCommande.setCommandeClient(commandeClientResultat);
        historiqueCommande.setAction("AJOUT COMMANDE");
        historiqueCommande.setCreatedDate(new Date());

        historiqueCommandeService.saveHistoriqueCommande(historiqueCommande);

        return new ResponseEntity<>(commandeClientResultat, HttpStatus.CREATED);
    }

    @PostMapping(value = APP_ROOT + "/commandes/commandesClientes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une commande",
            notes = "Cette méthode permet d'enregistrer et modifié une commande", response = CommandeClient.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "La commande a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun commande a été crée / modifié")

    })
    public ResponseEntity<CommandeClient> createCommandeClient(@RequestBody CommandeClient commandeClient, @RequestParam Long id) {

        CommandeClient commandeClientResultat;

        HistoriqueCommande historiqueCommande = new HistoriqueCommande();

        Utilisateur utilisateur = utilisateurService.findUtilisateurById(id).get();
        commandeClient.setUtilisateur(utilisateur);
        commandeClient.setNumeroCommande(this.generateCodeCommand());

        commandeClientResultat = commandeClientService.saveCommandeClient(commandeClient);

        historiqueCommande.setUtilisateur(utilisateur);
        historiqueCommande.setCommandeClient(commandeClientResultat);
        historiqueCommande.setAction("AJOUT COMMANDE");
        historiqueCommande.setCreatedDate(new Date());

        historiqueCommandeService.saveHistoriqueCommande(historiqueCommande);

        return new ResponseEntity<CommandeClient>(commandeClientResultat, HttpStatus.CREATED);
    }

    @PutMapping(value = APP_ROOT + "/commandes/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier une commande",
            notes = "Cette méthode permet de modifier une commande", response = CommandeClient.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La commande a été modifié"),
            @ApiResponse(code = 400, message = "Aucun commande a été modifié")

    })
    public ResponseEntity<CommandeClient> updateLigneCmdClient(@PathVariable(value = "id") Long id, @RequestBody CommandeClient commandeClient) throws Exception {

        CommandeClient commandeClientResultat;

        HistoriqueCommande historiqueCommande = new HistoriqueCommande();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple authUser = (UserPrinciple) authentication.getPrincipal();

        Optional<Utilisateur> optionalUtilisateur = utilisateurService.findUtilisateurById(authUser.getId());
        Utilisateur utilisateur = optionalUtilisateur.get();

        commandeClient.setId(id);

        commandeClientResultat = commandeClientService.saveCommandeClient(commandeClient);

        historiqueCommande.setUtilisateur(utilisateur);
        historiqueCommande.setCommandeClient(commandeClientResultat);
        historiqueCommande.setAction("MODIFICATION COMMANDE");
        historiqueCommande.setCreatedDate(new Date());

        historiqueCommandeService.saveHistoriqueCommande(historiqueCommande);

        return new ResponseEntity<>(commandeClientResultat, HttpStatus.OK);

    }

    @DeleteMapping(value = APP_ROOT + "/commandes/delete/{id}")
    @ApiOperation(value = "Supprimer une CommandeClient par son ID",
            notes = "Cette méthode permet de supprimer une CommandeClient par son ID", response = CommandeClient.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La Commande a été supprimé")
    })
    public ResponseEntity<?> deleteCommande(@PathVariable(value = "id") Long id) {

        CommandeClient commandeClientResultat;

        HistoriqueCommande historiqueCommande = new HistoriqueCommande();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple authUser = (UserPrinciple) authentication.getPrincipal();

        Optional<Utilisateur> optionalUtilisateur = utilisateurService.findUtilisateurById(authUser.getId());
        Utilisateur utilisateur = optionalUtilisateur.get();

        commandeClientResultat = commandeClientService.findCommandeClientById(id).get();

        historiqueCommande.setUtilisateur(utilisateur);
        historiqueCommande.setCommandeClient(commandeClientResultat);
        historiqueCommande.setAction("SUPPRESSSION COMMANDE");
        historiqueCommande.setCreatedDate(new Date());
        historiqueCommandeService.saveHistoriqueCommande(historiqueCommande);

        commandeClientService.deleteCommande(id);

        return ResponseEntity.ok().build();

    }

    @GetMapping(value = APP_ROOT + "/commandes/searchNumberOfCommandeByMonth")
    public List<?> getNumberTotalOfCommandeByMonth() {
        return commandeClientService.countNumberTotalOfCommandeByMonth();
    }

    @GetMapping(value = APP_ROOT + "/commandes/searchSumCommandeByMonth")
    public List<?> getSumTotalOfCommandeByMonth() {
        return commandeClientService.sumTotalOfCommandeByMonth();
    }


    @PostMapping(path = "/createOrder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUserRequest(@RequestBody CommandeClient CcmmandeClient) {
        return commandeClientService.createOrder(CcmmandeClient);
    }


    @GetMapping(value = APP_ROOT + "/commandes/generateCodeCommand")
    public long generateCodeCommand() {
        return commandeClientService.generateCodeCommand();
    }
}
