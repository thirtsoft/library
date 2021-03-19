package com.library.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.library.entities.Employe;
import com.library.entities.Versement;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class VersementRepositoryTest {
	
	@Autowired
	private VersementRepository versementRepository;
	
	@Autowired
	private EmployeRepository employeRepository;
	
	
	@Test
	@Rollback(false)
	public void testCreateVersement() {
		Long empId = (long) 3;
		Optional<Employe> employe = employeRepository.findById(empId);
		
		Versement versement = new Versement(null, "Vers08", "Casse", "Recu","BCEAO", 2000000.0, "Ver", "ver_1.png", new Date(), employe.get());
		
		Versement saveVersement = versementRepository.save(versement);
		
		assertNotNull(saveVersement);
	}
	
	@Test 
	public void testFindVersementById() { 
		long verId = 3;
		boolean isVersementExist = versementRepository.findById(verId).isPresent();
	  
		assertTrue(isVersementExist);
	}
	
	@Test 
	public void testFindVersementByNumVersement() { 
		String numVersement = "BANK1";
		Versement versement = versementRepository.findByNumVersement(numVersement);
	  
		assertThat(versement.getNumVersement()).isEqualTo(numVersement); 
	}
	
	@Test 
	public void testFindVersementByNature() { 
		String nature = "Depot";
		Versement versement = versementRepository.findByNature(nature);
	  
		assertThat(versement.getNature()).isEqualTo(nature); 
	}
	
	@Test 
	@Rollback(false)
	public void testUpdateVersement() {
		String verNumVersement = "BCEAO";
		String verNature = "BCEAO";
		String verRecu = "Recu";
		String verBank = "BCEAO";
		Double verMontant = 5000000.0;
		String verMotif = "Ver";
		String verFile = "Ver_2.jpg";
		
		Long empId = (long) 5;
		Optional<Employe> employe = employeRepository.findById(empId);
		Employe emp = employe.get();
		
		Versement versement = new Versement(null, verNumVersement, verNature, verRecu, verBank,
				verMontant, verMotif, verFile, new Date(), emp);
		
		versement.setId((long) 4);
		versementRepository.save(versement);
		
		Versement versementUpdate = versementRepository.findByNumVersement(verNumVersement);
		
		assertThat(versementUpdate.getNumVersement()).isEqualTo(verNumVersement);
		
	}
	
	@Test
	@Rollback(false)
	public void testDeleteVersement() {
		Long id = (long) 8;
		
		boolean isExistBeforeDelete = versementRepository.findById(id).isPresent();
		
		versementRepository.deleteById(id);
		
		boolean notExistAfterDelete = versementRepository.findById(id).isPresent();
		
		assertTrue(isExistBeforeDelete);
		
		assertFalse(notExistAfterDelete);
	}
	
	@Test
	public void testListVersements() {
		List<Versement> versements = versementRepository.findAll();
		
		for (Versement versement: versements) {
			System.out.println(versement);
		}
		assertThat(versements).size().isGreaterThan(0);
	}
	
	@Test 
	public void testFindListVersementByEmploye() { 
		Long empId = (long) 1;
		
		List<Versement> versements = versementRepository.findVersementByEmployeId(empId);
		List<Versement> versementList = new ArrayList<Versement>();
		
		for (Versement versement: versements) {
			versementList.add(versement);
		}
		assertThat(versementList.size()).isEqualTo(2);
	}
	
	@Test
	public void testListFindVersementByNumVersement() {
		String numVersement = "Ver";
		
		List<Versement> versements = versementRepository.findListVersementByNumVersement("%"+numVersement+"%");
		List<Versement> versementList = new ArrayList<Versement>();
		
		for (Versement versement: versements) {
			versementList.add(versement);
		}
		assertThat(versementList.size()).isBetween(1, 4);
			
	}

	

}
