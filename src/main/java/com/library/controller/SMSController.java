package com.library.controller;

import com.library.entities.Client;
import com.library.entities.Fournisseur;
import com.library.services.SMSService;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.library.utils.Constants.APP_ROOT;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class SMSController {

    @Autowired
    private SMSService smsService;

    @PostMapping(value = APP_ROOT + "/sendSMSToFournisseur")
    public Message sendSMS(@RequestBody Fournisseur fournisseur) {
        System.out.println(fournisseur);
        return smsService.sendSMSToFournisseur(fournisseur);
    }

    @PostMapping(value = APP_ROOT + "/sendSMSToCustomer")
    public Message sendSMSToCustomer(@RequestBody Client client) {
        System.out.println(client);
        return smsService.sendSMSToClient(client);
    }


}
