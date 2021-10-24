package com.library.repository;

import com.library.entities.HistoriqueVente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface HistoriqueVenteRepository extends JpaRepository<HistoriqueVente, Long> {

    @Query("select count(f) from HistoriqueVente f ")
    BigDecimal countNumberOfHistoriqueVentes();

    List<HistoriqueVente> findByOrderByIdDesc();

}
