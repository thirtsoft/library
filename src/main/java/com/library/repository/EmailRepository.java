package com.library.repository;

import com.library.entities.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

    @Query("select p from Email p where p.fournisseur.id =:four")
    Email EmailByFournisseurId(@Param("four") Long id);

}
