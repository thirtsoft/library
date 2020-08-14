package com.library.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.library.entities.Employe;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class EmployeRepositoryTest {
	
	@Autowired
	private EmployeRepository  employeRepository;
	
	@Test
	@Rollback(false)
	public void testCreateEmploye() {
		Employe employe = new Employe(null, "Diallo", "Amadu", "1989d", "B", "776543219", "ad@gmail.com");
		
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
		String empNom = "Wade";
		String empPrenom = "Wade";
		String empEmail = "adama@gmail.com";
		
		Employe employe = new Employe(null, empNom, empPrenom, 
				empCni, "DK", "776543219", empEmail);

		
		employe.setId((long) 4);
		employeRepository.save(employe);
		
		Employe employeUpdate = employeRepository.findByNom(empNom);
		Employe employeUpdate1 = employeRepository.findByPrenom(empPrenom);
		
		assertThat(employeUpdate.getNom()).isEqualTo(empNom);
		assertThat(employeUpdate1.getPrenom()).isEqualTo(empPrenom);
	
	}
	
	@Test
	@Rollback(false)
	public void testDeleteEmploye() {
		Long id = (long) 7;
		
		boolean isExistBeforeDelete = employeRepository.findById(id).isPresent();
		
		employeRepository.deleteById(id);
		
		boolean notExistAfterDelete = employeRepository.findById(id).isPresent();
		
		assertTrue(isExistBeforeDelete);
		
		assertFalse(notExistAfterDelete);
	}
	

}
