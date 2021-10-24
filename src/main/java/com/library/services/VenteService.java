package com.library.services;

import com.library.entities.Vente;
import com.library.entities.Vente;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface VenteService {

    List<Vente> findAllVentes();

    List<Vente> findAllVentesOrderDesc();

    Optional<Vente> findVenteById(Long venteId);

    Vente saveVente(Vente vente);

    Vente saveVenteWithBarcode(Vente vente);

    Vente updateVente(Long venteId, Vente vente);

    ResponseEntity<Object> deleteVenteClient(Long id);

    int getNombreVentes(Date d1, Date d2);

    int getNumberOfVente();

    long generateNumeroVente();

    BigDecimal countSumsOfVentess();

    //   .. Vente findVenteByNumeroVente(long numeroVente);
    Vente findVenteByNumeroVente(Long numeroVente);

    Vente findByStatus(String status);

    void deleteVente(Long id);

    BigDecimal sumTotalOfVenteByDay();

    BigDecimal sumTotalOfVentesByMonth();

    BigDecimal sumTotalOfVentesByYear();

    Integer countNumberOfVenteByDay();

    List<Vente> findVenteWithParticularDayAndMonth();

    List<?> countNumberTotalOfVenteByMonth();

    List<?> sumTotalOfVenteByMonth();

    List<?> sumTotalOfVenteByYears();

    List<Vente> findListVenteByEmployeId(Long empId);

}
