package com.library.repository;

import com.library.entities.HistoriqueCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface HistoriqueCommandeRepository extends JpaRepository<HistoriqueCommande, Long> {

    @Query("select count(f) from HistoriqueCommande f ")
    BigDecimal countNumberOfHistoriqueCommandes();

    List<HistoriqueCommande> findByOrderByIdDesc();

}
