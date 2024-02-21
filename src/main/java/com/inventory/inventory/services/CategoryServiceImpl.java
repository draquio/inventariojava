package com.inventory.inventory.services;

import com.inventory.inventory.dao.ICategoryDao;
import com.inventory.inventory.model.Category;
import com.inventory.inventory.response.CategoryResponseRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService{
    @Autowired
    private ICategoryDao categoryDao;
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> search() {
       CategoryResponseRest response = new CategoryResponseRest();
       try {
           List<Category> category = (List<Category>) categoryDao.findAll();
           response.getCategoryResponse().setCategory(category);
           response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
       } catch (Exception e) {
           response.setMetadata("Respuesta fallida", "-1", "error al consultar");
           e.getStackTrace();
           return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
       }
       return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }
}
