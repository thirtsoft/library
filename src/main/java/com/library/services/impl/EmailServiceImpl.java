package com.library.services.impl;

import com.library.entities.Client;
import com.library.entities.Email;
import com.library.entities.Fournisseur;
import com.library.repository.EmailRepository;
import com.library.services.EmailService;
import com.library.services.FournisseurService;
import com.library.utils.EmailConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private FournisseurService fournisseurService;

    private final JavaMailSender javaMailSender;


    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;

    }

    @Override
    public void sendEmail(Email email) throws MailException {

        boolean f = false;
    /*
        String from = "thirdiallo@gmail.com";

        String host="smtp.gmail.com";

        Properties properties = System.getProperties();
        System.out.println("PROPERTIES " +properties);

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");


        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("thirdiallo@gmail.com", "tairoudiallo91");
            }
        });
        session.setDebug(true);

        MimeMessage m = new MimeMessage(session);
        try {
            m.setFrom(from);
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            m.setSubject(subject);
            m.setText(message);

            Transport.send(m);

            System.out.println("Send Success.......................");

            f = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;

    */


        StringBuilder sb = new StringBuilder();
        sb.append("Nom : " + email.getName()).append(System.lineSeparator());
        sb.append("\n Message: " + email.getMessage());

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(email.getFournisseur().getEmail());
        mail.setFrom(email.getFrom());
        mail.setSubject(email.getSubject());
        mail.setText(email.getMessage());


        System.out.println(email);

        javaMailSender.send(mail);

        emailRepository.save(email);

    }

    @Override
    public void sendMail(Fournisseur fournisseur) throws MailException {

        StringBuilder sb = new StringBuilder();
        sb.append("Nom : " + EmailConstants.managerName).append(System.lineSeparator());
        sb.append("\n Subject : " + fournisseur.getSubject());
        sb.append("\n Message : " + fournisseur.getMessage());

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(fournisseur.getEmail());
        mail.setFrom(EmailConstants.from);
        mail.setSubject(fournisseur.getSubject());
        mail.setText(fournisseur.getMessage());


        System.out.println(fournisseur);

        javaMailSender.send(mail);


    }

    @Override
    public void sendMailToAllFournisseurs(Email email) {

        StringBuilder sb = new StringBuilder();
        sb.append("Nom : " + EmailConstants.managerName).append(System.lineSeparator());

        List<Fournisseur> furnishesList = fournisseurService.findAllFournisseurs();

        SimpleMailMessage mail = new SimpleMailMessage();

        for (int i = 0; i < furnishesList.size(); i++) {
            Fournisseur fournisseur = furnishesList.get(i);
            mail.setTo(fournisseur.getEmail());
            mail.setSubject(email.getSubject());
            mail.setText(email.getMessage());
            mail.setFrom(EmailConstants.from);
        }

        javaMailSender.send(mail);

    }

    @Override
    public void sendMailToClient(Client client) throws MailException {

        StringBuilder sb = new StringBuilder();
        sb.append("Nom : " + EmailConstants.managerName).append(System.lineSeparator());
        sb.append("\n Subject : " + client.getSubject());
        sb.append("\n Message : " + client.getMessage());

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(client.getEmail());
        mail.setFrom(EmailConstants.from);
        mail.setSubject(client.getSubject());
        mail.setText(client.getMessage());


        javaMailSender.send(mail);


    }


}
