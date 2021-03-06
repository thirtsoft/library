package com.library.services.impl;

import com.library.entities.Role;
import com.library.entities.Utilisateur;
import com.library.repository.RoleRepository;
import com.library.repository.UtilisateurRepository;
import com.library.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    /*
        @Autowired
        private BCryptPasswordEncoder bCryptPasswordEncoder;
    */
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RoleRepository roleRepository;

    /*
        @Override
        public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
            String hashPw = bCryptPasswordEncoder.encode(utilisateur.getPassword());
            utilisateur.setPassword(hashPw);
            return utilisateurRepository.save(utilisateur);
        }
    */
    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUtilisateur(String username, String roleName) {

    }

    @Override
    public Utilisateur findUtilisateurByUsername(String username) {
        return null;
    }
}
