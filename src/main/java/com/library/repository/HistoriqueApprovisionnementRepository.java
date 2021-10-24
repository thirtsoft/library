package com.library.repository;

import com.library.entities.HistoriqueApprovisionnement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface HistoriqueApprovisionnementRepository extends JpaRepository<HistoriqueApprovisionnement, Long> {

    @Query("select count(f) from HistoriqueApprovisionnement f ")
    BigDecimal countNumberOfHistoriqueApprovisionnements();

    List<HistoriqueApprovisionnement> findByOrderByIdDesc();

}
