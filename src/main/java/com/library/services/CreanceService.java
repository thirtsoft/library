package com.library.services;

import com.library.entities.Creance;
import com.library.entities.Creance;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CreanceService {

    List<Creance> findAllCreances();

    List<Creance> findAllCreancesOrderDesc();

    Optional<Creance> findCreanceById(Long creanceId);

    Creance findByStatus(String status);

    List<Creance> findListCreanceByStatus(String status);

    Creance findByLibelle(String libelle);

    List<Creance> findListCreanceByLibelle(String libelle);

    List<Creance> findCreanceByClientId(Long clientId);

    int getNumberOfCreances();

    BigDecimal countNumbersOfCreances();

    Creance saveCreance(Creance creance);

    Creance updateCreance(Long id, Creance creance);

    void updateCreanceStatus(Long id, String status);

    void deleteCreance(Long id);

    Optional<Creance> findByReference(Long reference);

    boolean updateStatus(Long reference, String status);

    Optional<Creance> findByCodeCreance(String codeCreance);

    boolean updateStatusCreance(String codeCreance, String status);

    Creance setCreanceOnlyStatus(String status, String id);

    Creance setCreanceOnlyAvanceCreance(double avanceCreance, String id);

    long generateReferenceCreance();

    List<?> sumTotalOfCreancesByMonth();

}
