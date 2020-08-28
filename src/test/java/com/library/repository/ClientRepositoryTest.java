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
	public void testFindClientByRaisonSocialNotExist() { 
		String raisonSocial = "Ndiaye";
		Client client = clientRepository.findByRaisonSocial(raisonSocial);
	  
		assertNull(client); 
	}
	
	@Test 
	public void testFindClientByChefService() { 
		String chefService = "Saliou";
		Client client = clientRepository.findByChefService(chefService);
	  
		assertNull(client); 
	}
	
	@Test 
	@Rollback(false)
	public void testUpdateClient() {
		String clientRaisonSocial = "Wade";
		String clientChefService = "Wade";
		String clientAdress = "DK";
		String clientEmail = "adama@gmail.com";
		
		Client client = new Client(null, clientRaisonSocial, clientChefService, clientAdress, 
				"775643219", clientEmail);
		
		client.setId((long) 11);
		clientRepository.save(client);
		
		Client clientUpdate = clientRepository.findByRaisonSocial(clientRaisonSocial);
		Client clientUpdate1 = clientRepository.findByChefService(clientChefService);
		
		assertThat(clientUpdate.getRaisonSocial()).isEqualTo(clientRaisonSocial);
		assertThat(clientUpdate1.getChefService()).isEqualTo(clientChefService);
	
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
	public void testListFindClientByRaisonSocial() {
		String raisonSocial = "CL";
		
		List<Client> clients = clientRepository.ListClientByRaisonSocial("%"+raisonSocial+"%");
		List<Client> clientList = new ArrayList<Client>();
		
		for (Client client: clients) {
			clientList.add(client);
		}
		assertThat(clientList.size()).isBetween(1, 4);
		//assertThat(clientList.size()).isGreaterThan(0);
			
	}
	
	@Test
	public void testListFindClientByChefService() {
		String chefService = "M";
		
		List<Client> clients = clientRepository.ListClientByChefService("%"+chefService+"%");
		List<Client> clientList = new ArrayList<Client>();
		
		for (Client client: clients) {
			clientList.add(client);
		}
		//assertThat(categoriesList.size()).isEqualTo(2);
		assertThat(clientList.size()).isGreaterThan(0);
			
	}
	
	
	

}
