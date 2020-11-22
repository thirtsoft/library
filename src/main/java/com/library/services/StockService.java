package com.library.services;

import com.library.entities.Produit;
import com.library.entities.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public interface StockService {

    List<Stock> findListStocks();
    Optional<Stock> findStockById(Long stockId);
    Stock findStockByQuantite(int quantite);

    Stock findStockByProductId(Long prodId);

    List<Stock> findListStockByProductId(Long prodId);

    Page<Stock> findAllStocksByPageable(Pageable page);
    Page<Stock>findAllStocksByProduitIdByPageable(Long prodId, Pageable pageable);

    Stock saveStock(Stock stock);
    Stock updateStock(Long stockId, Stock stock);
    ResponseEntity<Object> deleteStock(Long stockId);




}
