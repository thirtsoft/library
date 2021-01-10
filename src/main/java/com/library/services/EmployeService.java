package com.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.library.entities.Employe;

public interface EmployeService {
	
	List<Employe> findAllEmploye();
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
	
	Page<Employe> findAllEmployeByPage(Pageable page);
	Page<Employe> findEmployeByKeyWord(String mc, Pageable pageable);

}
