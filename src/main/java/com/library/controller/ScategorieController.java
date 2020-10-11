package com.library.controller;

import java.io.*;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import com.library.entities.Category;
import com.library.message.ResponseMessage;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.entities.Scategorie;
import com.library.services.ScategorieService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class ScategorieController {
	
	@Autowired
	private ScategorieService scategorieService;

	@Autowired
	private ExcelService excelService;
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ServletContext context;
	
	
	@GetMapping("/scategories")
	public List<Scategorie> getAllScategories() {
		return scategorieService.findAllScategories();
	}
	
	@GetMapping("/scategories/{id}")
	public Optional<Scategorie> getScategorieById(@PathVariable(value = "id") Long scatId) {
		return scategorieService.findScategorieById(scatId);
	}
	
	@GetMapping("/searchScategorieByCode")
	public Scategorie getScategorieByCode(@RequestParam(name = "code") String code) {
		return scategorieService.findByCode(code);
	}
	
	@GetMapping("/searchListScategoriesByCode")
	public List<Scategorie> getListScategoriesByCode(@RequestParam(name = "code") String code) {
		return scategorieService.findListScategorieByCode("%"+code+"%");
	}
	
	@GetMapping("/searchProduitByLibelle")
	public Scategorie getScategorieByLibelle(@RequestParam(name = "libelle") String libelle) {
		return scategorieService.findByLibelle(libelle);
	}
	
	@GetMapping("/searchListScategoriesByLibelle")
	public List<Scategorie> getListScategoriesByLibelle(@RequestParam(name = "libelle") String libelle) {
		return scategorieService.findListScategorieByLibelle("%"+libelle+"%");
	}
	
	@GetMapping("/searchListScategoriesByCategoryId/{catId}")
	public List<Scategorie> getListScategoriesByCategoryId(@PathVariable (name="catId") Long catId) {
		return scategorieService.findScategorieByCateoryId(catId);
	}

	@GetMapping("/searchListScategoriesByCategoryId")
	public List<Scategorie> getListScategoriesByCategory(@RequestParam (name="catId") Long catId) {
		return scategorieService.findScategorieByCateoryId(catId);
	}
	
	@GetMapping("/searchListScategoriesByCategoryPageable")
	public Page<Scategorie> getAllScategoriesByPageable(@RequestParam(name = "cat")Long catId,
			@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size) {
		return scategorieService.findAllScategoriesByCategory(catId, PageRequest.of(page, size));
	}
	
	@GetMapping("/searchListScategoriesByPageable")
	public Page<Scategorie> getAllScategoriesByPageable(@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size) {
		return scategorieService.findAllScategorietsByPageable(PageRequest.of(page, size));
	}
	
	@GetMapping("/searchListScategoriesByKeyword")
	public Page<Scategorie> getAllScategoriesByKeyword(@RequestParam(name = "mc") String mc, 
			@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size) {
		return scategorieService.findScategorieByKeyWord("%"+mc+"%", PageRequest.of(page, size));
		
	}
	
	
	@PostMapping("/scategories")
	public Scategorie saveScategorie(@RequestBody Scategorie sCategorie) {
		return scategorieService.saveScategorie(sCategorie);
	}
	
	@PutMapping("/scategories/{ScatId}")
	public Scategorie updateScategorie(@PathVariable Long ScatId, @RequestBody Scategorie sCategorie) {
		sCategorie.setId(ScatId);
		return scategorieService.saveScategorie(sCategorie);	
	}
	
	@DeleteMapping("/scategories/{id}")
	public ResponseEntity<Object> deleteScategorie(@PathVariable(value="id") Long ScatId) {
		return scategorieService.deleteScategorie(ScatId);
	}

	@GetMapping(value = "/createScategoriePdf")
	public void createScategoriePdf(HttpServletRequest request, HttpServletResponse response) {
		List<Scategorie> scategories = scategorieService.findAllScategories();
		boolean isFlag = scategorieService.createScategoriePdf(scategories, context, request, response);

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

	@PostMapping(value = "/uploadScategorie")
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

	@GetMapping(value = "/download/scategories.xlsx")
	public ResponseEntity<InputStreamResource> excelScategoriesReport() throws IOException {
		List<Scategorie> scategories = (List<Scategorie>) scategorieService.findAllScategories();

		ByteArrayInputStream in = ExcelUtils.ScategoriesToExcel(scategories);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=scategories.xlsx");

		return ResponseEntity
				.ok()
				.headers(headers)
				.body(new InputStreamResource(in));
	}



}
