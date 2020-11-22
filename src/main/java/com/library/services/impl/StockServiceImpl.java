package com.library.services.impl;

import com.library.entities.Stock;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.StockRepository;
import com.library.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Override
    public List<Stock> findListStocks() {
        return stockRepository.findAll();
    }

    @Override
    public Optional<Stock> findStockById(Long stockId) {
        if (!stockRepository.existsById(stockId)) {
            throw new ResourceNotFoundException("Stock N° " + stockId + "not found");
        }

        return stockRepository.findById(stockId);
    }

    @Override
    public Stock findStockByQuantite(int quantite) {
        return stockRepository.findByQuantite(quantite);
    }

    @Override
    public Stock findStockByProductId(Long prodId) {
        return stockRepository.findStockByProductId(prodId);
    }

    @Override
    public List<Stock> findListStockByProductId(Long prodId) {
        return stockRepository.findListStockByProductId(prodId);
    }

    @Override
    public Page<Stock> findAllStocksByPageable(Pageable page) {
        return stockRepository.findAllStocksByPageable(page);
    }

    @Override
    public Page<Stock> findAllStocksByProduitIdByPageable(Long prodId, Pageable pageable) {
        return stockRepository.findStockByProduitIdByPageable(prodId, pageable);
    }

    @Override
    public Stock saveStock(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public Stock updateStock(Long stockId, Stock stock) {
        if (!stockRepository.existsById(stockId)) {
            throw new ResourceNotFoundException("Stock N° " + stockId + "not found");
        }
        Optional<Stock> stockReference = stockRepository.findById(stockId);
        if (!stockReference.isPresent()) {
            throw new ResourceNotFoundException("Stock N° " + stockId + "not found");
        }
        Stock stockResultat = stockReference.get();

        stockResultat.setQuantite(stock.getQuantite());
        stockResultat.setProduit(stock.getProduit());

        return stockRepository.save(stockResultat);
    }

    @Override
    public ResponseEntity<Object> deleteStock(Long stockId) {
        if (!stockRepository.existsById(stockId)) {
            throw new ResourceNotFoundException("Stock N° " + stockId + "not found");
        }
        stockRepository.deleteById(stockId);

        return ResponseEntity.ok().build();
    }
}
