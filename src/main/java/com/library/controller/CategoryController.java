package com.library.controller;

import com.library.assembler.CategoryRestAssembler;
import com.library.controller.model.CategoryModel;
import com.library.entities.CategorieCharge;
import com.library.entities.Category;
import com.library.exceptions.ResourceNotFoundException;
import com.library.message.response.ResponseMessage;
import com.library.services.CategoryService;
import com.library.services.ExcelService;
import com.library.services.PdfService;
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

import static com.library.utils.Constants.APP_ROOT;

@RestController
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRestAssembler categoryRestAssembler;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ServletContext context;

    @GetMapping(value = APP_ROOT + "/categories/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des categories",
            notes = "Cette méthode permet de chercher et renvoyer la liste des categories", responseContainer = "List<CategoryModel>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des categories / une liste vide")
    })
    public List<CategoryModel> getAllCategory() {
        return categoryRestAssembler.assembledEntityToModel(categoryService.findAllCategory());
    }

    @GetMapping(value = APP_ROOT + "/categories/allCategoryOrderDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Category",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Category",
            responseContainer = "List<Category>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Category / une liste vide")
    })
    ResponseEntity<List<Category>> getAllCategoryOrderDesc() {
        List<Category> categoryList = categoryService.findAllCategorysOrderDesc();
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

    @GetMapping(value = APP_ROOT + "/categories/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une category par ID",
            notes = "Cette méthode permet de chercher un category par son ID", response = CategoryModel.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La avec l'id ID category a été trouver"),
            @ApiResponse(code = 404, message = "Aucun category n'existe avec cette ID pas dans la BD")

    })
    public ResponseEntity<CategoryModel> getCategoryById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Category category = categoryService.findCategoryById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category that id is" + id + "not found"));
        return ResponseEntity.ok().body(categoryRestAssembler.assembledEntityToModel(category));

    }

    @PostMapping(value = APP_ROOT + "/categories/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une category",
            notes = "Cette méthode permet d'enregistrer une category", response = CategoryModel.class )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "La category a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucune category  crée / modifié")

    })
    public ResponseEntity<CategoryModel> createCategory(@RequestBody Category category) {
        return new ResponseEntity<>(categoryRestAssembler.assembledEntityToModel(categoryService.saveCategory(category)), HttpStatus.CREATED);

    }

    @PutMapping(value = APP_ROOT + "/categories/update/{catId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier une category par son ID",
            notes = "Cette méthode permet de modifier un category par son ID", response = CategoryModel.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La category a été modifié"),
            @ApiResponse(code = 400, message = "Aucun category modifié")
    })
    public ResponseEntity<CategoryModel> updateCategory(@PathVariable(value = "catId") Long catId, @RequestBody Category category) {
        category.setId(catId);
        return new ResponseEntity<>(categoryRestAssembler.assembledEntityToModel(categoryService.updateCategory(catId, category)), HttpStatus.OK);
    }

    @DeleteMapping(value = APP_ROOT +"/categories/delete/{id}")
    @ApiOperation(value = "Supprimer une Category par son ID",
            notes = "Cette méthode permet de supprimer un Category par son ID", response = Category.class )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La Category a été supprimé")
    })
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = APP_ROOT + "/categories/createCategoriePdf")
    @ApiOperation(value = "Générer un pdf",
            notes = "Cette méthode permet de générer un pdf contenant la liste de Category")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pdf a été généré")
    })
    public void createCategoriePdf(HttpServletRequest request, HttpServletResponse response) {
        List<Category> categories = categoryService.findAllCategory();
        boolean isFlag = pdfService.createCategoriesPdf(categories, context, request, response);

        if (isFlag) {
            String fullPath = request.getServletContext().getRealPath("/resources/reports/" + "categories" + ".pdf");
            filedownload(fullPath, response, "categories.pdf");
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

    @PostMapping(value = APP_ROOT + "/categories/uploadCategorie")
    @ApiOperation(value = "Upload file",
            notes = "Cette méthode permet d'importer le contenu d'un file excel et d'envoyer les données vers la BD")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le fichier a été importer")
    })
    public ResponseEntity<ResponseMessage> uploadExcelCategorie(@RequestParam("file") MultipartFile file) {
        String message;
        if (ExcelUtils.isExcelFile(file)) {
            try {
                excelService.storeCategorieFile(file);
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

    @GetMapping(value = APP_ROOT + "/categories/download/categories.xlsx")
    @ApiOperation(value = "Download file",
            notes = "Cette méthode permet d'exporter les données de la BD vers un fichier Excel")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le fichier a été exporté")
    })
    public ResponseEntity<InputStreamResource> excelCategoriesReport() throws IOException {
        List<Category> categories = categoryService.findAllCategory();

        ByteArrayInputStream in = ExcelUtils.CategoriesToExcel(categories);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=categories.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }

}
