package com.library.controller;

import com.library.entities.Client;
import com.library.entities.Email;
import com.library.entities.Fournisseur;
import com.library.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/alAmine")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendEmail")
    public ResponseEntity<Email> sendEmail(@RequestBody Email email) {
        try {
            emailService.sendEmail(email);
            return new ResponseEntity<Email>(email, HttpStatus.OK);
        }catch(MailException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PostMapping("/sendMail")
    public ResponseEntity<Fournisseur> sendMail(@RequestBody Fournisseur fournisseur) {
        try {
            emailService.sendMail(fournisseur);
            return new ResponseEntity<Fournisseur>(fournisseur, HttpStatus.OK);
        }catch(MailException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PostMapping("/sendMailToCustomer")
    public ResponseEntity<Client> sendMailToCustomer(@RequestBody Client client) {
        try {
            emailService.sendMailToClient(client);
            return new ResponseEntity<Client>(client, HttpStatus.OK);
        }catch(MailException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
