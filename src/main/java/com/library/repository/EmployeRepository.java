package com.library.repository;

import com.library.entities.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {

    Employe findByEmail(String email);

    Employe findByCni(String cni);

    @Query("select c from Employe c where c.cni like :cni")
    List<Employe> ListEmployeByCni(@Param("cni") String cni);

    @Query("select c from Employe c where c.email like :mail")
    List<Employe> ListEmployeByEmail(@Param("mail") String email);

    @Query("select c from Employe c where c.nom like :name")
    Employe findByNom(@Param("name") String nom);

    @Query("select c from Employe c where c.nom like :name")
    List<Employe> ListEmployeByNom(@Param("name") String nom);

    @Query("select c from Employe c where c.prenom like :pren")
    Employe findByPrenom(@Param("pren") String prenom);

    @Query("select c from Employe c where c.prenom like :pren")
    List<Employe> ListEmployeByPrenom(@Param("pren") String prenom);

    @Query("select c from Employe c where c.telephone like :tel")
    Employe findByTelephone(@Param("tel") String telephone);

    @Query("select c from Employe c where c.telephone like :tel")
    List<Employe> ListEmployeByTelephone(@Param("tel") String telephone);


}
