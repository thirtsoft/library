package com.library.services.impl;

import com.library.entities.Role;
import com.library.entities.Utilisateur;
import com.library.enumeration.RoleName;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.RoleRepository;
import com.library.repository.UtilisateurRepository;
import com.library.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public List<Utilisateur> findAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    @Override
    public List<Utilisateur> findAllUtilisateursOrderDesc() {
        return utilisateurRepository.findByOrderByIdDesc();
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
        Optional<Utilisateur> existUser = utilisateurRepository.findById(idUser);
        if (!existUser.isPresent()) {
            throw new ResourceNotFoundException("User Not found");
        }
        Utilisateur userResult = existUser.get();

        userResult.setUsername(utilisateur.getUsername());
        userResult.setEmail(utilisateur.getEmail());
        userResult.setName(utilisateur.getName());
        userResult.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        userResult.setActive(utilisateur.isActive());

        return utilisateurRepository.save(userResult);
    }

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

    @Override
    public void addRoleToUser(String username, RoleName roleName) {
        Role role = roleRepository.findByName(roleName).get();
        Utilisateur utilisateur = utilisateurRepository.findByUsername(username).get();
        utilisateur.getRoles().add(role);
    }

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

    @Override
    public Utilisateur activatedUser(String isActive, String id) {
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(Long.valueOf(id));
        Utilisateur utilisateur = optionalUtilisateur.get();
        utilisateur.setActive(Boolean.valueOf(isActive));
        return utilisateurRepository.save(utilisateur);
    }

    /**
     * @return a list of all the authorities
     */
    public List<String> findAuthorities() {
        //  return roleRepository.findAll().stream().map(Role::getName).collect(Collectors.toList());
        return null;
    }
}
