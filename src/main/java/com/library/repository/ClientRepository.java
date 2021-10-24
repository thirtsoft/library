package com.library.repository;

import com.library.entities.Charge;
import com.library.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByEmail(String email);

    Client findByTelephone(String telephone);

    Client findByCodeClient(String codeClient);

    @Query("select c from Client c where c.raisonSocial like :raisonSocial")
    Client findByRaisonSocial(@Param("raisonSocial") String raisonSocial);

    @Query("select c from Client c where c.raisonSocial like :raisonSocial")
    List<Client> ListClientByRaisonSocial(@Param("raisonSocial") String raisonSocial);

    @Query("select c from Client c where c.chefService like :chefService")
    Client findByChefService(@Param("chefService") String chefService);

    @Query("select c from Client c where c.chefService like :chefService")
    List<Client> ListClientByChefService(@Param("chefService") String chefService);

    @Query("select c.raisonSocial, count(c) as countClient from Client c group by c.raisonSocial")
    List<Object[]> ListClientGroupByRaisonSocial();

    List<Client> findByOrderByIdDesc();


}
