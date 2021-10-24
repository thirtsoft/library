package com.library.repository;

import com.library.entities.HistoriqueCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface HistoriqueChargeRepository extends JpaRepository<HistoriqueCharge, Long> {

    @Query("select count(f) from HistoriqueCharge f ")
    BigDecimal countNumberOfHistoriqueCharges();

    List<HistoriqueCharge> findByOrderByIdDesc();

}
