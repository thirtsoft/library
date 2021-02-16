package com.library.services.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.entities.Contrat;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.ContratRepository;
import com.library.services.ContratService;
import com.library.utils.FileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.AbstractFileResolvingResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContratServiceImpl implements ContratService {
	
	@Autowired
	private ContratRepository contratRepository;

	private final Path fileStorageLocation = Paths.get("C:\\Users\\Folio9470m\\AlAmine\\Contrat");

	@Override
	public List<Contrat> findAllContrats() {
		List<Contrat> contrats = contratRepository.findAll();
		contrats.stream().map(Contrat::getContent).forEach(FileHelper::decompressBytes);
		return contrats;
	}

	@Override
	public Optional<Contrat> findContratById(Long id) {
		if (!contratRepository.existsById(id)) {
			throw new ResourceNotFoundException("Contrat N° " + id + "not found");
		}
		return contratRepository.findById(id);
	}

	@Override
	public Contrat findByReference(String reference) {
		return contratRepository.findByReference(reference);
	}

	@Override
	public List<Contrat> findListContratByReference(String reference) {
		return contratRepository.findListContratByReference(reference);
	}

	@Override
	public Contrat findByNature(String nature) {
		return contratRepository.findByNature(nature);
	}

	@Override
	public List<Contrat> findListContratByNature(String nature) {
		return contratRepository.findListContratByNature(nature);
	}

	@Override
	public List<Contrat> findContratByClientId(Long clientId) {
		return contratRepository.findLitContratByClientId(clientId);
	}

	@Override
	public Contrat saveContrat(Contrat contrat) {
		if (contratRepository.findByReference(contrat.getReference()) != null) {
			throw new IllegalArgumentException("Ce Contrat existe");
		}
		return contratRepository.save(contrat);
	}

	@Override
	public Contrat updateContrat(Long id, Contrat contrat) {
		if (!contratRepository.existsById(id)) {
			throw new ResourceNotFoundException("Contrat N° " + id + "not found");
		}
		Optional<Contrat> cont = contratRepository.findById(id);
		if (!cont.isPresent()) {
			throw new ResourceNotFoundException("Contrat not found");
		}
		
		Contrat contratResult = cont.get();
		
		contratResult.setReference(contrat.getReference());
		contratResult.setNature(contrat.getNature());
		contratResult.setClient(contrat.getClient());
		contratResult.setDescription(contrat.getDescription());
		contratResult.setDateDebutContrat(contrat.getDateDebutContrat());
		contratResult.setDateFinContrat(contrat.getDateFinContrat());
		
		return contratRepository.save(contratResult);
	}

	@Override
	public void deleteContrat(Long id) {
		if (!contratRepository.existsById(id)) {
			throw new ResourceNotFoundException("Contrat not found");
		}
		contratRepository.deleteById(id);
	}

	@Override
	public Contrat createContrat(String contrat, MultipartFile fileContrant) throws JsonParseException, JsonMappingException, IOException {

		Contrat contrat1 = new ObjectMapper().readValue(contrat, Contrat.class);
		System.out.println(contrat1);

		contrat1.setFileContrat(fileContrant.getOriginalFilename());

		return contratRepository.save(contrat1);
	}

	@Override
	public Resource loadFileAsResource(String fileName) throws Exception {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (((AbstractFileResolvingResource) resource).exists()) {
				return resource;
			}else {
				throw new Exception("File not found " + fileName);
			}

		} catch (MalformedURLException ex) {
			throw new Exception("File not found " + fileName, ex);
		}

	}

	@Override
	public Page<Contrat> findAllContratsByPageable(Pageable page) {
		return contratRepository.findAllContratsByPageable(page);
	}

	@Override
	public Page<Contrat> findAllContratsByClient(Long clientId, Pageable pageable) {
		return contratRepository.findContratByClientPageable(clientId, pageable);
	}

	@Override
	public Page<Contrat> findContratByKeyWord(String mc, Pageable pageable) {
		return contratRepository.findContratsByKeyWord(mc, pageable);
	}

	@Override
	public boolean createPdf(List<Contrat> contrats, ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createExcel(List<Contrat> contrats, ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		return false;
	}



}
