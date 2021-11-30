package com.library.controller;

import com.library.entities.Produit;
import com.library.exceptions.ResourceNotFoundException;
import com.library.message.response.ResponseMessage;
import com.library.services.ExcelService;
import com.library.services.PdfService;
import com.library.services.ProduitService;
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


@RestController
//@CrossOrigin(origins = "http://localhost:4200")
//@RequestMapping("/prodApi")
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ExcelUtils utils;

    @Autowired
    private ServletContext context;

    @GetMapping(value = APP_ROOT + "/produits/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Produit",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Produit", responseContainer = "List<Produit>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Produit / une liste vide")
    })
    public List<Produit> getAllProduits() {
        return produitService.findAllProduits();
    }

    @GetMapping(value = APP_ROOT + "/produits/allProduitOrderDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Produit",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Produit",
            responseContainer = "List<Produit>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Produit / une liste vide")
    })
    ResponseEntity<List<Produit>> getAllProduitOrderDesc() {
        List<Produit> produitList = produitService.findAllProduitsOrderDesc();
        return new ResponseEntity<>(produitList, HttpStatus.OK);
    }

    @GetMapping(value = APP_ROOT + "/produits/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Produit par ID",
            notes = "Cette méthode permet de chercher une Produit par son ID", response = Produit.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Produit a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Produit n'existe avec cette ID pas dans la BD")

    })
    public Optional<Produit> getProduitById(@PathVariable(value = "id") Long prodId) {
        return produitService.findProduitById(prodId);
    }

    @GetMapping(value = APP_ROOT + "/produits/searchProduitByReference", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Produit par reference",
            notes = "Cette méthode permet de chercher une Produit par reference", response = Produit.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Produit avec cet reference a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Produit n'existe avec cette reference pas dans la BD")

    })
    public Produit getProduitByReference(@RequestParam(name = "ref") String reference) {
        return produitService.findByReference(reference);
    }

    @GetMapping(value = APP_ROOT + "/produits/searchProduitByDesignation", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Produit par designation",
            notes = "Cette méthode permet de chercher une Produit par designation", response = Produit.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Produit avec cet designation a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Produit n'existe avec cette designation pas dans la BD")

    })
    public Produit getProduitByDesignation(@RequestParam(name = "des") String designation) {
        return produitService.findByDesignation(designation);
    }

    @GetMapping(value = APP_ROOT + "/produits/searchProduitByPrixAchat")
    public Produit getProduitByPrixAchat(@RequestParam(name = "price") Double prixAchat) {
        return produitService.findByPrixAchat(prixAchat);
    }

    @GetMapping(value = APP_ROOT + "/produits/searchListProduitsByDesignation", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Produit par designation",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Produit par designation", responseContainer = "List<Produit>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Produit par designation / une liste vide")
    })
    public List<Produit> getAllProduitsByDesignation(@RequestParam(name = "des") String designation) {
        return produitService.findListProduitByDesignation("%" + designation + "%");
    }

    @GetMapping(value = APP_ROOT + "/produits/searchListProduitsByScategoryId", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Produit par scategorie",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Produit par scategorie", responseContainer = "List<Produit>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Produit par scategorie / une liste vide")
    })
    public List<Produit> getAllProduitsByScategoryId(@RequestParam("scaId") Long scatId) {
        return produitService.findProductByScateoryId(scatId);
    }

    @GetMapping(value = APP_ROOT + "/produits/searchCountProduitsByStock")
    @ApiOperation(value = "Compter le nombre de Produit",
            notes = "Cette méthode permet de compter le nombre de Produit dont la quantity de stock est positif")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le nombre de Produit / zéro")
    })
    public List<?> countNumberOfProduitWithStoc() {
        return produitService.countNumberOfProduitWithStoc();
    }

    @GetMapping(value = APP_ROOT + "/produits/countProduitsByStock")
    public Integer countNumbersOfProductsByStock() {
        int prod = produitService.countNumbersOfProductsByStock();
        System.out.println(prod);
        return prod;
    }

    @GetMapping(value = APP_ROOT + "/produits/countProduitsWhenQStockEqualStockInit")
    @ApiOperation(value = "Compter le nombre de Produit par quantity",
            notes = "Cette méthode permet de compter le nombre de Produit dont la quantity de stock est égale à la quantity initial  ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le nombre de Produit par quantity / zéro")
    })
    public Integer countNumbersOfProductsWhenQStockEqualStockInit() {
        int prodQtstockEqualStockInit = produitService.countNumbersOfProductsWhenQStockEqualStockInit();
        System.out.println(prodQtstockEqualStockInit);
        return prodQtstockEqualStockInit;
    }

    @GetMapping(value = APP_ROOT + "/produits/countProduitsWhenQStockInfStockInit")
    @ApiOperation(value = "Compter le nombre de Produit négatif",
            notes = "Cette méthode permet de compter le nombre de Produit dont la quantity de stock est négatif")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le nombre de Produit négatif / zéro")
    })
    public Integer countNumbersOfProductsWhenQStockInfStockInit() {
        int prodQtstockInfStockInit = produitService.countNumbersOfProductsWhenQStockInfStockInit();
        System.out.println(prodQtstockInfStockInit);
        return prodQtstockInfStockInit;
    }

    @PostMapping(value = APP_ROOT + "/produits/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un Produit",
            notes = "Cette méthode permet d'enregistrer et modifier un Produit", response = Produit.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Le Produit a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun Produit  crée / modifié")

    })
    public ResponseEntity<Produit> saveProduit(@RequestBody Produit produit) {
        if (produitService.findByReference(produit.getReference()) != null) {
            return new ResponseEntity<>(produit, HttpStatus.BAD_REQUEST);
        }
        produitService.saveProduit(produit);
        return new ResponseEntity<>(produit, HttpStatus.CREATED);
    }

    @PutMapping(value = APP_ROOT + "/produits/update/{prodId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier un Produit par son ID",
            notes = "Cette méthode permet de modifier un Produit par son ID", response = Produit.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Produit a été modifié avec succès"),
            @ApiResponse(code = 400, message = "Aucun Produit n'a été modifié")

    })
    public ResponseEntity<Produit> updateProduit(@PathVariable(value = "prodId") Long prodId, @RequestBody Produit produit) {
        produit.setId(prodId);
        return new ResponseEntity<>(produitService.updateProduit(prodId, produit), HttpStatus.OK);
    }

    @DeleteMapping(value = APP_ROOT + "/produits/delete/{id}")
    @ApiOperation(value = "Supprimer un Produit par son ID",
            notes = "Cette méthode permet de supprimer un Produit par son ID", response = Produit.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Produit a été supprimé")
    })
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") Long prodId) {
        produitService.deleteProduit(prodId);
        return ResponseEntity.ok().build();

    }

    @GetMapping(value = APP_ROOT + "/produits/createPdf")
    @ApiOperation(value = "Générer un PDf",
            notes = "Cette méthode permet de générer la liste des Produits sous format Pdf")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Pdf a été supprimé")
    })
    public void createPdf(HttpServletRequest request, HttpServletResponse response) {
        List<Produit> produitList = produitService.findAllProduits();
        boolean isFlag = pdfService.createPdfProduits(produitList, context, request, response);
        // boolean isFlag = produitService.createPdf(produitList, context, request, response);

        if (isFlag) {
            String fullPath = request.getServletContext().getRealPath("/resources/reports/" + "articles" + ".pdf");
            filedownload(fullPath, response, "articles.pdf");
        }
    }

    @PostMapping(value = APP_ROOT + "/produits/upload")
    @ApiOperation(value = "Importer un Excel",
            notes = "Cette méthode permet d'importer la liste de produits contenu dans un fichier Excel vers la BD")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Fichier Excel a été importé")
    })
    public ResponseEntity<ResponseMessage> uploadExcel(@RequestParam("file") MultipartFile file) {
        String message;
        if (ExcelUtils.isExcelFile(file)) {
            try {
                excelService.store(file);
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

    @GetMapping(value = APP_ROOT + "/produits/download/articles.xlsx")
    @ApiOperation(value = "Exporter la liste de produit",
            notes = "Cette méthode permet d'exporter la liste de produits de la BD vers un fichier Excel")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Fichier Excel a été télécharger")
    })
    public ResponseEntity<InputStreamResource> excelProduitsReport() throws IOException {
        List<Produit> produits = produitService.findAllProduits();

        ByteArrayInputStream in = ExcelUtils.produitsToExcel(produits);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=articles.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }

    @PostMapping(value = APP_ROOT + "/produits/createProduitWithBarcode", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un Produit avec code-barre",
            notes = "Cette méthode permet d'enregistrer et modifier un Produit", response = Produit.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Le Produit a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun Produit  crée / modifié")

    })
    public ResponseEntity<Produit> saveProduitWithBarCode(@RequestBody Produit produit) throws Exception {

        return ResponseEntity.ok(produitService.saveProductWithBarcode(produit));

    }

    @GetMapping(value = APP_ROOT + "/produits/searchProduitByBarCode/{barcode}")
    @ApiOperation(value = "chercher un produit par son code-barre",
            notes = "Cette méthode permet de chercher et renvoyer un Produit par son code-barre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Produit a été trouvé")
    })
    public ResponseEntity<Produit> getProduitByBarCode(@PathVariable(value = "barcode") String barcode) {

        Produit produitInfo = produitService
                .findProductByBarcode(barcode)
                .orElseThrow(() ->
                        new ResourceNotFoundException(barcode + " NOT Found!"));

        return ResponseEntity.ok().body(produitInfo);
    }

 /*   @PostMapping(value = APP_ROOT + "/produits/createProduitWithQrcode", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un Produit avec qrCode",
            notes = "Cette méthode permet d'enregistrer et modifier un Produit avec qrCode", response = Produit.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Le Produit a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun Produit  crée / modifié")

    })
    public ResponseEntity<Produit> saveProduitWithQrCode(@RequestBody Produit produit) throws Exception {
        return ResponseEntity.ok(produitService.saveProductWithQrcode(produit));
    }*/

 /*   @GetMapping(value = APP_ROOT + "/produits/searchProduitByQrCode/{qrCode}")
    @ApiOperation(value = "chercher un produit par son qrcode",
            notes = "Cette méthode permet de chercher et renvoyer un Produit par son qrcode")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Produit a été trouvé avec le qrcode indiqué")
    })
    public ResponseEntity<Produit> getProduitByQrCode(@PathVariable("qrCode") String qrCode) {
        Produit produitInfo = produitService
                .findProductByQrcode(qrCode)
                .orElseThrow(() ->
                        new ResourceNotFoundException(qrCode + " NOT Found!"));

        return ResponseEntity.ok().body(produitInfo);
    }*/


}
