package com.library.controller;

import com.library.assembler.CategoryRestAssembler;
import com.library.controller.model.CategoryModel;
import com.library.entities.Category;
import com.library.exceptions.ResourceNotFoundException;
import com.library.message.ResponseMessage;
import com.library.services.CategoryService;
import com.library.services.ExcelService;
import com.library.utils.ExcelUtils;
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
import java.util.List;
import java.util.Locale;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
//@RequestMapping("/alAmine")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRestAssembler categoryRestAssembler;

    @Autowired
    private ExcelService excelService;
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ServletContext context;

    @GetMapping("/categories")
    //@GetMapping
    public List<CategoryModel> getAllCategory() {
        return categoryRestAssembler.assembledEntityToModel(categoryService.findAllCategory());
    }

    @GetMapping("/categories/{id}")
    //@GetMapping("/{id}")
    public ResponseEntity<CategoryModel> getCategoryById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Category category = categoryService.findCategoryById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category that id is" + id + "not found"));
        return ResponseEntity.ok().body(categoryRestAssembler.assembledEntityToModel(category));

    }

    /*@GetMapping("/searchCategoryByCode")
    public CategoryModel getCategoryByCode(@RequestParam(value = "code") String code) {
        return categoryRestAssembler.assembledEntityToModel(categoryService.findByCode(code));
    }

    @GetMapping("/searchListCategoryByCode")
    public List<CategoryModel> getAllCategoryByCode(@RequestParam(value = "c") String code) {
        return categoryRestAssembler.assembledEntityToModel(categoryService.ListCategoryByDesignation("%" + code + "%"));
    }

    @GetMapping("/searchCategoryByDesignation")
    public CategoryModel getCategoryByDesignation(@RequestParam(value = "des") String designation) {
        return categoryRestAssembler.assembledEntityToModel(categoryService.findByDesignation(designation));
    }*/

    /*@GetMapping("/searchListCategoryByDesignation")
    public List<CategoryModel> getAllCategoryByDesignation(@RequestParam(value = "des") String designation) {
        return categoryRestAssembler.assembledEntityToModel(categoryService.ListCategoryByDesignation("%" + designation + "%"));
    }

    @GetMapping("/searchListCategoryByPageable")
    public Page<Category> getAllCategoryByPageable(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
        return categoryService.findAllCategoryByPage(PageRequest.of(page, size));
    }*/

    @PostMapping("/categories")
    //@PostMapping
    public ResponseEntity<CategoryModel>  createCategory(@RequestBody Category category) {
        return new ResponseEntity<>(categoryRestAssembler.assembledEntityToModel(categoryService.saveCategory(category)), HttpStatus.OK);

    }

    @PutMapping("/categories/{catId}")
    //@PutMapping("/{catId}")
    public ResponseEntity<CategoryModel> updateCategory(@PathVariable(value = "catId") Long catId, @RequestBody Category category) {
        category.setId(catId);
        return new ResponseEntity<>(categoryRestAssembler.assembledEntityToModel(categoryService.updateCategory(catId, category)), HttpStatus.OK);
    }

    @DeleteMapping("/categories/{id}")
    //@DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/categories/createCategoriePdf")
    public void createCategoriePdf(HttpServletRequest request, HttpServletResponse response) {
        List<Category> categories = categoryService.findAllCategory();
        boolean isFlag = categoryService.createCategoriePdf(categories, context, request, response);

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

    @PostMapping(value = "/categories/uploadCategorie")
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

    @GetMapping(value = "/categories/download/categories.xlsx")
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
