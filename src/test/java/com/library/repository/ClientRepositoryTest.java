package com.library.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.library.entities.Client;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ClientRepositoryTest {
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Test
	@Rollback(false)
	public void testCreateClient() {
		Client client = new Client(null, "Dieng", "Sidiya", "Mbao", "775643219", "seydou@gmail.com");
		
		Client saveClient = clientRepository.save(client);
		
		assertNotNull(saveClient);
	}
	
	@Test 
	public void testFindClientById() { 
		long clientId = 2;
		boolean isClientExist = clientRepository.findById(clientId).isPresent();
	  
		assertTrue(isClientExist);
	}
	
	@Test 
	public void testFindClientByNomNotExist() { 
		String nom = "Ndiaye";
		Client client = clientRepository.findByNom(nom);
	  
		assertNull(client); 
	}
	
	@Test 
	public void testFindClientByPrenom() { 
		String prenom = "Saliou";
		Client client = clientRepository.findByPrenom(prenom);
	  
		assertNull(client); 
	}
	
	@Test 
	@Rollback(false)
	public void testUpdateClient() {
		String clientNom = "Wade";
		String clientPrenom = "Wade";
		String clientAdress = "DK";
		String clientEmail = "adama@gmail.com";
		
		Client client = new Client(null, clientNom, clientPrenom, clientAdress, 
				"775643219", clientEmail);
		
		client.setId((long) 11);
		clientRepository.save(client);
		
		Client clientUpdate = clientRepository.findByNom(clientNom);
		Client clientUpdate1 = clientRepository.findByPrenom(clientPrenom);
		
		assertThat(clientUpdate.getNom()).isEqualTo(clientNom);
		assertThat(clientUpdate1.getPrenom()).isEqualTo(clientPrenom);
	
	}
	
	@Test
	@Rollback(false)
	public void testDeleteClient() {
		Long id = (long) 15;
		
		boolean isExistBeforeDelete = clientRepository.findById(id).isPresent();
		
		clientRepository.deleteById(id);
		
		boolean notExistAfterDelete = clientRepository.findById(id).isPresent();
		
		assertTrue(isExistBeforeDelete);
		
		assertFalse(notExistAfterDelete);
	}
	
	@Test
	public void testListClients() {
		List<Client> clients = clientRepository.findAll();
		
		for (Client client: clients) {
			System.out.println(client);
		}
		assertThat(clients).size().isGreaterThan(0);
	}
	
	@Test
	public void testListFindClientByNom() {
		String nom = "CL";
		
		List<Client> clients = clientRepository.ListClientByNom("%"+nom+"%");
		List<Client> clientList = new ArrayList<Client>();
		
		for (Client client: clients) {
			clientList.add(client);
		}
		assertThat(clientList.size()).isBetween(1, 4);
		//assertThat(clientList.size()).isGreaterThan(0);
			
	}
	
	@Test
	public void testListFindClientByPrenom() {
		String prenom = "M";
		
		List<Client> clients = clientRepository.ListClientByPrenom("%"+prenom+"%");
		List<Client> clientList = new ArrayList<Client>();
		
		for (Client client: clients) {
			clientList.add(client);
		}
		//assertThat(categoriesList.size()).isEqualTo(2);
		assertThat(clientList.size()).isGreaterThan(0);
			
	}
	
	
	

}
