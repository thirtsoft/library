package com.library.repository;

import com.library.entities.Charge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ChargeRepository extends JpaRepository<Charge, Long> {

    Charge findByCodeCharge(String codeCharge);

    @Query("select p from Charge p where p.codeCharge like :code")
    List<Charge> findListCodeCharge(@Param("code") String codeCharge);

    @Query("select p from Charge p where p.nature like :nat")
    Charge findByNature(@Param("nat") String nature);

    @Query("select sum(c.montantCharge) from Charge c where year(c.date) = year(current_date)")
    BigDecimal sumTotalOfChargeByYear();

    @Query("select p from Charge p where p.nature like :nat")
    List<Charge> findListNature(@Param("nat") String nature);

    @Query("select EXTRACT(month from(v.date)), sum(v.montantCharge) from Charge v group by EXTRACT(month from(v.date))")
    List<?> sumTotalOfChargeByMonth();

    List<Charge> findByOrderByIdDesc();


}
