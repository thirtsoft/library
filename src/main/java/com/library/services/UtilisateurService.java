package com.library.services;

import com.library.entities.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {

    List<Utilisateur> findAllUtilisateurs();

    Optional<Utilisateur> findUtilisateurById(Long idUser);

    Utilisateur saveUtilisateur(Utilisateur utilisateur);

    Utilisateur updateUtilisateur(Long idUser, Utilisateur utilisateur);

    //  boolean updateUsername(String email, String username);
    //  boolean updatePassword(String email, String password, String newPassword);
    void deleteUtilisateur(Long idUser);

    Utilisateur findUtilisateurByUsername(String username);
    //Utilisateur findUtilisateurByEmail(String email);

    List<Utilisateur> findListUtilisateurByUsername(String username);

    //  public boolean updateUsernameOfUtilisateur(String email, String username);

    boolean updateUsernameOfUtilisateur(String username, String newUsername);

    //  public boolean updatePasswordofUtilisateur(String email, String oldPass, String newPass);

    boolean updatePasswordofUtilisateur(String username, String oldPass, String newPass);

}
