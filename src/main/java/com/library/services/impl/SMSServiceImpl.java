package com.library.services.impl;

import com.library.entities.Client;
import com.library.entities.Fournisseur;
import com.library.services.SMSService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class SMSServiceImpl implements SMSService {

    // Find your Account Sid and Token at twilio.com/console

    public static final String ACCOUNT_SID = "AC58b0100a0c93449564e14d17c81941b7";
    public static final String AUTH_TOKEN = "b0e09a637d87b9ce1c9b68cb71d68b9b";

    public static final String TWILIO_PHONE_NUMBER = "+14422273139";

//    public static final String TO_PHONE_NUMBER = "00221773959921";

    //   public static final String TEXT = "Bonjour ceci est test de twilio";

    @Override
    public Message sendSMSToFournisseur(Fournisseur fournisseur) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber(fournisseur.getTelephone()),//The phone number you are sending text to
                new PhoneNumber(TWILIO_PHONE_NUMBER), fournisseur.getMessage())
                .create();

        System.out.println(message.getSid());

        return message;
    }

    @Override
    public Message sendSMSToClient(Client client) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber(client.getTelephone()),//The phone number you are sending text to
                new PhoneNumber(TWILIO_PHONE_NUMBER), client.getMessage())
                .create();

        System.out.println(message.getSid());

        return message;
    }
}
