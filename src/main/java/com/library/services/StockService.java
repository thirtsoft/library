package com.library.services;

import com.library.entities.Stock;

import java.util.List;
import java.util.Optional;

public interface StockService {

    List<Stock> findListStocks();

    Optional<Stock> findStockById(Long stockId);

    Stock findStockByQuantite(int quantite);

    Stock findStockByProductId(Long prodId);

    List<Stock> findListStockByProductId(Long prodId);

    Stock saveStock(Stock stock);

    Stock updateStock(Long stockId, Stock stock);


}
