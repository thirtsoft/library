package com.library.services.impl;

import java.util.List;
import java.util.Optional;

import com.library.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.library.entities.Client;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.ClientRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {
	
	@Autowired
	private ClientRepository clientRepository;

	@Override
	public List<Client> findAllClient() {
		return clientRepository.findAll();
	}

	@Override
	public Optional<Client> findClientById(Long id) {
		if (!clientRepository.existsById(id)) {
			throw new ResourceNotFoundException("Client N° " + id + "not found");
		}
		return clientRepository.findById(id);
	}

	@Override
	public Client findByRaisonSocial(String raisonSocial) {
		return clientRepository.findByRaisonSocial(raisonSocial);
	}

	@Override
	public Client findByChefService(String chefService) {
		return clientRepository.findByChefService(chefService);
	}

	@Override
	public List<Client> ListClientByRaisonSocial(String raisonSocial) {
		return clientRepository.ListClientByRaisonSocial(raisonSocial);
	}

	@Override
	public List<Client> ListClientByChefService(String chefService) {
		return clientRepository.ListClientByChefService(chefService);
	}

	@Override
	public List<Object[]> ListClientGroupByRaisonSocial() {
		return clientRepository.ListClientGroupByRaisonSocial();
	}

	@Override
	public Page<Client> findAllClientByPage(Pageable page) {
		return clientRepository.findClientByPageable(page);
	}

	@Override
	public Page<Client> findClientByKeyWord(String mc, Pageable pageable) {
		return clientRepository.findClientByKeyWord(mc, pageable);
	}

	@Override
	public Long countNumberOfClient() {
		return clientRepository.count();
	}

	@Override
	public Client updateClientByEmail(String email, String id) {
		Optional<Client> originalClient = clientRepository.findById(Long.valueOf(id));
		Client client = originalClient.get();
		client.setEmail(email);
		return clientRepository.save(client);
	}

	@Override
	public Client saveClient(Client client) {
		return clientRepository.save(client);
	}
	
	@Override
	public Client updateClient(Long id, Client client) {
		if (!clientRepository.existsById(id)) {
			throw new ResourceNotFoundException("Client N° " + id + "nout found");
		}
		Optional<Client> clt = clientRepository.findById(id);
		if (!clt.isPresent()) {
			throw new ResourceNotFoundException("Client N° " + id + "not found");
		}
		Client clientResult = clt.get();
		clientResult.setRaisonSocial(client.getRaisonSocial());
		clientResult.setChefService(client.getChefService());
		clientResult.setAdresse(client.getAdresse());
		clientResult.setTelephone(client.getTelephone());
		clientResult.setEmail(client.getEmail());
		
		return clientRepository.save(clientResult);
	}

	@Override
	public ResponseEntity<Object> deleteClient(Long id) {
		if (!clientRepository.existsById(id)) {
			throw new ResourceNotFoundException("Client N° " + id + "not found");
		}
		clientRepository.deleteById(id);
		
		return ResponseEntity.ok().build();
	}


}
