package com.library.services;

import com.library.entities.Prestation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PrestationService {

    Prestation savePrestation(Prestation prestation);

    Prestation updatePrestation(Long prestId, Prestation prestation);

    Optional<Prestation> findPrestationById(Long prestId);

    List<Prestation> findAllPrestations();

    List<Prestation> findAllPrestationsOrderDesc();

    BigDecimal sumTotalOfPrestationByMonth();

    BigDecimal sumTotalOfPrestationByYear();

    void deletePrestation(Long prestId);

}
