package com.library.services.impl;

import com.library.entities.Utilisateur;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.UtilisateurRepository;
import com.library.services.RoleService;
import com.library.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public List<Utilisateur> findAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    @Override
    public Optional<Utilisateur> findUtilisateurById(Long idUser) {
        if (!utilisateurRepository.existsById(idUser)) {
            throw new ResourceNotFoundException("User N ° " + idUser + "not found");
        }

        return utilisateurRepository.findById(idUser);
    }

    @Override
    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public Utilisateur updateUtilisateur(Long idUser, Utilisateur utilisateur) {
        if (!utilisateurRepository.existsById(idUser)) {
            throw new ResourceNotFoundException("User N ° " + idUser + "not found");
        }
        Optional<Utilisateur> user = utilisateurRepository.findById(idUser);
        if (!user.isPresent()) {
            throw new ResourceNotFoundException("User N ° " + idUser + "not found");
        }
        Utilisateur userResult = user.get();

        userResult.setUsername(utilisateur.getUsername());
        userResult.setPassword(utilisateur.getPassword());
        userResult.setActive(true);

        return utilisateurRepository.save(userResult);
    }
/*
    @Override
    public boolean updateUsername(String email, String username) {
        Utilisateur userInfo = utilisateurRepository.findByEmail(email);
        if (userInfo != null) {
            userInfo.setUsername(username);
            utilisateurRepository.save(userInfo);
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePassword(String email, String password, String newPassword) {

        return false;
    }
*/
    @Override
    public void deleteUtilisateur(Long idUser) {
        if (!utilisateurRepository.existsById(idUser)) {
            throw new ResourceNotFoundException("User N ° " + idUser + "not found");
        }

        utilisateurRepository.deleteById(idUser);
    }

    @Override
    public Utilisateur findUtilisateurByUsername(String username) {
        return null;
    }
/*
    @Override
    public Utilisateur findUtilisateurByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }
*/
    @Override
    public List<Utilisateur> findListUtilisateurByUsername(String username) {
        return utilisateurRepository.findListUtilisateurByUsername(username);
    }

    @Override
    public boolean updateUsernameOfUtilisateur(String username, String newUsername) {
        Optional<Utilisateur> existsUser = this.utilisateurRepository.findByUsername(username);
        Utilisateur user;
        if (existsUser.isPresent()) {
            user = existsUser.get();
            user.setUsername(newUsername);
            this.utilisateurRepository.save(user);
            return true;
        }

        return false;
    }

    @Override
    public boolean updatePasswordofUtilisateur(String username, String oldPass, String newPass) {
        Optional<Utilisateur> existsUser = this.utilisateurRepository.findByUsername(username);
        Utilisateur user;
        if (existsUser.isPresent()) {
            user = existsUser.get();

            if (passwordEncoder.matches(oldPass, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(newPass));
                this.utilisateurRepository.save(user);
                return true;
            }
        }
        return false;
    }
}
