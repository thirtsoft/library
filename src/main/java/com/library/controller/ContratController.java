package com.library.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.entities.Contrat;
import com.library.services.ContratService;
import com.library.services.ExcelService;
import com.library.utils.FileHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class ContratController {

    private static final Logger logger = LoggerFactory.getLogger(ContratController.class);

    private final Path fileStorageLocation = Paths.get("C://Users//Folio9470m//AlAmine//Contrat//");

    private final Path rootLocation = Paths.get("C:\\Users\\Folio9470m\\AlAmine\\Contrat\\");

    private final String EXTERNAL_FILE_PATH = "C:/Users/Folio9470m/AlAmine/Contrat/";


    @Autowired
    private ContratService contratService;
    @Autowired
    private ExcelService excelService;

    private String contratsDir = "C://Users//Folio9470m//AlAmine//Contrat//";


    @GetMapping("/contrats")
    public List<Contrat> getAllContrats() {
        return contratService.findAllContrats();
    }

    @GetMapping("/contrats/{id}")
    public Optional<Contrat> getContratById(@PathVariable(value = "id") Long id) {
        return contratService.findContratById(id);
    }

    @GetMapping("/searchContratByReference")
    public Contrat getContratByReference(@RequestParam(name = "ref") String reference) {
        return contratService.findByReference(reference);
    }

    @GetMapping("/searchListContratsByReference")
    public List<Contrat> getAllContratsByReference(@RequestParam(name = "ref") String reference) {
        return contratService.findListContratByReference("%" + reference + "%");
    }

    @GetMapping("/searchContratByNature")
    public Contrat getContratByNature(@RequestParam(name = "nat") String nature) {
        return contratService.findByNature(nature);
    }

    @GetMapping("/searchListContratsByNature")
    public List<Contrat> getAllContratsByNature(@RequestParam(name = "nat") String nature) {
        return contratService.findListContratByNature("%" + nature + "%");
    }

    @GetMapping("/searchListContratsByClientId")
    public List<Contrat> getAllContratsByClientId(@RequestParam("client") Long clientId) {
        return contratService.findContratByClientId(clientId);
    }

    @PostMapping("/createContrats")
    public ResponseEntity<?> createContrat(@RequestPart(name = "contrat") String cont,
                                              @RequestParam(name = "file") MultipartFile file) throws JsonParseException, JsonMappingException, IOException {
        Contrat contrat = new ObjectMapper().readValue(cont, Contrat.class);
        if (file != null && !file.isEmpty()) {
            contrat.setFileContrat(file.getOriginalFilename());
            file.transferTo(new File(contratsDir + file.getOriginalFilename()));
        }

        contratService.saveContrat(contrat);
        return ResponseEntity.status(HttpStatus.CREATED).body("Contrat is created");
      //  return new ResponseEntity<>("Contrat with file is create successfull", HttpStatus.CREATED);
    }

    @PostMapping("/saveContrats")
    public BodyBuilder saveContrats(@RequestParam(name = "contrat") String cont,
                                    @RequestParam(name = "uploading") MultipartFile file) throws Exception {
        Contrat contrat = new ObjectMapper().readValue(cont, Contrat.class);
        if (file != null && !file.isEmpty()) {
            contrat.setContent(FileHelper.compressBytes(file.getBytes()));
        }
        contratService.saveContrat(contrat);
        return ResponseEntity.status(HttpStatus.CREATED);
    }


    @PutMapping("/contrats/{id}")
    public ResponseEntity<Contrat> updateContrat(@PathVariable Long id, @RequestBody Contrat contrat) {
        contrat.setId(id);
        return new ResponseEntity<>(contratService.updateContrat(id, contrat), HttpStatus.OK);
    }

    @DeleteMapping("/contrats/{id}")
    public ResponseEntity<?> deleteContrat(@PathVariable(value = "id") Long id) {
        contratService.deleteContrat(id);
        return ResponseEntity.ok().build();
    }


    @RequestMapping("/downloadContratFile/{fileName:.+}")
    public void downloadContratFile(HttpServletRequest request, HttpServletResponse response,
                                    @PathVariable("fileName") String fileName) throws IOException {
        File file = new File(EXTERNAL_FILE_PATH + fileName);
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
