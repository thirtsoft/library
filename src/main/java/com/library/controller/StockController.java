package com.library.controller;

import com.library.entities.Stock;
import com.library.services.StockService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.library.utils.Constants.APP_ROOT;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping(value = APP_ROOT + "/stocks/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Stock",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Stock", responseContainer = "List<Stock>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Stock / une liste vide")
    })
    public List<Stock> getAllStocks() {
        return stockService.findListStocks();
    }

    @GetMapping(value = APP_ROOT + "/stocks/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Stock par id",
            notes = "Cette méthode permet de chercher une Stock par id", response = Stock.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Stock avec l'id a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Stock n'existe avec cet id pas dans la BD")

    })
    public Optional<Stock> getStockById(@PathVariable(value = "id") Long id) {
        return stockService.findStockById(id);
    }

    @GetMapping(value = APP_ROOT + "/stocks/searchStockByQuantite", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Stock par quantite",
            notes = "Cette méthode permet de chercher une Stock par quantite", response = Stock.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Stock avec la quantité indiquée a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Stock n'existe avec cette quantite pas dans la BD")

    })
    public Stock getStockByQuantite(@RequestParam(name = "qte") int quantite) {
        return stockService.findStockByQuantite(quantite);
    }

    @GetMapping(value = APP_ROOT + "/stocks/searchStockByProduct", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Stock par produit",
            notes = "Cette méthode permet de chercher une Stock par son produit", response = Stock.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Stock a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Stock n'existe avec ce produit pas dans la BD")

    })
    public Stock getStockByProduct(@RequestParam("id") Long prodId) {
        return stockService.findStockByProductId(prodId);
    }

    @GetMapping(value = APP_ROOT + "/stocks/searchListStocksByProduitId", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Stock par produit",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Stock par produit", responseContainer = "List<Stock>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Charge par produit / une liste vide")
    })
    public List<Stock> getListStocksByProduitId(@RequestParam("id") Long prodId) {
        return stockService.findListStockByProductId(prodId);
    }


    @PostMapping(value = APP_ROOT + "/stocks/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un Stock",
            notes = "Cette méthode permet d'enregistrer un Stock", response = Stock.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Le Stock a été crée / modifié"),
            @ApiResponse(code = 400, message = "Aucun Stock  crée / modifié")

    })
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
        return new ResponseEntity<>(stockService.saveStock(stock), HttpStatus.CREATED);
    }

    @PutMapping(value = APP_ROOT + "/stocks/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier un Stock par son ID",
            notes = "Cette méthode permet de modifier un Stock par son ID", response = Stock.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Stock avec cet ID a été modifié"),
            @ApiResponse(code = 400, message = "Aucun Stock  modifié avec cet ID")

    })
    public ResponseEntity<Stock> updateStock(@PathVariable Long stockId, @RequestBody Stock stock) {
        stock.setId(stockId);
        return new ResponseEntity<>(stockService.saveStock(stock), HttpStatus.OK);

    }

    @DeleteMapping(value = APP_ROOT + "/stocks/delete/{id}")
    @ApiOperation(value = "Supprimer un Stock par son ID",
            notes = "Cette méthode permet de supprimer un Stock par son ID", response = Stock.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Stock a été supprimé")
    })
    public ResponseEntity<?> deleteStock(@PathVariable(value = "id") Long stockId) {
        stockService.deleteStock(stockId);
        return ResponseEntity.ok().build();
    }

}
