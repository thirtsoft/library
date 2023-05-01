package com.library.repository;

import com.library.entities.Employe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class EmployeRepositoryTest {
	
	@Autowired
	private EmployeRepository  employeRepository;
	
	@Test
	@Rollback(false)
	public void testCreateEmploye() {
		Employe employe = new Employe(null, "Diallo", "Saliou Woury", "1989d", "B", "776543219","776543219", "ad@gmail.com");
		
		Employe saveEmploye = employeRepository.save(employe);
		
		assertNotNull(saveEmploye);
	}
	
	@Test 
	public void testFindEmployeById() { 
		long empId = 3;
		boolean isEmployeExist = employeRepository.findById(empId).isPresent();
	  
		assertTrue(isEmployeExist);
	}
	
	@Test 
	public void testFindClientByCniNotExist() { 
		String cni = "199100078";
		Employe employe = employeRepository.findByCni(cni);
	  
		assertNull(employe); 
	}
	
	@Test 
	public void testFindEmployeByNom() { 
		String nom = "emp1";
		Employe employe = employeRepository.findByNom(nom);
	  
		assertNotNull(employe); 
	}
	
	@Test 
	public void testFindEmployeByEmail() { 
		String email = "thirdiallo@gmail.com";
		Employe isEmailExist = employeRepository.findByEmail(email);
	  
		assertNull(isEmailExist);
	}
	
	@Test 
	@Rollback(false)
	public void testUpdateEmploye() {
		String empCni = "Wade2010";
		String empNom = "Thiam";
		String empPrenom = "Fanta";
		String empEmail = "masterou@gmail.com";
		
		Employe employe = new Employe(null, empNom, empPrenom, 
				empCni, "DK", "776543219","776543219", empEmail);

		
		employe.setId((long) 5);
		employeRepository.save(employe);
		
		Employe employeUpdate = employeRepository.findByNom(empNom);
		Employe employeUpdate1 = employeRepository.findByPrenom(empPrenom);
		
		assertThat(employeUpdate.getNom()).isEqualTo(empNom);
		assertThat(employeUpdate1.getPrenom()).isEqualTo(empPrenom);
	
	}
	
	@Test
	@Rollback(false)
	public void testDeleteEmploye() {
		Long id = (long) 10;
		
		boolean isExistBeforeDelete = employeRepository.findById(id).isPresent();
		
		employeRepository.deleteById(id);
		
		boolean notExistAfterDelete = employeRepository.findById(id).isPresent();
		
		assertTrue(isExistBeforeDelete);
		
		assertFalse(notExistAfterDelete);
	}
	
	@Test
	public void testListEmployes() {
		List<Employe> employes = employeRepository.findAll();
		
		for (Employe employe: employes) {
			System.out.println(employe);
		}
		assertThat(employes).size().isGreaterThan(0);
	}
	
	@Test
	public void testListFindEmployeByEmail() {
		String email = "fallou@gmail.com";
		
		List<Employe> employes = employeRepository.ListEmployeByEmail("%"+email+"%");
		List<Employe> employeList = new ArrayList<Employe>();
		
		for (Employe employe: employes) {
			employeList.add(employe);
		}
		assertThat(employeList.size()).isBetween(1, 3);
			
	}
	
	@Test
	public void testListFindEmployeByNom() {
		String nom = "Wa";
		
		List<Employe> employes = employeRepository.ListEmployeByNom("%"+nom+"%");
		List<Employe> employeList = new ArrayList<Employe>();
		
		for (Employe employe: employes) {
			employeList.add(employe);
		}
		assertThat(employeList.size()).isEqualTo(1);
		
	}
	
	@Test
	public void testListFindEmployeByPrenom() {
		String prenom = "t";
		
		List<Employe> employes = employeRepository.ListEmployeByPrenom("%"+prenom+"%");
		List<Employe> employeList = new ArrayList<Employe>();
		
		for (Employe employe: employes) {
			employeList.add(employe);
		}
		
		assertThat(employeList.size()).isGreaterThan(0);
			
	}
	

}
