package com.library.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.domaine.Response;
import com.library.services.ExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.library.entities.Contrat;
import com.library.services.ContratService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class ContratController {
	
	@Autowired
	private ContratService contratService;

	@Autowired
	private ExcelService excelService;

    private static final Logger logger = LoggerFactory.getLogger(ContratController.class);

	private final Path fileStorageLocation = Paths.get("C://Users//Folio9470m//AlAmine//Contrat//");
    private String contratsDir="C://Users//Folio9470m//AlAmine//Contrat//";
	
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

	@PostMapping("/saveContrat")
    public ResponseEntity<Object> saveContrat(@RequestParam(name = "contrat") String cont,
                                              @RequestParam(name = "file") MultipartFile file) throws Exception {
        Contrat contrat = new ObjectMapper().readValue(cont, Contrat.class);
        if (!(file.isEmpty())) {
            contrat.setFileContrat(file.getOriginalFilename());
        }
        contratService.saveContrat(contrat);
        if (!(file.isEmpty())) {
            contrat.setFileContrat(file.getOriginalFilename());
            file.transferTo(new File(contratsDir+file.getOriginalFilename()));
        }
        return new ResponseEntity<>("Contrat with file is create successfull", HttpStatus.OK);
    }

    // methode pour afficher la photo d'un produit
    @GetMapping("/fileContrat/{id}")
    public byte[] getFileContrat(@PathVariable("id") Long id) throws Exception {
	    Contrat cont = contratService.findContrattById(id).get();
        return Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/AlAmine/Contrat/"+cont.getFileContrat()));
    }




    @PostMapping("/createContrat")
	public ResponseEntity<?> createCandidate(@RequestPart("contrat") String contrat, @RequestParam("file_contrat")
			MultipartFile file1) throws JsonParseException, JsonMappingException, IOException {
		Contrat contrat2 = contratService.createContrat(contrat, file1);
		if (contrat2!=null) {
			return  ResponseEntity.status(HttpStatus.ACCEPTED).body("Contrat is saved");
		}else {
			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Contrat is not saved");
		}
	}

    @GetMapping("/downloadFile/{fileName:.+}")

    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws Exception {

        // Load file as Resource
        Resource resource = contratService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping("/contrats")
	public ResponseEntity<Contrat> createContrat(@RequestBody Contrat contrat) {
		if (contratService.findByReference(contrat.getReference()) != null) {
			return new ResponseEntity<Contrat>(contrat, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Contrat>(contrat, HttpStatus.CREATED);
	}

	@PutMapping("/contrats/{id}")
	public Contrat updateContrat(@PathVariable Long id, @RequestBody Contrat contrat) {
		contrat.setId(id);
		return contratService.saveContrat(contrat);
	}
	
	@DeleteMapping("/contrats/{id}")
	public ResponseEntity<?> deleteContrat(@PathVariable(value="id") Long id) {
		contratService.deleteContrat(id);
		return ResponseEntity.ok().build();
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
