package com.library.services.impl;

import com.library.entities.Role;
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

    public AccountServiceImpl(UtilisateurRepository utilisateurRepository, RoleRepository roleRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;
    }

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
/*
    @Override
    public void addRoleToUtilisateur(String username, String roleName) {
        Role role = roleRepository.findByRoleName(roleName);
        Utilisateur utilisateur = utilisateurRepository.findByUsername(username);
        utilisateur.getRoles().add(role);

    }
*/

}
