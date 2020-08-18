package com.library.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

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
import com.library.entities.Client;
import com.library.repository.ClientRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientServiceTest {
	
	@Autowired
	private ClientService clientService;
	
	@MockBean
	private ClientRepository clientRepository;
	
	@Test
	public void testCreateClient() {
		Client  client = new Client();
		client.setNom("CL22");
		client.setPrenom("PREEEEN");
		client.setAdresse("HHHHH");
		
		Mockito.when(clientRepository.save(client)).thenReturn(client);
		
		assertThat(clientService.saveClient(client)).isEqualTo(client);
	}
	
	@Test
	public void testFindClientByNom() {
		
		Client client = new Client(null, "Dieng", "Sidiya", "Mbao", "775643219", "seydou@gmail.com");
		
		when(clientRepository.findByNom(client.getNom())).thenReturn(client);
		
		Client clt = clientService.findByNom(client.getNom());
		
		assertNotNull(clt);
		assertThat(clt.getNom()).isEqualTo(client.getNom());
		
	}
	
	@Test
	public void testFindClientByPrenom() {
		
		Client client = new Client(null, "Dieng", "Sidiya", "Mbao", "775643219", "seydou@gmail.com");
		
		when(clientRepository.findByPrenom(client.getPrenom())).thenReturn(client);
		
		Client clt = clientService.findByPrenom(client.getPrenom());
		
		assertNotNull(clt);
		assertThat(clt.getPrenom()).isEqualTo(client.getPrenom());
		
	}
	
	
	@Test
	public void testAllClients() {
		when(clientRepository.findAll()).thenReturn(Stream
				.of(new Client(null, "CL", "CL","CL","CL","CL"), 
			new Client(null, "CL", "CL","CL","CL","CL")).collect(Collectors.toList()));
		assertEquals(2, clientService.findAllClient().size());
	}
	
	

}
