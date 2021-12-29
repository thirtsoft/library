package com.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.entities.Versement;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.VersementService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static com.library.utils.Constants.APP_ROOT;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class VersementController {

    private final String EXTERNAL_FILE_PATH = "C:/Users/Folio9470m/AlAmine/Versement/";
    private final String versementsDir = "C://Users//Folio9470m//AlAmine//Versement//";
    @Autowired
    private VersementService versementService;

    @GetMapping(value = APP_ROOT + "/versements/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Versement",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Versement", responseContainer = "List<Versement>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Charge / une liste vide")
    })
    public List<Versement> getAllVersements() {
        return versementService.findAllVersements();
    }

    @GetMapping(value = APP_ROOT + "/versements/allVersementderDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Versement",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Vente",
            responseContainer = "List<Versement>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Versement / une liste vide")
    })
    ResponseEntity<List<Versement>> getAllVersementOrderDesc() {
        List<Versement> versementList = versementService.findAllVersementsOrderDesc();
        return new ResponseEntity<>(versementList, HttpStatus.OK);
    }

    @GetMapping(value = APP_ROOT + "/versements/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Versement par ID",
            notes = "Cette méthode permet de chercher un Versement par son ID", response = Versement.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Versement a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Versement n'existe avec cette ID pas dans la BD")

    })
    public Optional<Versement> getVersementById(@PathVariable(value = "id") Long id) {
        return versementService.findVersementById(id);
    }

    @GetMapping(value = APP_ROOT + "/versements/searchVersementByNumVersement", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Versement par numVersement",
            notes = "Cette méthode permet de chercher une Versement par sa numVersement", response = Versement.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Versement avec cette numVersement a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Charge n'existe avec cet numVersement pas dans la BD")

    })
    public Versement getVersementByNumVersement(@RequestParam(name = "num") String numVersement) {
        return versementService.findByNumVersement(numVersement);
    }

    @GetMapping(value = APP_ROOT + "/versements/searchListVersementsByNumVersement", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Versement par numVersement",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Versement par numVersement", responseContainer = "List<Versement>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Versement par numVersement / une liste vide")
    })
    public List<Versement> getListVersementsByNumVersement(@RequestParam(name = "num") String numVersement) {
        return versementService.findListVersementByNumVersement("%" + numVersement + "%");
    }

    @GetMapping(value = APP_ROOT + "/versements/searchVersementByNature", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Versement par nature",
            notes = "Cette méthode permet de chercher une Versement par sa nature", response = Versement.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Versement avec cette nature a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Charge n'existe avec cet nature pas dans la BD")

    })
    public Versement getVersementByNature(@RequestParam(name = "nat") String nature) {
        return versementService.findByNature(nature);
    }

    @GetMapping(value = APP_ROOT + "/versements/searchListVersementsByNature", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Versement par nature",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Versement par nature", responseContainer = "List<Versement>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Versement par nature / une liste vide")
    })
    public List<Versement> getListVersementsByNature(@RequestParam(name = "nat") String nature) {
        return versementService.findListVersementByNature("%" + nature + "%");
    }

    @GetMapping(value = APP_ROOT + "/versements/searchListVersementsByEmployeId", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Versement par Employe",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Versement par employe", responseContainer = "List<Versement>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Versement par employe / une liste vide")
    })
    public List<Versement> getAllVersementsByEmployeId(Long empId) {
        List<Versement> verResult = versementService.findVersementByEmployeId(empId);
        System.out.println(verResult);
        //	return versementService.findVersementByEmployeId(empId);
        return verResult;
    }

    @PostMapping(value = APP_ROOT + "/versements/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un Versement",
            notes = "Cette méthode permet d'enregistrer un Versement", response = Versement.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Le Versement a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun Versement n'a pas été  crée / modifié")

    })
    public ResponseEntity<Versement> saveVersement(@RequestBody Versement versement) {
        if (versementService.findByNumVersement(versement.getNumVersement()) != null) {
            return new ResponseEntity<>(versement, HttpStatus.BAD_REQUEST);
        }
        versementService.saveVersement(versement);
        return new ResponseEntity<>(versement, HttpStatus.CREATED);
    }

    @PutMapping(value = APP_ROOT + "/versements/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier un Versement",
            notes = "Cette méthode permet de modifier un Versement", response = Versement.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Versement a été modifié"),
            @ApiResponse(code = 400, message = "Aucun Versement n'a été modifié")

    })
    public ResponseEntity<Versement> updateVersement(@PathVariable Long id, @RequestBody Versement versement) {
        versement.setId(id);
        return new ResponseEntity<>(versementService.updateVersement(id, versement), HttpStatus.OK);
    }

    @PostMapping(value = APP_ROOT + "/versements/createVersement")
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

    @PostMapping(value = APP_ROOT + "/versements/uploadPdfFile/{id}")
    @ApiOperation(value = "Importer un PDF",
            notes = "Cette méthode permet d'importer un fichier PDf d'un dossier externe vers la BD")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'importation du fichier  s'est passé avec succès")
    })
    public void uploadVersementFile(MultipartFile file, @PathVariable("id") Long id) throws IOException {
        Versement versement = versementService.findVersementById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Versement not found"));
        versement.setFileVersement(file.getOriginalFilename());
        Files.write(Paths.get(System.getProperty("user.home") + "/AlAmine/Versement/" + versement.getFileVersement()), file.getBytes());

        versementService.saveVersement(versement);
    }

    @RequestMapping(value = APP_ROOT + "/versements/downloadVersementFile/{fileName:.+}")
    @ApiOperation(value = "Télécharger un PDF",
            notes = "Cette méthode permet de télécharger un le reçu d'un  Versement")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Versement a été téléchargé")
    })
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

    @DeleteMapping(value = APP_ROOT + "/versements/delete/{id}")
    @ApiOperation(value = "Supprimer un Versement par son ID",
            notes = "Cette méthode permet de supprimer un Versement par son ID", response = Versement.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Versement a été supprimé")
    })
    public ResponseEntity<?> deleteVersement(@PathVariable(value = "id") Long id) {
        versementService.deleteVersement(id);
        return ResponseEntity.ok().build();
    }


}
