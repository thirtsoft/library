package com.library.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.core.io.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.library.entities.Contrat;
import org.springframework.web.multipart.MultipartFile;

public interface ContratService {
	
	public List<Contrat> findAllContrats();
	public Optional<Contrat> findContrattById(Long id);
	
	public Contrat findByReference(String reference);
	public List<Contrat> findListContratByReference(String reference);
	
	public Contrat findByNature(String nature);
	public List<Contrat> findListContratByNature(String nature);

	
	public List<Contrat> findContratByClientId(Long clientId);
	
	Contrat saveContrat(Contrat contrat);
	Contrat updateProduit(Long id, Contrat contrat);
	ResponseEntity<Object> deleteContrat(Long id);

	Contrat createContrat(String contrat, MultipartFile fileContrant) throws JsonParseException, JsonMappingException, IOException;
	Resource loadFileAsResource(String fileName) throws Exception;
	
	
	public Page<Contrat> findAllContratsByPageable(Pageable page);
	public Page<Contrat>findAllContratsByClient(Long clientId, Pageable pageable);
	
	public Page<Contrat> findContratByKeyWord(String mc, Pageable pageable);
	
	boolean createPdf(List<Contrat> contrats, ServletContext context, HttpServletRequest request, HttpServletResponse response);
	
	boolean createExcel(List<Contrat> contrats, ServletContext context, HttpServletRequest request, HttpServletResponse response);


}
