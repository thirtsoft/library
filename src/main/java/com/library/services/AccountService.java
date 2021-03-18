package com.library.services;

import com.library.entities.Role;
import com.library.entities.Utilisateur;

public interface AccountService {

  //  Utilisateur saveUtilisateur(Utilisateur utilisateur);
    Role saveRole(Role role);
    void addRoleToUtilisateur(String username, String roleName);
    Utilisateur findUtilisateurByUsername(String username);

}
