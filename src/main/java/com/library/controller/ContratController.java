package com.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.entities.CommandeClient;
import com.library.entities.Contrat;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.ContratService;
import com.library.services.ExcelService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import static com.library.utils.Constants.APP_ROOT;


@RestController
@CrossOrigin
//@RequestMapping("/alAmine")
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

    @GetMapping(value = APP_ROOT + "/contrats/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Contrat",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Contrats", responseContainer = "List<Contrat>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Contrat / une liste vide")
    })
    public List<Contrat> getAllContrats() {
        return contratService.findAllContrats();
    }

    @GetMapping(value = APP_ROOT + "/contrats/allContratOrderDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Contrat",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Contrat",
            responseContainer = "List<Contrat>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Contrat / une liste vide")
    })
    ResponseEntity<List<Contrat>> getAllContratOrderDesc() {
        List<Contrat> contratList = contratService.findAllContratsOrderDesc();
        return new ResponseEntity<>(contratList, HttpStatus.OK);
    }

    @GetMapping(value = APP_ROOT + "/contrats/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Contrat par ID",
            notes = "Cette méthode permet de chercher un Contrat par son ID", response = Contrat.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Contrat a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Contrat n'existe avec cette ID pas dans la BD")

    })
    public Optional<Contrat> getContratById(@PathVariable(value = "id") Long id) {
        return contratService.findContratById(id);
    }

    @GetMapping(value = APP_ROOT + "/contrats/searchContratByReference", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Contrat par son REFERENCE",
            notes = "Cette méthode permet de chercher un Contrat par son REFERENCE", response = Contrat.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Contrat a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Contrat n'existe avec cette REFERENCE pas dans la BD")

    })
    public Contrat getContratByReference(@RequestParam(name = "ref") String reference) {
        return contratService.findByReference(reference);
    }

    @GetMapping(value = APP_ROOT + "/contrats/searchListContratsByReference")
    @ApiOperation(value = "Renvoi la liste des Contrat par REFERENCE",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Contrats par REFERENCE", responseContainer = "List<Contrat>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Contrat / une liste vide")
    })
    public List<Contrat> getAllContratsByReference(@RequestParam(name = "ref") String reference) {
        return contratService.findListContratByReference("%" + reference + "%");
    }

    @GetMapping(value = APP_ROOT + "/contrats/searchContratByNature")
    public Contrat getContratByNature(@RequestParam(name = "nat") String nature) {
        return contratService.findByNature(nature);
    }

    @GetMapping(value = APP_ROOT + "/contrats/searchListContratsByNature")
    public List<Contrat> getAllContratsByNature(@RequestParam(name = "nat") String nature) {
        return contratService.findListContratByNature("%" + nature + "%");
    }

    @GetMapping(value = APP_ROOT + "/contrats/searchListContratsByClientId")
    public List<Contrat> getAllContratsByClientId(@RequestParam("client") Long clientId) {
        return contratService.findContratByClientId(clientId);
    }

    @PostMapping(value = APP_ROOT + "/contrats/createContrats")
    @ApiOperation(value = "Enregistrer un Contrat",
            notes = "Cette méthode permet d'enregistrer et modifier un Contrat", response = Contrat.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Le Contrat a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun Contrat crée / modifié")

    })
    public ResponseEntity<?> createContrat(@RequestPart(name = "contrat") String cont,
                                           @RequestParam(name = "file") MultipartFile file) throws IOException {
        Contrat contrat = new ObjectMapper().readValue(cont, Contrat.class);
        if (file != null && !file.isEmpty()) {
            contrat.setFileContrat(file.getOriginalFilename());
            file.transferTo(new File(contratsDir + file.getOriginalFilename()));
        }

        contratService.saveContrat(contrat);

        return ResponseEntity.status(HttpStatus.CREATED).body("Contrat is created");
    }

    @PostMapping(value = APP_ROOT + "/contrats/uploadFilePdf/{id}")
    @ApiOperation(value = "importer un Contrat",
            notes = "Cette méthode permet d'importer un contrat pdf depuis le disque vers la BD")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Contrat a été importé")
    })
    public void uploadContratFile(MultipartFile file, @PathVariable("id") Long id) throws IOException {
        Contrat contrat = contratService.findContratById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat not found"));
        contrat.setFileContrat(file.getOriginalFilename());
        Files.write(Paths.get(System.getProperty("user.home") + "/AlAmine/Contrat/" + contrat.getFileContrat()), file.getBytes());

        contratService.saveContrat(contrat);
    }

    @PutMapping(value = APP_ROOT + "/contrats/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier un Contrat",
            notes = "Cette méthode permet de modifier un Contrat", response = Contrat.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Contrat a été modifié"),
            @ApiResponse(code = 400, message = "Aucun Contrat modifié")

    })
    public ResponseEntity<Contrat> updateContrat(@PathVariable Long id, @RequestBody Contrat contrat) {
        contrat.setId(id);
        return new ResponseEntity<>(contratService.updateContrat(id, contrat), HttpStatus.OK);
    }

    @DeleteMapping(value = APP_ROOT + "/contrats/delete/{id}")
    @ApiOperation(value = "Supprimer un Contrat par son ID",
            notes = "Cette méthode permet de supprimer un Contrat par son ID", response = Contrat.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Contrat a été supprimé")
    })
    public ResponseEntity<?> deleteContrat(@PathVariable(value = "id") Long id) {
        contratService.deleteContrat(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = APP_ROOT + "/contrats/downloadContratFile/{fileName:.+}")
    @ApiOperation(value = "Télécharger un Contrat par son ID",
            notes = "Cette méthode permet de télécharger un Contrat par son ID", response = Contrat.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Contrat a été télécharger")
    })
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
