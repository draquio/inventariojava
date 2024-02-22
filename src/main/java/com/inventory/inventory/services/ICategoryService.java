package com.inventory.inventory.services;

import com.inventory.inventory.model.Category;
import com.inventory.inventory.response.CategoryResponseRest;
import org.springframework.http.ResponseEntity;

public interface ICategoryService {
    public ResponseEntity<CategoryResponseRest> search();
    public ResponseEntity<CategoryResponseRest> searchById(Long id);
    public ResponseEntity<CategoryResponseRest> save(Category category);
    public ResponseEntity<CategoryResponseRest> update(Category category, Long id);
}
