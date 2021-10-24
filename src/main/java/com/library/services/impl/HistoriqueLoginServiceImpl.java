package com.library.services.impl;

import com.library.entities.HistoriqueLogin;
import com.library.entities.Utilisateur;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.HistoriqueLoginRepository;
import com.library.services.HistoriqueLoginService;
import com.library.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HistoriqueLoginServiceImpl implements HistoriqueLoginService {

    private final HistoriqueLoginRepository historiqueLoginRepository;

    private final UtilisateurService utilisateurService;

    @Autowired
    public HistoriqueLoginServiceImpl(HistoriqueLoginRepository historiqueLoginRepository,
                                      UtilisateurService utilisateurService) {
        this.historiqueLoginRepository = historiqueLoginRepository;
        this.utilisateurService = utilisateurService;
    }

    @Override
    public HistoriqueLogin saveHistoriqueLogin(HistoriqueLogin historiqueLogin) {
        return historiqueLoginRepository.save(historiqueLogin);
    }

    @Override
    public HistoriqueLogin saveHistoriqueLoginWithConnectedUser(HistoriqueLogin historiqueLogin, Long userId) {

        Optional<Utilisateur> optionalUtilisateur = utilisateurService.findUtilisateurById(userId);
        Utilisateur utilisateur = optionalUtilisateur.get();

        historiqueLogin.setUtilisateur(utilisateur);
        historiqueLogin.setCreatedDate(new Date());

        return historiqueLoginRepository.save(historiqueLogin);
    }

    @Override
    public Optional<HistoriqueLogin> findHistoriqueLoginById(Long id) {
        if (!historiqueLoginRepository.existsById(id)) {
            throw new ResourceNotFoundException("HistoriqueLogin that id is" + id + "not found");
        }
        return historiqueLoginRepository.findById(id);
    }

    @Override
    public List<HistoriqueLogin> findAllHistoriqueLogins() {
        return historiqueLoginRepository.findAll();
    }

    @Override
    public List<HistoriqueLogin> findAllHistoriqueLoginsOrderDesc() {
        return historiqueLoginRepository.findByOrderByIdDesc();
    }

    @Override
    public BigDecimal countNumberOfHistoriqueLogins() {
        return historiqueLoginRepository.countNumberOfHistoriqueLogins();
    }

    @Override
    public void deleteHistoriqueLogin(Long id) {
        if (!historiqueLoginRepository.existsById(id)) {
            throw new ResourceNotFoundException("HistoriqueLogin that id is" + id + "not found");
        }
        historiqueLoginRepository.deleteById(id);
    }
}
