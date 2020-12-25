package com.library.services;

import com.library.entities.Vente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface VenteService {

    List<Vente> findAllVentes();
    Optional<Vente> findVenteById(Long venteId);
    Vente saveVente(Vente vente);

    Vente updateVente(Long venteId, Vente vente);
    ResponseEntity<Object> deleteVenteClient(Long id);

    int getNombreVentes(Date d1, Date d2);
    int getNumberOfVente();

    BigDecimal countSumsOfVentess();

    Vente findVenteByNumeroVente(int numeroVente);
    Vente findByStatus(String status);

    /*
    List<Vente> findListVenteByNumeroVente(String numeroVente);
*/
    Page<Vente> findAllVenteByPageable(Pageable pageable);

    Page<Vente> findVenteByKeyWord(String mc, Pageable pageable);

    void deleteVente(Long id);

    List<Vente> findVenteWithParticularDayAndMonth();

    BigDecimal sumTotalOfVenteByDay();

    Integer countNumberOfVenteByDay();

    List<?> countNumberTotalOfVenteByMonth();
    List<?> sumTotalOfVenteByMonth();

}
