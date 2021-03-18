package com.library.services;

import com.library.entities.Client;
import com.library.entities.Fournisseur;
import com.twilio.rest.api.v2010.account.Message;

public interface SMSService {

    Message sendSMSToFournisseur(Fournisseur fournisseur);

    Message sendSMSToClient(Client client);

}
