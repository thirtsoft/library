package com.library.services;

import com.library.entities.Client;
import com.library.entities.Email;
import com.library.entities.Fournisseur;

public interface EmailService {

    void sendEmail(Email email);

    void sendMail(Fournisseur fournisseur);

    void sendMailToAllFournisseurs(Email email);

    void sendMailToClient(Client client);

}
