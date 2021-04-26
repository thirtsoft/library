package com.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.entities.Versement;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.VersementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class VersementController {

    private final String EXTERNAL_FILE_PATH = "C:/Users/Folio9470m/AlAmine/Versement/";
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
        return versementService.findListVersementByNumVersement("%" + numVersement + "%");
    }

    @GetMapping("/searchVersementByNature")
    public Versement getVersementByNature(@RequestParam(name = "nat") String nature) {
        return versementService.findByNature(nature);
    }

    @GetMapping("/searchListVersementsByNature")
    public List<Versement> getListVersementsByNature(@RequestParam(name = "nat") String nature) {
        return versementService.findListVersementByNature("%" + nature + "%");
    }

    @GetMapping("/searchListVersementsByEmployeId")
    public List<Versement> getAllVersementsByEmployeId(Long empId) {
        List<Versement> verResult = versementService.findVersementByEmployeId(empId);
        System.out.println(verResult);
        //	return versementService.findVersementByEmployeId(empId);
        return verResult;
    }


    @PostMapping("/versements")
    public ResponseEntity<Versement> saveVersement(@RequestBody Versement versement) {
        if (versementService.findByNumVersement(versement.getNumVersement()) != null) {
            return new ResponseEntity<>(versement, HttpStatus.BAD_REQUEST);
        }
        versementService.saveVersement(versement);
        return new ResponseEntity<>(versement, HttpStatus.CREATED);
    }

    @PutMapping("/versements/{id}")
    public ResponseEntity<Versement> updateVersement(@PathVariable Long id, @RequestBody Versement versement) {
        versement.setId(id);
        return new ResponseEntity<>(versementService.updateVersement(id, versement), HttpStatus.OK);
    }

    @PostMapping("/createVersement")
    public ResponseEntity<?> createVersement(@RequestPart(name = "versement") String vers,
                                             @RequestParam(name = "file") MultipartFile file) throws IOException {
        Versement versement = new ObjectMapper().readValue(vers, Versement.class);
        if (file != null && !file.isEmpty()) {
            versement.setFileVersement(file.getOriginalFilename());
            file.transferTo(new File(versementsDir + file.getOriginalFilename()));
        }

        versementService.saveVersement(versement);

        return ResponseEntity.status(HttpStatus.CREATED).body("Versement is created");
    }

    @PostMapping(path = "/uploadPdfFile/{id}")
    public void uploadVersementFile(MultipartFile file, @PathVariable("id") Long id) throws IOException {
        Versement versement = versementService.findVersementById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Versement not found"));
        versement.setFileVersement(file.getOriginalFilename());
        Files.write(Paths.get(System.getProperty("user.home") + "/AlAmine/Versement/" + versement.getFileVersement()), file.getBytes());

        versementService.saveVersement(versement);
    }

    @RequestMapping("/downloadVersementFile/{fileName:.+}")
    public void downloadVersementFile(HttpServletRequest request, HttpServletResponse response,
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


    @DeleteMapping("/versements/{id}")
    public ResponseEntity<?> deleteVersement(@PathVariable(value = "id") Long id) {
        versementService.deleteVersement(id);
        return ResponseEntity.ok().build();
    }


}
