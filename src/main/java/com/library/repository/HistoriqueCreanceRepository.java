package com.library.repository;

import com.library.entities.HistoriqueCreance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface HistoriqueCreanceRepository extends JpaRepository<HistoriqueCreance, Long> {

    @Query("select count(f) from HistoriqueCreance f ")
    BigDecimal countNumberOfHistoriqueCreances();

    List<HistoriqueCreance> findByOrderByIdDesc();

}
