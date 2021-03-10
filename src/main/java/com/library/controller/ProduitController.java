package com.library.controller;

import com.library.entities.Produit;
import com.library.message.ResponseMessage;
import com.library.services.ExcelService;
import com.library.services.ProduitService;
import com.library.utils.ExcelUtils;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/prodApi")
public class ProduitController {

    @Autowired
    private ProduitService produitService;
    @Autowired
    private ExcelService excelService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ExcelUtils utils;

    @Autowired
    private ServletContext context;

    @GetMapping("/produits")
    public List<Produit> getAllProduits() {
        return produitService.findAllProduits();
    }

    @GetMapping("/produits/{id}")
    public Optional<Produit> getProduitById(@PathVariable(value = "id") Long prodId) {
        return produitService.findProduitById(prodId);
    }

    @GetMapping("/searchProduitByReference")
    public Produit getProduitByReference(@RequestParam(name = "ref") String reference) {
        return produitService.findByReference(reference);
    }

    @GetMapping("/searchProduitByDesignation")
    public Produit getProduitByDesignation(@RequestParam(name = "des") String designation) {
        return produitService.findByDesignation(designation);
    }

    @GetMapping("/searchProduitByPrixAchat")
    public Produit getProduitByPrixAchat(@RequestParam(name = "price") Double prixAchat) {
        return produitService.findByPrixAchat(prixAchat);
    }

    @GetMapping("/searchListProduitsByDesignation")
    public List<Produit> getAllProduitsByDesignation(@RequestParam(name = "des") String designation) {
        return produitService.findListProduitByDesignation("%" + designation + "%");
    }
/*
    @GetMapping("/searchListProduitsByCategoryId")
    public List<Produit> getAllProduitsByCategoryId(@RequestParam("caId") Long catId) {
        return produitService.findProductByCateoryId(catId);
    }
*/
    @GetMapping("/searchListProduitsByScategoryId")
    public List<Produit> getAllProduitsByScategoryId(@RequestParam("scaId") Long scatId) {
        return produitService.findProductByScateoryId(scatId);
    }

    @GetMapping("/searchListProduitsByDate")
    public List<Produit> getAllProduitsByAddDate(@RequestParam("date") Date add_date) {
        return produitService.findListProduitByAddDate(add_date);
    }

    @GetMapping("/searchListProduitsByPageable")
    public Page<Produit> getAllProduitsByPageable(@RequestParam(name = "page") int page,
                                                  @RequestParam(name = "size") int size) {
        return produitService.findAllProduitsByPageable(PageRequest.of(page, size));
    }

    @GetMapping("/searchListProduitsByKeyword")
    public Page<Produit> getAllProduitsByKeyword(@RequestParam(name = "mc") String mc,
                                                 @RequestParam(name = "page") int page,
                                                 @RequestParam(name = "size") int size) {
        return produitService.findProduitByKeyWord("%" + mc + "%", PageRequest.of(page, size));
    }

    @GetMapping("/searchCountProduitsByStock")
    public List<?> countNumberOfProduitWithStoc() {
        return produitService.countNumberOfProduitWithStoc();
    }


    @PostMapping("/produits")
    public ResponseEntity<Produit> saveProduit(@RequestBody Produit produit) {
        if (produitService.findByReference(produit.getReference()) !=null) {
            return new ResponseEntity<Produit>(produit, HttpStatus.BAD_REQUEST);
        }
        produitService.saveProduit(produit);
        return new ResponseEntity<Produit>(produit, HttpStatus.CREATED);
    }

    @PutMapping("/produits/{prodId}")
    public ResponseEntity<Produit>  updateProduit(@PathVariable Long prodId, @RequestBody Produit produit) {
        produit.setId(prodId);
        return new ResponseEntity<>(produitService.updateProduit(prodId, produit), HttpStatus.OK);
    }

    @DeleteMapping("/produits/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") Long prodId) {
        produitService.deleteProduit(prodId);
        return ResponseEntity.ok().build();

    }

    @GetMapping(value = "/createPdf")
    public void createPdf(HttpServletRequest request, HttpServletResponse response) {
        List<Produit> produitList = produitService.findAllProduits();
        boolean isFlag = produitService.createPdf(produitList, context, request, response);

        if (isFlag) {
            String fullPath = request.getServletContext().getRealPath("/resources/reports/" + "articles" + ".pdf");
            filedownload(fullPath, response, "articles.pdf");
        }
    }
/*
    @GetMapping(value = "/createExcel")
    public void createExcel(HttpServletRequest request, HttpServletResponse response) {
        List<Produit> produitList = produitService.findAllProduits();
        boolean isFlag = produitService.createExcel(produitList, context, request, response);

        if (isFlag) {
            String fullPath = request.getServletContext().getRealPath("/resources/reports/" + "articles" + ".xlsx");
            filedownload(fullPath, response, "articles.xlsx");
        }
    }*/

    @PostMapping(value = "/upload")
    public ResponseEntity<ResponseMessage> uploadExcel(@RequestParam("file") MultipartFile file) {
        String message;
        if (utils.isExcelFile(file)) {
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


    @GetMapping(value = "/download/articles.xlsx")
    public ResponseEntity<InputStreamResource> excelProduitsReport() throws IOException {
        List<Produit> produits = (List<Produit>) produitService.findAllProduits();

        ByteArrayInputStream in = utils.produitsToExcel(produits);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=articles.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }


}
