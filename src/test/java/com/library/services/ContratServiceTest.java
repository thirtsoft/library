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

import com.library.entities.Client;
import com.library.entities.Contrat;
import com.library.repository.ClientRepository;
import com.library.repository.ContratRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ContratServiceTest {
	
	@Autowired
	private ContratService contratService;
	
	@MockBean
	private ContratRepository contratRepository;
	
	@MockBean
	private ClientRepository clientRepository;
	
	
	@Test
	public void testCreateContrat() {
		Contrat contrat = new Contrat();
		contrat.setReference("CONT11");
		contrat.setNature("NatCONT");
		contrat.setDescription("DESCCONT");
		
		
		Mockito.when(contratRepository.save(contrat)).thenReturn(contrat);
		
		assertThat(contratService.saveContrat(contrat)).isEqualTo(contrat);
	}
	
	@Test
	public void testFindContratByReference() {
		
		Client client = new Client(null, "CL", "CL","CL","CL","CL"); 
		
		Contrat contrat = new Contrat(null, "Cont1", "Prestation", "Logiciel", new Date(), client);
		
		when(contratRepository.findByReference(contrat.getReference())).thenReturn(contrat);
		
		Contrat ctrt = contratService.findByReference(contrat.getReference());
		
		assertNotNull(ctrt);
		assertThat(ctrt.getReference()).isEqualTo(contrat.getReference());
		
	}
	
	@Test
	public void testFindContratByNature() {
		
		Client client = new Client(null, "CL", "CL","CL","CL","CL"); 
		
		Contrat contrat = new Contrat(null, "Cont1", "Prestation", "Logiciel", new Date(), client);
		
		when(contratRepository.findByNature(contrat.getNature())).thenReturn(contrat);
		
		Contrat ctrt = contratService.findByNature(contrat.getNature());
		
		assertNotNull(ctrt);
		assertThat(ctrt.getNature()).isEqualTo(contrat.getNature());
		
	}
	
	
	@Test
	public void testfindAllContrats() {
		Client client = new Client(null, "CL", "CL","CL","CL","CL"); 
		when(contratRepository.findAll()).thenReturn(Stream
				.of(new Contrat(null, "CONT22", "CONT22", "CONT22", new Date(), client), 
		new Contrat(null, "CONT33", "CONT33", "CONT33", new Date(), client)).collect(Collectors.toList()));
		assertEquals(2, contratService.findAllContrats().size());
	}
	
	

}
