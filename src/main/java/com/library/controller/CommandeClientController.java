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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @GetMapping("/searchListCommandeClientByPageable")
    public Page<CommandeClient> getCommandeClientByPageable(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size) {
        return commandeClientService.findAllCommandeClientByPageable(PageRequest.of(page, size));
    }

    @GetMapping("/searchListCommandeClientByClientPageable")
    public Page<CommandeClient> getAllCommandeClientByPageable(@RequestParam(name = "prod") Long clientId,
                                                               @RequestParam(name = "page") int page,
                                                               @RequestParam(name = "size") int size) {
        return commandeClientService.findAllCommandeClientByClient(clientId, PageRequest.of(page, size));
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

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/commandes")
    public ResponseEntity<CommandeClient> createCommande(@RequestBody CommandeClient commandeClient) {

        commandeClientService.createCommande(commandeClient);

//		List<LigneCmdClient> lcomms = commandeClient.getLigneCmdClients();
//		for (LigneCmdClient lc : lcomms) {
//			lc.setNumero(commandeClient.getNumCommande());
//			ligneCmdClientService.saveLigneCmdClient(lc);
//
//		}
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/commandesClientes")
    public ResponseEntity<CommandeClient> createCommandeClient(@RequestBody CommandeClient commandeClient) {
		/*
		CommandeClient Resultat = commandeClientService.saveCommandeClient(commandeClient);
		return ResponseEntity.ok(Resultat);
		*/

        return new ResponseEntity<CommandeClient>(commandeClientService.saveCommandeClient(commandeClient), HttpStatus.CREATED);
    }

    @PutMapping("/commandes/{id}")
    public ResponseEntity<CommandeClient> updateLigneCmdClient(@PathVariable(value = "id") Long id, @RequestBody CommandeClient commandeClient) throws Exception {
        commandeClient.setId(id);
        return new ResponseEntity<>(commandeClientService.saveCommandeClient(commandeClient), HttpStatus.OK);

    }

    @DeleteMapping("/commandes/{id}")
    public void deleteCommande(@PathVariable(value = "id") Long id) {
        commandeClientService.deleteCommande(id);
        //return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/searchNumberOfCommandeByMonth")
    public List<?> getNumberTotalOfCommandeByMonth() {
        return commandeClientService.countNumberTotalOfCommandeByMonth();
    }

    @GetMapping("/searchSumCommandeByMonth")
    public List<?> getSumTotalOfCommandeByMonth() {
        return commandeClientService.sumTotalOfCommandeByMonth();
    }

    /*
    @DeleteMapping("/commandes/{delete}")
    public ResponseEntity<Object> deleteCommandeClient(@PathVariable(value = "id") Long id) {
        return commandeClientService.deleteCommandeClient(id);

    }
*/
    @PostMapping(path = "/createOrder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUserRequest(@RequestBody CommandeClient CcmmandeClient) {
        return commandeClientService.createOrder(CcmmandeClient);
    }

    /*
	@PostMapping(path = "/reports/commande/pdf")
	public StringResult printCommande(@RequestBody Report report) {
		StringResult reportName = new StringResult();
    	try {
			reportName = this.reportService.createReport(report);

		} catch (SQLException e) {
    		e.printStackTrace();
		}
    	return  reportName;
	}
*/
    @GetMapping("/report/pdf/{id}")
    public String generateReport(@PathVariable Long id) throws FileNotFoundException, JRException {
        return reportCommande.exportReport(id);
    }

    @GetMapping("/generateCodeCommand")
    public long generateCodeCommand() {
        return commandeClientService.generateCodeCommand();
    }
}
