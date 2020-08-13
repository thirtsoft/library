package com.library.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.library.entities.Client;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.ClientRepository;

@Service
@Transactional
public class ClientServiceImpl implements ClientService{
	
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
	public Client findByNom(String nom) {
		return clientRepository.findByNom(nom);
	}

	@Override
	public Client findByPrenom(String prenom) {
		return clientRepository.findByPrenom(prenom);
	}

	@Override
	public List<Client> ListClientByNom(String nom) {
		return clientRepository.ListClientByNom(nom);
	}

	@Override
	public List<Client> ListClientByPrneom(String prenom) {
		return clientRepository.ListClientByPrenom(prenom);
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
		clientResult.setNom(client.getNom());
		clientResult.setPrenom(client.getPrenom());
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
