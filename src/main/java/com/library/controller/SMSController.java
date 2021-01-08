package com.library.controller;

import com.library.entities.Client;
import com.library.entities.Fournisseur;
import com.library.services.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.twilio.rest.api.v2010.account.Message;

@CrossOrigin
@RestController
@RequestMapping("/alAmine")
public class SMSController {

    @Autowired
    private SMSService smsService;

    @PostMapping("/sendSMSToFournisseur")
    public Message sendSMS(@RequestBody Fournisseur fournisseur) {
        System.out.println(fournisseur);
        return smsService.sendSMSToFournisseur(fournisseur);
    }

    @PostMapping("/sendSMSToCustomer")
    public Message sendSMSToCustomer(@RequestBody Client client) {
        System.out.println(client);
        return smsService.sendSMSToClient(client);
    }


}
