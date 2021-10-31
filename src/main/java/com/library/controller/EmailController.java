package com.library.controller;

import com.library.entities.Client;
import com.library.entities.Email;
import com.library.entities.Fournisseur;
import com.library.services.EmailService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.library.utils.Constants.APP_ROOT;

@CrossOrigin
@RestController
//@RequestMapping("/alAmine")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping(value = APP_ROOT + "/sendEmail")
    @ApiOperation(value = "Envoyer un email",
            notes = "Cette méthode permet d'envoyer un email",
            response = Email.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'email a été envoyé / modifié"),
            @ApiResponse(code = 400, message = "Aucun Email  envoyé")
    })
    public ResponseEntity<Email> sendEmail(@RequestBody Email email) {
        try {
            emailService.sendEmail(email);
            return new ResponseEntity<>(email, HttpStatus.OK);
        } catch (MailException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = APP_ROOT + "/sendMail")
    @ApiOperation(value = "Envoyer un email à un Fournisseurs",
            notes = "Cette méthode permet d'envoyer un email à un Fournisseurs",
            response = Email.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'email a été envoyé / modifié"),
            @ApiResponse(code = 400, message = "Aucun Email  envoyé")
    })
    public ResponseEntity<Fournisseur> sendMail(@RequestBody Fournisseur fournisseur) {
        try {
            emailService.sendMail(fournisseur);
            return new ResponseEntity<>(fournisseur, HttpStatus.OK);
        } catch (MailException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = APP_ROOT + "/sendMailToAllFournisseur")
    @ApiOperation(value = "Envoyer un email à plusieurs Fournisseurs",
            notes = "Cette méthode permet d'envoyer un email à plusieurs Fournisseurs",
            response = Email.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'email a été envoyé / modifié"),
            @ApiResponse(code = 400, message = "Aucun Email  envoyé")
    })
    public ResponseEntity<Email> sendMail(@RequestBody Email email) {
        try {
            emailService.sendMailToAllFournisseurs(email);
            return new ResponseEntity<>(email, HttpStatus.OK);
        } catch (MailException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = APP_ROOT + "/sendMailToCustomer")
    public ResponseEntity<Client> sendMailToCustomer(@RequestBody Client client) {
        try {
            emailService.sendMailToClient(client);
            return new ResponseEntity<Client>(client, HttpStatus.OK);
        } catch (MailException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
