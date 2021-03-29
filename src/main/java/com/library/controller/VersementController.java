package com.library.controller;

import java.io.*;
import java.net.URLConnection;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.entities.Contrat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
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

import com.library.entities.Versement;
import com.library.services.VersementService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class VersementController {

	private String versementsDir = "C://Users//Folio9470m//AlAmine//Versement//";

	@Autowired
	private VersementService versementService;
		
	@GetMapping("/versements")
	public List<Versement> getAllVersements() {
		return versementService.findAllVersements();
	}
	
	@GetMapping("/versements/{id}")
	public Optional<Versement> getVersementById(@PathVariable(value = "id") Long id) {
		return versementService.findVersementById(id);
	}
	
	@GetMapping("/searchVersementByNumVersement")
	public Versement getVersementByNumVersement(@RequestParam(name = "num") String numVersement) {
		return versementService.findByNumVersement(numVersement);
	}
	
	@GetMapping("/searchListVersementsByNumVersement")
	public List<Versement> getListVersementsByNumVersement(@RequestParam(name = "num") String numVersement) {
		return versementService.findListVersementByNumVersement("%"+numVersement+"%");
	}
	
	@GetMapping("/searchVersementByNature")
	public Versement getVersementByNature(@RequestParam(name = "nat") String nature) {
		return versementService.findByNature(nature);
	}
	
	@GetMapping("/searchListVersementsByNature")
	public List<Versement> getListVersementsByNature(@RequestParam(name = "nat") String nature) {
		return versementService.findListVersementByNature("%"+nature+"%");
	}
	
	@GetMapping("/searchListVersementsByEmployeId")
	public List<Versement> getAllVersementsByEmployeId(@RequestParam("id") Long empId) {
		return versementService.findVersementByEmployeId(empId);
	}

	@GetMapping("/searchListVersementsByKeyword")
	public Page<Versement> getAllVersementsByKeyword(@RequestParam(name = "mc") String mc, 
			@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size) {
		return versementService.findVersementByKeyWord("%"+mc+"%", PageRequest.of(page, size));
		
	}
	
	@PostMapping("/versements")
	public ResponseEntity<Versement> saveVersement(@RequestBody Versement versement) {
		if (versementService.findByNumVersement(versement.getNumVersement()) !=null) {
			return new ResponseEntity<Versement>(versement, HttpStatus.BAD_REQUEST);
		}
		versementService.saveVersement(versement);
		return new ResponseEntity<Versement>(versement, HttpStatus.CREATED);
	}

	@PutMapping("/versements/{id}")
	public ResponseEntity<Versement> updateVersement(@PathVariable Long id, @RequestBody Versement versement) {
		versement.setId(id);
		return new ResponseEntity<>(versementService.updateVersement(id, versement), HttpStatus.OK);
	}
	
	@DeleteMapping("/versements/{id}")
	public ResponseEntity<?> deleteVersement(@PathVariable(value="id") Long id) {
		versementService.deleteVersement(id);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/createVersement")
	public ResponseEntity<?> createVersement(@RequestParam(name = "versement") String versement,
										   @RequestParam(name = "file") MultipartFile file) throws JsonParseException, JsonMappingException, IOException {
		Versement versement_1 = new ObjectMapper().readValue(versement, Versement.class);
		if (file != null && !file.isEmpty()) {
			versement_1.setFileVersement(file.getOriginalFilename());
			file.transferTo(new File(versementsDir + file.getOriginalFilename()));
		}

		versementService.saveVersement(versement_1);
		return new ResponseEntity<>("Versement with file is create successfull", HttpStatus.CREATED);
	}

	@RequestMapping("/downloadVersementFile/{fileName:.+}")
	public void downloadContratFile(HttpServletRequest request, HttpServletResponse response,
									@PathVariable("fileName") String fileName) throws IOException {
		File file = new File(versementsDir + fileName);
		if (file.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}




}
