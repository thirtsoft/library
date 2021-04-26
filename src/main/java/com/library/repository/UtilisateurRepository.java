package com.library.repository;

import com.library.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    @Query("select c from Utilisateur c where c.username like :username")
    List<Utilisateur> findListUtilisateurByUsername(@Param("username") String username);

    Optional<Utilisateur> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<Utilisateur> findByEmail(String email);


}
