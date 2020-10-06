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

import com.library.entities.Client;
import com.library.entities.Contrat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ContratRepositoryTest {
	
	@Autowired
	private ContratRepository contratRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Test
	@Rollback(false)
	public void testCreateContrat() {
		
		Long clientId = (long) 1;
		Optional<Client> client = clientRepository.findById(clientId);
		
		Contrat contrat = new Contrat(null, "Cont1", "Prestation",1200, "Logiciel", new Date(), new Date(), client.get());
		
		Contrat saveContrat = contratRepository.save(contrat);
		
		assertNotNull(saveContrat);
	}
	
	@Test 
	public void testFindContratById() { 
		long contId = 3;
		boolean isContratExist = contratRepository.findById(contId).isPresent();
	  
		assertTrue(isContratExist);
	}
	
	@Test 
	public void testFindContratByReference() { 
		String reference = "Cont1";
		Contrat contrat = contratRepository.findByReference(reference);
	  
		assertThat(contrat.getReference()).isEqualTo(reference); 
	}
	
	@Test 
	@Rollback(false)
	public void testUpdateContrat() {
		String contratReference = "ContratZ";
		String contratNature = "Java Marketing";
		String contratDescription = "Marketing Digital";
		double montantContrat = 12000;
		
		Long clientId = (long) 2;
		Optional<Client> client = clientRepository.findById(clientId);
		Client emp = client.get();
		
		Contrat contrat = new Contrat(null, contratReference, contratNature, montantContrat,
				contratDescription, new Date(), new Date(), emp);
		
		contrat.setId((long) 3);
		contratRepository.save(contrat);
		
		Contrat contratUpdate = contratRepository.findByReference(contratReference);
		
		assertThat(contratUpdate.getReference()).isEqualTo(contratReference);
		
	}
	
	@Test
	@Rollback(false)
	public void testDeleteContrat() {
		Long id = (long) 5;
		
		boolean isExistBeforeDelete = contratRepository.findById(id).isPresent();
		
		contratRepository.deleteById(id);
		
		boolean notExistAfterDelete = contratRepository.findById(id).isPresent();
		
		assertTrue(isExistBeforeDelete);
		
		assertFalse(notExistAfterDelete);
	}
	
	@Test
	public void testListContrats() {
		List<Contrat> contrats = contratRepository.findAll();
		
		for (Contrat contrat: contrats) {
			System.out.println(contrat);
		}
		assertThat(contrats).size().isGreaterThan(0);
	}
	
	@Test 
	public void testFindListContratByClient() { 
		Long clientId = (long) 1;
		
		List<Contrat> contrats = contratRepository.findLitContratByClientId(clientId);
		List<Contrat> contratList = new ArrayList<Contrat>();
		
		for (Contrat contrat: contrats) {
			contratList.add(contrat);
		}
		assertThat(contratList.size()).isEqualTo(3);
	}
	
	@Test
	public void testListFindContratByReference() {
		String reference = "C";
		
		List<Contrat> contrats = contratRepository.findListContratByReference("%"+reference+"%");
		List<Contrat> contratList = new ArrayList<Contrat>();
		
		for (Contrat contrat: contrats) {
			contratList.add(contrat);
		}
		assertThat(contratList.size()).isBetween(1, 4);
			
	}
	
	@Test
	public void testListFindContratByNature() {
		String nature = "J";
		
		List<Contrat> contrats = contratRepository.findListContratByNature("%"+nature+"%");
		List<Contrat> contratList = new ArrayList<Contrat>();
		
		for (Contrat contrat: contrats) {
			contratList.add(contrat);
		}
		assertThat(contratList.size()).isBetween(1, 4);
			
	}
	

}
