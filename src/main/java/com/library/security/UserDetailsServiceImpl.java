package com.library.security;


import com.library.entities.Utilisateur;
import com.library.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl /*implements UserDetailsService*/ {

    @Autowired
    private AccountService accountService;
    /*
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = accountService.findUtilisateurByUsername(username);

        if (utilisateur == null) throw new UsernameNotFoundException("Utilisateur existe");

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        utilisateur.getRoles().forEach(role-> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });

        return new User(utilisateur.getUsername(), utilisateur.getPassword(), authorities);
    }
    */
}
