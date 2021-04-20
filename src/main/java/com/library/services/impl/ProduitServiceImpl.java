package com.library.services.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.library.entities.Produit;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.ProduitRepository;
import com.library.services.ProduitService;
import com.library.services.StockService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ProduitServiceImpl implements ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private StockService stockService;

    @Override
    public List<Produit> findAllProduits() {
        return produitRepository.findAll();
    }

    @Override
    public Optional<Produit> findProduitById(Long prodId) {
        if (!produitRepository.existsById(prodId)) {
            throw new ResourceNotFoundException("Produit that id is" + prodId + "is not found");
        }
        return produitRepository.findById(prodId);
    }

    @Override
    public Produit findByReference(String reference) {
        return produitRepository.findByReference(reference);
    }

    @Override
    public Produit findByDesignation(String designation) {
        return produitRepository.findByDesignation(designation);
    }

    @Override
    public Produit findByPrixAchat(Double prixAchat) {
        return produitRepository.findByPrixAchat(prixAchat);
    }

    @Override
    public List<Produit> findListProduitByDesignation(String designation) {
        return produitRepository.findListProduitByDesignation(designation);
    }

    @Override
    public List<Produit> findProductByScateoryId(Long scatId) {
        return produitRepository.findProductByScateoryId(scatId);
    }

    @Override
    public Page<Produit> findAllProduitsByPageable(Pageable page) {
        return produitRepository.findAllProduitsByPageable(page);
    }

    @Override
    public Page<Produit> findProduitByKeyWord(String mc, Pageable pageable) {
        return produitRepository.findProduitByKeyWord(mc, pageable);
    }

	/*
	@Override
	public Produit saveProduit(Long catId, Produit produit) {
		Set<Produit> products = new HashSet<Produit>();

		Category cat = new Category();

		Optional<Category> catbyId = categoryRepository.findById(catId);

		if(!catbyId.isPresent()) { throw new ResourceNotFoundException("Category" +catId + "not found");

		}

		Category category = catbyId.get();

		Produit prod = produitRepository.save(produit); products.add(prod);
		//cat.setProducts(products);

		return prod;
	}

	*/

    @Override
    public Produit saveProduit(Produit produit) {
        Produit productinfo = produitRepository.findByReference(produit.getReference());

        if (productinfo != null) {
            throw new IllegalArgumentException("Cet Article existe déjà");
        }

        return produitRepository.save(produit);

    }

    @Override
    public Produit updateProduit(Long prodId, Produit produit) {
        if (!produitRepository.existsById(prodId)) {
            throw new ResourceNotFoundException("Produit that id is" + prodId + "not found");
        }
        Optional<Produit> prod = produitRepository.findById(prodId);

        if (!prod.isPresent()) {
            throw new ResourceNotFoundException("Produit that id is" + prodId + "not found");

        }
        Produit product = prod.get();

        product.setReference(produit.getReference());
        product.setDesignation(produit.getDesignation());
        product.setPrixAchat(produit.getPrixAchat());
        product.setPrixVente(produit.getPrixVente());
        product.setQtestock(produit.getQtestock());
        product.setStockInitial(produit.getStockInitial());
        product.setTva(produit.getTva());
        product.setPromo(produit.isPromo());
        product.setPhoto(produit.getPhoto());
        product.setScategorie(produit.getScategorie());

        return produitRepository.save(product);
    }

    @Override
    public Produit updateProduit(Produit produit) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteProduit(Long prodId) {
        if (!produitRepository.existsById(prodId)) {
            throw new ResourceNotFoundException("Produit that id is" + prodId + "not found");
        }
        produitRepository.deleteById(prodId);
    }

    @Override
    public boolean createPdf(List<Produit> produits, ServletContext context,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        Document document = new Document(PageSize.A4, 15, 15, 45, 30);
     //   Document document = new Document(PageSize.A4.rotate());
        try {
            String filePath = context.getRealPath("/resources/reports");
            File file = new File(filePath);
            boolean exist = new File(filePath).exists();
            if (!exist) {
                new File(filePath).mkdirs();
            }

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file + "/" + "articles" + ".pdf"));
            document.open();

            Font mainFontEntete = FontFactory.getFont(FontFactory.COURIER, 30, BaseColor.BLACK);
            mainFontEntete.setStyle(Font.BOLD);
            mainFontEntete.setColor(BaseColor.BLUE);
            mainFontEntete.setStyle(Font.UNDERLINE);

            Paragraph paragraphEntete = new Paragraph("AL AMINE", mainFontEntete);
            paragraphEntete.setAlignment(Element.ALIGN_CENTER);
            paragraphEntete.setIndentationLeft(90);
            paragraphEntete.setIndentationRight(90);
            paragraphEntete.setSpacingAfter(6);
            document.add(paragraphEntete);

            Font mainFontTitle = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);

            Paragraph paragraphTitle = new Paragraph("Prestation de Service & Commerce GeneralRC SN ZGR 2016 C233 / NINEA 00058166762P6\n" +
                    "N°Compte CNCAS SN 048 03001 000108318801 J/40N° Compte BNDE SN 169 03001 001000519301/30\n" +
                    "Tél: 77109 18 18 / Email: papeteriealamine@gmail.com\n", mainFontTitle);

            paragraphTitle.setAlignment(Element.ALIGN_CENTER);
            paragraphTitle.setIndentationLeft(50);
            paragraphTitle.setIndentationRight(50);
            paragraphTitle.setSpacingAfter(10);
            document.add(paragraphTitle);

            Font mainFont = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            mainFont.setStyle(Font.UNDERLINE);

            Paragraph paragraph = new Paragraph("LA LISTE DES ARTICLES", mainFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setIndentationLeft(50);
            paragraph.setIndentationRight(50);
            paragraph.setSpacingAfter(10f);
            paragraph.setSpacingBefore(5f);
            document.add(paragraph);

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10);

            Font tableHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
            Font tableBody = FontFactory.getFont("Arial", 12, BaseColor.BLACK);

            PdfPCell reference = new PdfPCell(new Paragraph("Reference", tableHeader));
            reference.setBorderColor(BaseColor.BLACK);
            reference.setPaddingLeft(10);
            reference.setHorizontalAlignment(Element.ALIGN_CENTER);
            reference.setVerticalAlignment(Element.ALIGN_CENTER);
            reference.setBackgroundColor(BaseColor.LIGHT_GRAY);
            reference.setExtraParagraphSpace(5f);
            table.addCell(reference);

            PdfPCell designation = new PdfPCell(new Paragraph("Designation", tableHeader));
            designation.setBorderColor(BaseColor.BLACK);
            designation.setPaddingLeft(10);
            designation.setHorizontalAlignment(Element.ALIGN_CENTER);
            designation.setVerticalAlignment(Element.ALIGN_CENTER);
            designation.setBackgroundColor(BaseColor.LIGHT_GRAY);
            designation.setExtraParagraphSpace(5f);
            table.addCell(designation);

            PdfPCell scategorie = new PdfPCell(new Paragraph("Categorie", tableHeader));
            scategorie.setBorderColor(BaseColor.BLACK);
            scategorie.setPaddingLeft(10);
            scategorie.setHorizontalAlignment(Element.ALIGN_CENTER);
            scategorie.setVerticalAlignment(Element.ALIGN_CENTER);
            scategorie.setBackgroundColor(BaseColor.LIGHT_GRAY);
            scategorie.setExtraParagraphSpace(5f);
            table.addCell(scategorie);

            PdfPCell prixAchat = new PdfPCell(new Paragraph("P.Achat", tableHeader));
            prixAchat.setBorderColor(BaseColor.BLACK);
            prixAchat.setPaddingLeft(10);
            prixAchat.setHorizontalAlignment(Element.ALIGN_CENTER);
            prixAchat.setVerticalAlignment(Element.ALIGN_CENTER);
            prixAchat.setBackgroundColor(BaseColor.LIGHT_GRAY);
            prixAchat.setExtraParagraphSpace(5f);
            table.addCell(prixAchat);


            PdfPCell prixVente = new PdfPCell(new Paragraph("P.Vente", tableHeader));
            prixVente.setBorderColor(BaseColor.BLACK);
            prixVente.setPaddingLeft(10);
            prixVente.setHorizontalAlignment(Element.ALIGN_CENTER);
            prixVente.setVerticalAlignment(Element.ALIGN_CENTER);
            prixVente.setBackgroundColor(BaseColor.LIGHT_GRAY);
            prixVente.setExtraParagraphSpace(5f);
            table.addCell(prixVente);

            PdfPCell prixDetail = new PdfPCell(new Paragraph("P.Detail", tableHeader));
            prixDetail.setBorderColor(BaseColor.BLACK);
            prixDetail.setPaddingLeft(10);
            prixDetail.setHorizontalAlignment(Element.ALIGN_CENTER);
            prixDetail.setVerticalAlignment(Element.ALIGN_CENTER);
            prixDetail.setBackgroundColor(BaseColor.LIGHT_GRAY);
            prixDetail.setExtraParagraphSpace(5f);
            table.addCell(prixDetail);

            PdfPCell stock = new PdfPCell(new Paragraph("Stock", tableHeader));
            stock.setBorderColor(BaseColor.BLACK);
            stock.setPaddingLeft(10);
            stock.setHorizontalAlignment(Element.ALIGN_CENTER);
            stock.setVerticalAlignment(Element.ALIGN_CENTER);
            stock.setBackgroundColor(BaseColor.LIGHT_GRAY);
            stock.setExtraParagraphSpace(5f);
            table.addCell(stock);

            for (Produit prod : produits) {
                PdfPCell referenceValue = new PdfPCell(new Paragraph(prod.getReference(), tableBody));
                referenceValue.setBorderColor(BaseColor.BLACK);
                referenceValue.setPaddingLeft(4);
                referenceValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                referenceValue.setVerticalAlignment(Element.ALIGN_MIDDLE);
                referenceValue.setBackgroundColor(BaseColor.WHITE);
                referenceValue.setExtraParagraphSpace(5f);
                table.addCell(referenceValue);

                PdfPCell designationValue = new PdfPCell(new Paragraph(prod.getDesignation(), tableBody));
                designationValue.setBorderColor(BaseColor.BLACK);
                designationValue.setPaddingLeft(10);
                designationValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                designationValue.setVerticalAlignment(Element.ALIGN_CENTER);
                designationValue.setBackgroundColor(BaseColor.WHITE);
                designationValue.setExtraParagraphSpace(5f);
                table.addCell(designationValue);

                PdfPCell scategorieValue = new PdfPCell(new Paragraph(prod.getScategorie().getLibelle(), tableBody));
                scategorieValue.setBorderColor(BaseColor.BLACK);
                scategorieValue.setPaddingLeft(10);
                scategorieValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                scategorieValue.setVerticalAlignment(Element.ALIGN_CENTER);
                scategorieValue.setBackgroundColor(BaseColor.WHITE);
                scategorieValue.setExtraParagraphSpace(5f);
                table.addCell(scategorieValue);

                PdfPCell prixAchatValue = new PdfPCell(new Paragraph(prod.getPrixAchat().toString(), tableBody));
                prixAchatValue.setBorderColor(BaseColor.BLACK);
                prixAchatValue.setPaddingLeft(10);
                prixAchatValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                prixAchatValue.setVerticalAlignment(Element.ALIGN_CENTER);
                prixAchatValue.setBackgroundColor(BaseColor.WHITE);
                prixAchatValue.setExtraParagraphSpace(5f);
                table.addCell(prixAchatValue);

                PdfPCell prixVenteValue = new PdfPCell(new Paragraph(prod.getPrixVente().toString(), tableBody));
                prixVenteValue.setBorderColor(BaseColor.BLACK);
                prixVenteValue.setPaddingLeft(10);
                prixVenteValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                prixVenteValue.setVerticalAlignment(Element.ALIGN_CENTER);
                prixVenteValue.setBackgroundColor(BaseColor.WHITE);
                prixVenteValue.setExtraParagraphSpace(5f);
                table.addCell(prixVenteValue);

                PdfPCell prixDetailValue = new PdfPCell(new Paragraph(prod.getPrixDetail().toString(), tableBody));
                prixDetailValue.setBorderColor(BaseColor.BLACK);
                prixDetailValue.setPaddingLeft(10);
                prixDetailValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                prixDetailValue.setVerticalAlignment(Element.ALIGN_CENTER);
                prixDetailValue.setBackgroundColor(BaseColor.WHITE);
                prixDetailValue.setExtraParagraphSpace(5f);
                table.addCell(prixDetailValue);

                PdfPCell stockValue = new PdfPCell(new Paragraph(String.valueOf(prod.getQtestock()), tableBody));
                stockValue.setBorderColor(BaseColor.BLACK);
                stockValue.setPaddingLeft(10);
                stockValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                stockValue.setVerticalAlignment(Element.ALIGN_CENTER);
                stockValue.setBackgroundColor(BaseColor.WHITE);
                stockValue.setExtraParagraphSpace(5f);
                table.addCell(stockValue);

            }

            document.add(table);
            document.close();
            writer.close();
            return true;

        } catch (Exception e) {
            return false;
        }

    }


    public boolean createExcel(List<Produit> produitList, ServletContext context, HttpServletRequest request,
                               HttpServletResponse response) {
        String filePath = context.getRealPath("/resources/reports");
        File file = new File(filePath);
        boolean exists = new File(filePath).exists();
        if (!exists) {
            new File(filePath).mkdirs();
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(file + "/" + "articles" + ".xlsx");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet workSheet = workbook.createSheet("Articles");
            workSheet.setDefaultColumnWidth(30);

            HSSFCellStyle headerCellStyle = workbook.createCellStyle();
            //headerCellStyle.setFillForegroundColor(HSSFColor.BLUE.index);
            //headerCellStyle.setFillPattern();

            HSSFRow headerRow = workSheet.createRow(0);

            HSSFCell reference = headerRow.createCell(0);
            reference.setCellValue("Reference");
            reference.setCellStyle(headerCellStyle);

            HSSFCell designation = headerRow.createCell(1);
            designation.setCellValue("Designation");
            designation.setCellStyle(headerCellStyle);

			/*
			HSSFCell scategorie = headerRow.createCell(2);
			scategorie.setCellValue("Scategorie");
			scategorie.setCellStyle(headerCellStyle);

			HSSFCell categorie = headerRow.createCell(3);
			categorie.setCellValue("Categorie");
			categorie.setCellStyle(headerCellStyle);

			*/
            int i = 1;
            for (Produit prod : produitList) {
                HSSFRow bodyRow = workSheet.createRow(i);

                HSSFCellStyle bodyCellStyle = workbook.createCellStyle();
                //bodyCellStyle.setFillBackgroundColor(HSSFColor.);

                HSSFCell referenceValue = bodyRow.createCell(0);
                referenceValue.setCellValue(prod.getReference());
                referenceValue.setCellStyle(bodyCellStyle);

                HSSFCell designationValue = bodyRow.createCell(1);
                designationValue.setCellValue(prod.getDesignation());
                designationValue.setCellStyle(bodyCellStyle);
				/*
				HSSFCell scategorieValue = bodyRow.createCell(2);
				scategorieValue.setCellValue(prod.getScategorie().getLibelle());
				scategorieValue.setCellStyle(bodyCellStyle);

				HSSFCell categorieValue = bodyRow.createCell(3);
				categorieValue.setCellValue(prod.getCategorie().getDesignation());
				categorieValue.setCellStyle(bodyCellStyle);

				*/
                i++;

            }

            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();

            return true;

        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public ResponseEntity<List<Produit>> importExcelFile(MultipartFile files) {

        return null;

    }

    @Override
    public List<?> countNumberOfProduitWithStoc() {
        return produitRepository.countNumberOfProduitWithStoc();
    }

    @Override
    public Integer countNumbersOfProductsByStock() {
        return produitRepository.countNumbersOfProductsByStock();
    }

    @Override
    public Integer countNumbersOfProductsWhenQStockEqualStockInit() {
        return produitRepository.countNumbersOfProductsWhenQStockEqualStockInit();
    }

    @Override
    public Integer countNumbersOfProductsWhenQStockInfStockInit() {
        return produitRepository.countNumbersOfProductsWhenQStockInfStockInit();
    }


}
