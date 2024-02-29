package com.inventory.inventory.services;

import com.inventory.inventory.model.Product;
import com.inventory.inventory.response.ProductResponseRest;
import org.springframework.http.ResponseEntity;

public interface IProductService {
    public ResponseEntity<ProductResponseRest> save(Product product, Long categoryID);

    public  ResponseEntity<ProductResponseRest> searchById(Long id);
}
