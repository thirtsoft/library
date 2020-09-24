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

    public List<Stock> findListStocks();
    public Optional<Stock> findStockById(Long stockId);
    public Stock findStockByQuantite(int quantite);

    public List<Stock> findListStockByProductId(Long prodId);

    public Page<Stock> findAllStocksByPageable(Pageable page);
    public Page<Stock>findAllStocksByProduitIdByPageable(Long prodId, Pageable pageable);

    public Stock saveStock(Stock stock);
    public Stock updateStock(Long stockId, Stock stock);
    public ResponseEntity<Object> deleteStock(Long stockId);




}
