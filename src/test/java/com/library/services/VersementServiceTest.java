package com.library.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.library.entities.Category;
import com.library.entities.Employe;
import com.library.entities.Produit;
import com.library.entities.Versement;
import com.library.repository.EmployeRepository;
import com.library.repository.VersementRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class VersementServiceTest {
	
	@Autowired
	private VersementService versementService;
	
	@MockBean
	private VersementRepository versementRepository;
	
	@MockBean
	private EmployeRepository employeRepository;
	
	
	@Test
	public void testCreateVersement() {
		Versement versement = new Versement();
		
		versement.setNumVersement("VER222");
		versement.setNature("VERVERS");
		versement.setMontantVersement(250000.0);
		
		Mockito.when(versementRepository.save(versement)).thenReturn(versement);
		
		assertThat(versementService.saveVersement(versement)).isEqualTo(versement);
	}
	
	@Test
	public void testFindVersementByNumVersement() {
		
		Employe employe = new Employe(null, "Diallo", "Saliou Woury", "1989d", "B", "776543219","776543219", "ad@gmail.com");

		Versement versement = new Versement(null, "Vers08", "Casse", "Recu","BCEAO", 2000000.0, "Ver", new Date(), employe);
		
		when(versementRepository.findByNumVersement(versement.getNumVersement())).thenReturn(versement);
		
		Versement vers = versementService.findByNumVersement(versement.getNumVersement());
		
		assertNotNull(vers);
		assertThat(vers.getNumVersement()).isEqualTo(versement.getNumVersement());
		
	}
	
	@Test
	public void testFindVersementByNature() {
		
		Employe employe = new Employe(null, "Diallo", "Saliou Woury", "1989d", "B", "776543219","776543219", "ad@gmail.com");

		Versement versement = new Versement(null, "Vers08", "Casse", "Recu","BCEAO", 2000000.0, "Ver", new Date(), employe);
		
		when(versementRepository.findByNature(versement.getNature())).thenReturn(versement);
		
		Versement vers = versementService.findByNature(versement.getNature());
		
		assertNotNull(vers);
		assertThat(vers.getNature()).isEqualTo(versement.getNature());
		
	}
	
	
	@Test
	public void testfindAllVersements() {
		Employe emp = new Employe(null, "Emp", "Emp","Empl","Emp","Emp","Emp","Emp"); 
		when(versementRepository.findAll()).thenReturn(Stream
				.of(new Versement(null, "Vers08", "Casse", "Recu","BCEAO", 2000000.0, "Ver", new Date(), emp),
		new Versement(null, "Vers08", "Casse", "Recu","BCEAO", 2000000.0, "Ver", new Date(), emp)).collect(Collectors.toList()));
		assertEquals(2, versementService.findAllVersements().size());
	}
	
	

}
