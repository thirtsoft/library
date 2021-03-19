package com.library.controller;

import com.library.entities.Stock;
import com.library.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/stocks")
    public List<Stock> getAllStocks() {
        return stockService.findListStocks();
    }

    @GetMapping("/stocks/{id}")
    public Optional<Stock> getStockById(@PathVariable(value = "id") Long id) {
        return stockService.findStockById(id);
    }

    @GetMapping("/searchStockByQuantite")
    public Stock getStockByQuantite(@RequestParam(name = "qte") int quantite) {
        return stockService.findStockByQuantite(quantite);
    }

    @GetMapping("/searchStockByProduct")
    public Stock getStockByProduct(@RequestParam("id") Long prodId) {
        return stockService.findStockByProductId(prodId);
    }

    @GetMapping("/searchListStocksByProduitId")
    public List<Stock> getListStocksByProduitId(@RequestParam("id") Long prodId) {
        return stockService.findListStockByProductId(prodId);
    }

    @GetMapping("/searchListVStocksByProduitPageable")
    public Page<Stock> getAllStocksByProduitIdByPageable(@RequestParam(name = "prod") Long prodId,
                                                         @RequestParam(name = "page") int page,
                                                         @RequestParam(name = "size") int size) {
        return stockService.findAllStocksByProduitIdByPageable(prodId, PageRequest.of(page, size));
    }

    @GetMapping("/searchListStocksByPageable")
    public Page<Stock> getAllStocksByPageable(@RequestParam(name = "page") int page,
                                              @RequestParam(name = "size") int size) {
        return stockService.findAllStocksByPageable(PageRequest.of(page, size));
    }

    @PostMapping("/stocks")
    public Stock createStock(@RequestBody Stock stock) {
        return stockService.saveStock(stock);
    }

    @PutMapping("/stocks/{id}")
    public Stock updateStock(@PathVariable Long stockId, @RequestBody Stock stock) {
        stock.setId(stockId);
        return stockService.saveStock(stock);
    }

    @DeleteMapping("/stocks/{id}")
    public ResponseEntity<Object> deleteStock(@PathVariable(value = "id") Long stockId) {
        return stockService.deleteStock(stockId);
    }

}
