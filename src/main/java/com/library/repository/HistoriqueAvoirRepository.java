package com.library.repository;

import com.library.entities.HistoriqueAvoir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface HistoriqueAvoirRepository extends JpaRepository<HistoriqueAvoir, Long> {

    @Query("select count(f) from HistoriqueAvoir f ")
    BigDecimal countNumberOfHistoriqueAvoirs();

    List<HistoriqueAvoir> findByOrderByIdDesc();

}
