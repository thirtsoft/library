package com.library.controller;

import com.library.entities.Scategorie;
import com.library.message.response.ResponseMessage;
import com.library.services.ExcelService;
import com.library.services.PdfService;
import com.library.services.ScategorieService;
import com.library.utils.ExcelUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.library.utils.Constants.APP_ROOT;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ScategorieController {

    @Autowired
    private ScategorieService scategorieService;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ServletContext context;

    @GetMapping(value = APP_ROOT + "/scategories/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Scategorie",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Scategorie", responseContainer = "List<Scategorie>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Scategorie / une liste vide")
    })
    public List<Scategorie> getAllScategories() {
        return scategorieService.findAllScategories();
    }

    @GetMapping(value = APP_ROOT + "/scategories/allScategorieOrderDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Scategorie",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Scategorie",
            responseContainer = "List<Scategorie>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Scategorie / une liste vide")
    })
    ResponseEntity<List<Scategorie>> getAllScategorieOrderDesc() {
        List<Scategorie> scategorieList = scategorieService.findAllScategoriesOrderDesc();
        return new ResponseEntity<>(scategorieList, HttpStatus.OK);
    }

    @GetMapping(value = APP_ROOT + "/scategories/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une Scategorie par ID",
            notes = "Cette méthode permet de chercher une Scategorie par son ID", response = Scategorie.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La Scategorie a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Scategorie n'existe avec cette ID pas dans la BD")

    })
    public Optional<Scategorie> getScategorieById(@PathVariable(value = "id") Long scatId) {
        return scategorieService.findScategorieById(scatId);
    }

    @GetMapping(value = APP_ROOT + "/scategories/searchScategorieByCode", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une Scategorie par CODE",
            notes = "Cette méthode permet de chercher une Scategorie par son CODE", response = Scategorie.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La Scategorie avec ce code a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Scategorie n'existe avec cette CODE pas dans la BD")

    })
    public Scategorie getScategorieByCode(@RequestParam(name = "code") String code) {
        return scategorieService.findByCode(code);
    }

    @GetMapping(value = APP_ROOT + "/scategories/searchListScategoriesByCode", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Scategorie par code",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Scategories par code", responseContainer = "List<Scategorie>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Scategorie par code / une liste vide")
    })
    public List<Scategorie> getListScategoriesByCode(@RequestParam(name = "code") String code) {
        return scategorieService.findListScategorieByCode("%" + code + "%");
    }

    @GetMapping(value = APP_ROOT + "/scategories/searchProduitByLibelle", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une Scategorie par libelle",
            notes = "Cette méthode permet de chercher une Scategorie par son libelle", response = Scategorie.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La Scategorie avec ce libelle a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Scategorie n'existe avec ce libelle pas dans la BD")

    })
    public Scategorie getScategorieByLibelle(@RequestParam(name = "libelle") String libelle) {
        return scategorieService.findByLibelle(libelle);
    }

    @GetMapping(value = APP_ROOT + "/scategories/searchListScategoriesByLibelle", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Scategorie par libelle",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Scategories par libelle", responseContainer = "List<Scategorie>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Scategorie par libelle / une liste vide")
    })
    public List<Scategorie> getListScategoriesByLibelle(@RequestParam(name = "libelle") String libelle) {
        return scategorieService.findListScategorieByLibelle("%" + libelle + "%");
    }

    @GetMapping(value = APP_ROOT + "/scategories/searchListScategoriesByCategoryId/{catId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Scategorie par categorie",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Scategories par categorie", responseContainer = "List<Scategorie>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Scategorie par categorie / une liste vide")
    })
    public List<Scategorie> getListScategoriesByCategoryId(@PathVariable(name = "catId") Long catId) {
        return scategorieService.findScategorieByCateoryId(catId);
    }

    @GetMapping(value = APP_ROOT + "/scategories/searchListScategoriesByCategoryId")
    public List<Scategorie> getListScategoriesByCategory(@RequestParam(name = "catId") Long catId) {
        return scategorieService.findScategorieByCateoryId(catId);
    }


    @PostMapping(value = APP_ROOT + "/scategories/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une Scategorie",
            notes = "Cette méthode permet d'enregistrer et modifier une Scategorie", response = Scategorie.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "La Scategorie a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun Scategorie  crée / modifié")
    })
    public ResponseEntity<Scategorie> saveScategorie(@RequestBody Scategorie scategorie) {
        if (scategorieService.findByCode(scategorie.getCode()) != null) {
            return new ResponseEntity<>(scategorie, HttpStatus.BAD_REQUEST);
        }
        scategorieService.saveScategorie(scategorie);
        return new ResponseEntity<>(scategorie, HttpStatus.CREATED);
    }

    @PutMapping(value = APP_ROOT + "/scategories/update/{ScatId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier un Scategorie par son ID",
            notes = "Cette méthode permet de modifier un Scategorie par son ID", response = Scategorie.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La Scategorie avec cet ID a été modifié"),
            @ApiResponse(code = 400, message = "Aucun Scategorie a été modifié avec ID")

    })
    public Scategorie updateScategorie(@PathVariable Long ScatId, @RequestBody Scategorie sCategorie) {
        sCategorie.setId(ScatId);
        return scategorieService.updateScategorie(ScatId, sCategorie);
    }

    @DeleteMapping(value = APP_ROOT + "/scategories/delete/{id}")
    @ApiOperation(value = "Supprimer une Scategorie par son ID",
            notes = "Cette méthode permet de supprimer une Scategorie par son ID", response = Scategorie.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La Scategorie a été supprimé")
    })
    public ResponseEntity<?> deleteScategorie(@PathVariable(value = "id") Long ScatId) {
        scategorieService.deleteScategorie(ScatId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = APP_ROOT + "/scategories/createScategoriePdf")
    @ApiOperation(value = "Générer un PDF",
            notes = "Cette méthode permet de générer la liste des scategories sous format pdf")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Pdf a été généré")
    })
    public void createScategoriePdf(HttpServletRequest request, HttpServletResponse response) {
        List<Scategorie> scategories = scategorieService.findAllScategories();
        boolean isFlag = pdfService.createScategoriesPdf(scategories, context, request, response);

        if (isFlag) {
            String fullPath = request.getServletContext().getRealPath("/resources/reports/" + "scategories" + ".pdf");
            filedownload(fullPath, response, "scategories.pdf");
        }
    }

    private void filedownload(String fullPath, HttpServletResponse response, String fileName) {
        File file = new File(fullPath);
        final int BUFFER_SIZE = 4096;
        if (file.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                String mimeType = context.getMimeType(fullPath);
                response.setContentType(mimeType);
                response.setHeader("content-disposition", "attachment; filename=" + fileName);
                OutputStream outputStream = response.getOutputStream();
                byte[] buffer = new byte[BUFFER_SIZE];
                int byteRead = -1;
                while ((byteRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, byteRead);
                }
                inputStream.close();
                outputStream.close();
                file.delete();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @PostMapping(value = APP_ROOT + "/scategories/uploadScategorie")
    @ApiOperation(value = "Importer un fichier excel",
            notes = "Cette méthode permet d'importer le contenu d'un fichier Excel vers la BD")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Fichier a été importé avec succès")
    })
    public ResponseEntity<ResponseMessage> uploadExcelScategorie(@RequestParam("file") MultipartFile file) {
        String message;
        if (ExcelUtils.isExcelFile(file)) {
            try {
                excelService.storeScategorieFile(file);
                message = messageSource.getMessage("message.upload.success", null, Locale.getDefault()) + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = messageSource.getMessage("message.upload.fail", null, Locale.getDefault()) + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
        message = messageSource.getMessage("message.upload.notExcelFile", null, Locale.getDefault());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping(value = APP_ROOT + "/scategories/download/scategories.xlsx")
    @ApiOperation(value = "Exporter un fichier excel",
            notes = "Cette méthode permet d'exporter le contenu d'une BD vers un fichier Excel")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Fichier a été exporté avec succès")
    })
    public ResponseEntity<InputStreamResource> excelScategoriesReport() throws IOException {
        List<Scategorie> scategories = scategorieService.findAllScategories();

        ByteArrayInputStream in = ExcelUtils.ScategoriesToExcel(scategories);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=scategories.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }


}
