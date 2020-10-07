package com.library.repository;

import com.library.entities.Charge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargeRepository extends JpaRepository<Charge, Long> {

    @Query("select p from Charge p where p.codeCharge like :code")
    public Charge findByCodeCharge(@Param("code") String codeCharge);

    @Query("select p from Charge p where p.codeCharge like :code")
    public List<Charge> findListCodeCharge(@Param("code") String codeCharge);


    @Query("select p from Charge p where p.nature like :nat")
    public Charge findByNature(@Param("nat") String nature);

    @Query("select p from Charge p where p.nature like :nat")
    public List<Charge> findListNature(@Param("nat") String nature);

    @Query("select p from Charge p")
    public Page<Charge> findAllChargesByPageable(Pageable pageable);

    @Query("select p from Charge p where p.nature like :mc")
    public Page<Charge> findChargesByKeyWord(@Param("mc") String mc, Pageable pageable);

}
