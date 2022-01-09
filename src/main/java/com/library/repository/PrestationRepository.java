package com.library.repository;

import com.library.entities.Prestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PrestationRepository extends JpaRepository<Prestation, Long> {

    @Query("select sum(p.montant) from Prestation p where month(p.datePrestation) = month(current_date)")
    BigDecimal sumTotalOfPrestationByMonth();

    @Query("select sum(v.montant) from Prestation v where year(v.datePrestation) = year(current_date)")
    BigDecimal sumTotalOfPrestationByYear();

    List<Prestation> findByOrderByIdDesc();
}
