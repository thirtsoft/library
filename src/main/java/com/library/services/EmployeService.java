package com.library.services;

import com.library.entities.Employe;

import java.util.List;
import java.util.Optional;

public interface EmployeService {

    List<Employe> findAllEmploye();

    List<Employe> findAllEmployesOrderDesc();

    Employe saveEmploye(Employe employe);

    Optional<Employe> findEmployeById(Long empId);

    Employe updateEmploye(Long empId, Employe employe);

    void deleteEmploye(Long empId);

    Employe findByNom(String nom);

    Employe findByPrenom(String prenom);

    Employe findByCni(String cni);

    Employe findByTelephone(String telephone);

    Employe findByEmail(String email);

    List<Employe> ListEmployeByNom(String nom);

    List<Employe> ListEmployeByPrenom(String prenom);

    List<Employe> ListEmployeByCni(String cni);

    List<Employe> ListEmployeByEmail(String email);

    List<Employe> ListEmployeByTelephone(String telephone);


}
