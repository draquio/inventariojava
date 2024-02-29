package com.inventory.inventory.response;

import com.inventory.inventory.model.Product;
import lombok.Data;

import java.util.List;
@Data
public class ProductResponse {
    List<Product> products;
}
