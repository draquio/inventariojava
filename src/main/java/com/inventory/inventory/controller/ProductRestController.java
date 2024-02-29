package com.inventory.inventory.controller;
import com.inventory.inventory.model.Product;
import com.inventory.inventory.response.ProductResponseRest;
import com.inventory.inventory.services.IProductService;
import com.inventory.inventory.util.Util;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1")
public class ProductRestController {
    private final IProductService productService;

    public ProductRestController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponseRest> save(
            @RequestParam("picture") MultipartFile picture,
            @RequestParam("name") String name,
            @RequestParam("price") int price,
            @RequestParam("quantity") int quantity,
            @RequestParam("categoryId") Long categoryId
            ) throws IOException {
        Product product = new Product();
        product.setName(name);
        product.setQuantity(quantity);
        product.setPrice(price);
        product.setPicture(Util.compressZLib(picture.getBytes()));
        return productService.save(product, categoryId);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseRest> searchById(@PathVariable Long id){
        return productService.searchById(id);
    }

}
