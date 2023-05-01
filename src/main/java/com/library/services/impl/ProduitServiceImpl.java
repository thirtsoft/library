package com.library.services.impl;

import com.library.entities.Produit;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.ProduitRepository;
import com.library.services.ProduitService;
import com.library.services.StockService;
import com.library.utils.ZxingBarcodeHelper;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Produit> findAllProduitsOrderDesc() {
        return produitRepository.findByOrderByIdDesc();
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
        product.setBarCode(produit.getBarCode());
        product.setDesignation(produit.getDesignation());
        product.setPrixAchat(produit.getPrixAchat());
        product.setPrixVente(produit.getPrixVente());
        product.setPrixDetail(produit.getPrixDetail());
        product.setQtestock(produit.getQtestock());
        product.setStockInitial(produit.getStockInitial());
        product.setScategorie(produit.getScategorie());

        return produitRepository.save(product);
    }

    @Override
    public void deleteProduit(Long prodId) {
        if (!produitRepository.existsById(prodId)) {
            throw new ResourceNotFoundException("Produit that id is" + prodId + "not found");
        }
        produitRepository.deleteById(prodId);
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

    @Override
    public Produit saveProductWithBarcode(Produit produit) throws Exception {
        Produit productResult = produitRepository.save(produit);
        /*
        Produit productResult = new Produit();
        if (produit.getBarCode() != null) {
            productResult = produitRepository.save(produit);

        } else {
            String productBarCodeResult = ZxingBarcodeHelper.generatedBarCodeWithProduct(produit);
            productResult.setBarCode(ZxingBarcodeHelper.generateCodeCommand());
            productResult.setReference(produit.getReference());
            productResult.setDesignation(produit.getDesignation());
            //    productResult.setQrCode(produit.getQrCode());
            productResult.setPrixAchat(produit.getPrixAchat());
            productResult.setPrixVente(produit.getPrixVente());
            productResult.setPrixVente(produit.getPrixVente());
            productResult.setQtestock(produit.getQtestock());
            productResult.setStockInitial(produit.getStockInitial());
            //    productResult.setCreationDate(produit.getCreationDate());
            //    productResult.setLastUpdateDate(produit.getLastUpdateDate());
            productResult.setScategorie(produit.getScategorie());

            produitRepository.save(productResult);
        }*/

        return productResult;
    }


    @Override
    public Optional<Produit> findProductByBarcode(String barCode) {
        return Optional.ofNullable(produitRepository.findByBarCode(barCode))
                .orElse(null);
    }

    @Override
    public List<Produit> findListProductByOrderByDesignationAsc() {
        return produitRepository.findListProductByOrderByDesignationAsc();
    }

    @Override
    public Double capitalDeDepart() {
        List<Produit> productList = produitRepository.findAll();
        double totalCapital = 0;
        for (Produit product : productList) {
            totalCapital += (product.getQtestock() * product.getPrixAchat());
        }
        return totalCapital;
    }

   /* @Override
    public Produit saveProductWithQrcode(Produit produit) throws Exception {
        Produit productResult = new Produit();
        if (produit.getQrCode() != null && !produit.getQrCode().isEmpty()) {
            productResult = produitRepository.save(produit);

        } else {
            String productQrCodeResult = ZxingQrcodeHelper.generatedQrCodeWithProduct(produit);
            productResult.setQrCode(ZxingQrcodeHelper.generateCodeCommand());
            productResult.setBarCode(produit.getBarCode());
            productResult.setReference(produit.getReference());
            productResult.setDesignation(produit.getDesignation());
            productResult.setPrixAchat(produit.getPrixAchat());
            productResult.setPrixVente(produit.getPrixVente());
            productResult.setPrixVente(produit.getPrixVente());
            productResult.setQtestock(produit.getQtestock());
            productResult.setStockInitial(produit.getStockInitial());
            productResult.setCreationDate(produit.getCreationDate());
            productResult.setLastUpdateDate(produit.getLastUpdateDate());
            productResult.setScategorie(produit.getScategorie());

            produitRepository.save(productResult);
        }

        return productResult;
    }
*/
   /* @Override
    public Optional<Produit> findProductByQrcode(String qrCode){
        return Optional.ofNullable(produitRepository.findByQrCode(qrCode))
                .orElse(null);
    }*/


}
