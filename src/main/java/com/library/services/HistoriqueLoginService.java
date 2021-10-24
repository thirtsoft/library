package com.library.services;

import com.library.entities.HistoriqueLogin;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface HistoriqueLoginService {

    HistoriqueLogin saveHistoriqueLogin(HistoriqueLogin historiqueLogin);

    HistoriqueLogin saveHistoriqueLoginWithConnectedUser(HistoriqueLogin historiqueLogin, Long userId);

    Optional<HistoriqueLogin> findHistoriqueLoginById(Long id);

    List<HistoriqueLogin> findAllHistoriqueLogins();

    List<HistoriqueLogin> findAllHistoriqueLoginsOrderDesc();

    BigDecimal countNumberOfHistoriqueLogins();

    void deleteHistoriqueLogin(Long id);

}
