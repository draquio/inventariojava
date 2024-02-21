package com.inventory.inventory.controller;
import com.inventory.inventory.model.Category;
import com.inventory.inventory.response.CategoryResponseRest;
import com.inventory.inventory.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CategoryRestController {
    @Autowired
    private ICategoryService service;

    // Get all categories
    @GetMapping("/categories")
    public ResponseEntity<CategoryResponseRest> searchCategories(){
        ResponseEntity<CategoryResponseRest> response = service.search();
        return response;
    }

    // Get category by Id
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseRest> searchCategoriesById(@PathVariable Long id){
        ResponseEntity<CategoryResponseRest> response = service.searchById(id);
        return response;
    }

    // Save category
    @PostMapping("/categories")
    public ResponseEntity<CategoryResponseRest> saveCategory(@RequestBody Category category){
        ResponseEntity<CategoryResponseRest> response = service.save(category);
        return response;
    }
}
