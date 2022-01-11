package com.library.controller;

import com.library.entities.Client;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.ClientService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.library.utils.Constants.APP_ROOT;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping(value = APP_ROOT + "/clients/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Clients",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Clients", responseContainer = "List<Client>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Client / une liste vide")
    })
    public List<Client> getAllClients() {
        return clientService.findAllClient();
    }

    @GetMapping(value = APP_ROOT + "/clients/allClientOrderDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Client",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Client",
            responseContainer = "List<Charge>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Client / une liste vide")
    })
    ResponseEntity<List<Client>> getAllClientOrderDesc() {
        List<Client> clientList = clientService.findAllClientsOrderDesc();
        return new ResponseEntity<>(clientList, HttpStatus.OK);
    }

    @GetMapping(value = APP_ROOT + "/clients/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Client par ID",
            notes = "Cette méthode permet de chercher un Client par son ID", response = Client.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La Client a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Client n'existe avec cette ID pas dans la BD")

    })
    public ResponseEntity<Client> getClientById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Client client = clientService.findClientById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        return ResponseEntity.ok().body(client);

    }

    @GetMapping(value = APP_ROOT + "/clients/NumberOfClients")
    @ApiOperation(value = "Compter le nombre de Client",
            notes = "Cette méthode permet de retourner le nombre total un Client")
    public Long getNumberOfClient() {
        return clientService.countNumberOfClient();
    }

    @GetMapping(value = "/searchClientByEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Client par EMAIL",
            notes = "Cette méthode permet de chercher un Client par son EMAIL", response = Client.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La Client a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Client n'existe avec cet EMAIL pas dans la BD")

    })
    public Client getClientByEmail(String email) {
        return clientService.findByEmail(email);
    }

    @GetMapping(value = APP_ROOT + "/clients/searchClientByRaisonSocial")
    public Client getClientByRaisonSocial(@RequestParam(value = "raisonSocial") String raisonSocial) {
        return clientService.findByRaisonSocial(raisonSocial);

    }

    @GetMapping(value = APP_ROOT + "/clients/searchListClientByRaisonSocial", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Clients par son CODE",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Clients par son CODE", responseContainer = "List<Client>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Client / une liste vide")
    })
    public List<Client> getListClientByRaisonSocial(@RequestParam(value = "raisonSocial") String raisonSocial) {

        return clientService.ListClientByRaisonSocial("%" + raisonSocial + "%");

    }

    @GetMapping(value = APP_ROOT + "/clients/ListClientGroupByRaisonSocial")
    public List<Object[]> getListClientGroupByRaisonSocial() {
        return clientService.ListClientGroupByRaisonSocial();
    }

    @PostMapping(value = APP_ROOT + "/clients/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un Client",
            notes = "Cette méthode permet d'enregistrer un Client", response = Client.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Le Client a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun Client  crée / modifié")

    })
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        if (clientService.findByCodeClient(client.getCodeClient()) != null) {
            return new ResponseEntity<>(client, HttpStatus.BAD_REQUEST);
        }
        if (clientService.findByEmail(client.getEmail()) != null) {
            return new ResponseEntity<>(client, HttpStatus.BAD_REQUEST);
        }
        if (clientService.findByTelephone(client.getTelephone()) != null) {
            return new ResponseEntity<>(client, HttpStatus.BAD_REQUEST);
        }
        clientService.saveClient(client);
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }

    @PutMapping(value = APP_ROOT + "/clients/update/{clientId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier un Client par son ID",
            notes = "Cette méthode permet de modifier un Client par son ID", response = Client.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Client a été modifié"),
            @ApiResponse(code = 400, message = "Aucun Client modifié")

    })
    public ResponseEntity<Client> updateClient(@PathVariable(value = "clientId") Long clientId, @RequestBody Client client) {
        client.setId(clientId);
        return new ResponseEntity<>(clientService.updateClient(clientId, client), HttpStatus.OK);
    }

    @PatchMapping(value = APP_ROOT + "/clients/udapteClientByEmail/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier un Client par son EMAIL",
            notes = "Cette méthode permet de modifier un Client par son EMAIL", response = Client.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Client avec l'email EMAIL a été modifié"),
            @ApiResponse(code = 400, message = "Aucun Client modifié")

    })
    public ResponseEntity<?> updateClientByEmail(@RequestParam("email") String email, @PathVariable("id") String id) {
        Client newClient = clientService.updateClientByEmail(email, id);
        return new ResponseEntity<>(newClient, HttpStatus.OK);
    }

    @DeleteMapping(value = APP_ROOT + "/clients/delete/{id}")
    @ApiOperation(value = "Supprimer un Client par son ID",
            notes = "Cette méthode permet de supprimer un Client par son ID", response = Client.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Client a été supprimé")
    })
    public ResponseEntity<?> deleteClient(@PathVariable(value = "id") Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok().build();

    }

}
