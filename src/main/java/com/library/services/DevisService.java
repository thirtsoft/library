package com.library.services;


import com.library.entities.Devis;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DevisService {

    List<Devis> findAllDevis();

    Optional<Devis> findDevisById(Long devId);

    Devis saveDevis(Devis devis);

    Devis updateDevis(Long devId, Devis devis);

    void deleteDevis(Long id);

    int getNombreDevis(Date d1, Date d2);

    int getNumberOfDevis();

    BigDecimal countNumbersOfDevis();

  //  Devis findByNumeroDevis(long numeroDevis);
    Devis findByNumeroDevis(Long numeroDevis);

    List<Devis> findDevisByClientId(Long clientId);

    List<Devis> findDevisByDate(Date dateDevis);

    List<?> countNumberTotalOfDevisByMonth();

    List<?> sumTotalOfDevisByMonth();

    long generateNumeroDevis();

}
