package com.library.controller;

import com.library.entities.Stock;
import com.library.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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


    @PostMapping("/stocks")
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
        return new ResponseEntity<>(stockService.saveStock(stock), HttpStatus.CREATED);
    }

    @PutMapping("/stocks/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable Long stockId, @RequestBody Stock stock) {
        stock.setId(stockId);
        return new ResponseEntity<>(stockService.saveStock(stock), HttpStatus.OK);

    }

    @DeleteMapping("/stocks/{id}")
    public ResponseEntity<?> deleteStock(@PathVariable(value = "id") Long stockId) {
        stockService.deleteStock(stockId);
        return ResponseEntity.ok().build();
    }

}
