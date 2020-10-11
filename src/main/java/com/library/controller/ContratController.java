package com.library.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.library.services.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.entities.Contrat;
import com.library.services.ContratService;
import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class ContratController {
	
	@Autowired
	private ContratService contratService;

	@Autowired
	private ExcelService excelService;
	
	@GetMapping("/contrats")
	public List<Contrat> getAllContrats() {
		return contratService.findAllContrats();
	}
	
	@GetMapping("/contrats/{id}")
	public Optional<Contrat> getContratById(@PathVariable(value = "id") Long id) {
		return contratService.findContrattById(id);
	}
	
	@GetMapping("/searchContratByReference")
	public Contrat getContratByReference(@RequestParam(name = "ref") String reference) {
		return contratService.findByReference(reference);
	}
	
	@GetMapping("/searchListContratsByReference")
	public List<Contrat> getAllContratsByReference(@RequestParam(name = "ref") String reference) {
		return contratService.findListContratByReference("%"+reference+"%");
	}
	
	@GetMapping("/searchContratByNature")
	public Contrat getContratByNature(@RequestParam(name = "nat") String nature) {
		return contratService.findByNature(nature);
	}
	
	@GetMapping("/searchListContratsByNature")
	public List<Contrat> getAllContratsByNature(@RequestParam(name = "nat") String nature) {
		return contratService.findListContratByNature("%"+nature+"%");
	}
	
	@GetMapping("/searchListContratsByClientId")
	public List<Contrat> getAllContratsByClientId(@RequestParam("client") Long clientId) {
		return contratService.findContratByClientId(clientId);
	}
	
	@GetMapping("/searchListContratsByClientPageable")
	public Page<Contrat> getAllContratsByPageable(@RequestParam(name = "client")Long clientId,
			@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size) {
		return contratService.findAllContratsByClient(clientId, PageRequest.of(page, size));
	}
	
	@GetMapping("/searchListContratsByPageable")
	public Page<Contrat> getAllContratsByPageable(@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size) {
		return contratService.findAllContratsByPageable(PageRequest.of(page, size));
	}
	
	@GetMapping("/searchListContratsByKeyword")
	public Page<Contrat> getAllContratsByKeyword(@RequestParam(name = "mc") String mc, 
			@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size) {
		return contratService.findContratByKeyWord("%"+mc+"%", PageRequest.of(page, size));
		
	}
	
	@PostMapping("/contrats")
	public Contrat createContrat(@RequestBody Contrat contrat) {
		return contratService.saveContrat(contrat);
	}
	
	
	@PutMapping("/contrats/{id}")
	public Contrat updateContrat(@PathVariable Long id, @RequestBody Contrat contrat) {
		contrat.setId(id);
		return contratService.saveContrat(contrat);
	}
	
	@DeleteMapping("/contrats/{id}")
	public ResponseEntity<Object> deleteContrat(@PathVariable(value="id") Long id) {
		return contratService.deleteContrat(id);
	}

	@PostMapping("/uploadContrat")
	public ResponseEntity<Object> uploadContratFile(@RequestParam("file") MultipartFile file) throws IOException {
		//Contrat contrat = new Contrat();
		//excelService.storeContratFile(file);

		File convertFile = new File("C:/Users/Folio9470m/Music/contrat/"+file.getOriginalFilename());
		convertFile.createNewFile();
		FileOutputStream fout = new FileOutputStream(convertFile);
		fout.write(file.getBytes());
		fout.close();
		return new ResponseEntity<>("File is uploaded successfully", HttpStatus.OK);

	}
	

}
