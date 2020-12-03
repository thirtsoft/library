package com.library.services;
import com.library.entities.Creance;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CreanceService {

    List<Creance> findAllCreances();
    Optional<Creance> findCreanceById(Long creanceId);

    Creance findByReference(int reference);
    List<Creance> findListCreanceByReference(int reference);
    Creance findByStatus(String status);
    List<Creance> findListCreanceByStatus(String status);

    Creance findByLibelle(String libelle);
    List<Creance> findListCreanceByLibelle(String libelle);


    List<Creance> findCreanceByClientId(Long clientId);

    int getNumberOfCreances();

    BigDecimal countNumbersOfCreances();

    Creance saveCreance(Creance creance);
    Creance updateCreance(Long id, Creance creance);


    void deleteCreance(Long id);


}
