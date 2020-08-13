package com.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.library.entities.Employe;

public interface EmployeService {
	
	public List<Employe> findAllEmploye();
	public Employe saveEmploye(Employe employe);
	public Optional<Employe> findEmployeById(Long empId);
	public Employe updateEmploye(Long empId, Employe employe);
	public ResponseEntity<Object> deleteEmploye(Long empId);
	
	public Employe findByNom(String nom);
	public Employe findByPrenom(String prenom);
	public Employe findByCni(String cni);
	public Employe findByEmail(String email);
	public Employe findByTelephone(String telephone);
	
	public List<Employe> ListEmployeByNom(String nom);
	public List<Employe> ListEmployeByPrenom(String prenom);
	public List<Employe> ListEmployeByCni(String cni);
	public List<Employe> ListEmployeByEmail(String email);
	public List<Employe> ListEmployeByTelephone(String telephone);
	
	public Page<Employe> findAllEmployeByPage(Pageable page);
	public Page<Employe> findEmployeByKeyWord(String mc, Pageable pageable);

}
