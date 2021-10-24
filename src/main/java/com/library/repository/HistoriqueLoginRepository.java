package com.library.repository;

import com.library.entities.HistoriqueLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface HistoriqueLoginRepository extends JpaRepository<HistoriqueLogin, Long> {

    @Query("select count(f) from HistoriqueLogin f ")
    BigDecimal countNumberOfHistoriqueLogins();

    List<HistoriqueLogin> findByOrderByIdDesc();

}
