package com.library.services;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.library.entities.Contrat;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ContratService {

	List<Contrat> findAllContrats();

	Optional<Contrat> findContratById(Long id);

	Contrat findByReference(String reference);

	List<Contrat> findListContratByReference(String reference);

	Contrat findByNature(String nature);

	List<Contrat> findListContratByNature(String nature);

	List<Contrat> findContratByClientId(Long clientId);

	Contrat saveContrat(Contrat contrat);
	Contrat updateContrat(Long id, Contrat contrat);
	void deleteContrat(Long id);

	Contrat createContrat(String contrat, MultipartFile fileContrant) throws JsonParseException, JsonMappingException, IOException;

	Resource loadFileAsResource(String fileName) throws Exception;
	
	
	Page<Contrat> findAllContratsByPageable(Pageable page);
	Page<Contrat>findAllContratsByClient(Long clientId, Pageable pageable);
	
	Page<Contrat> findContratByKeyWord(String mc, Pageable pageable);
	
	boolean createPdf(List<Contrat> contrats, ServletContext context, HttpServletRequest request, HttpServletResponse response);
	
	boolean createExcel(List<Contrat> contrats, ServletContext context, HttpServletRequest request, HttpServletResponse response);



}
