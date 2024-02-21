package com.inventory.inventory.dao;
import com.inventory.inventory.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface ICategoryDao extends CrudRepository<Category, Long> {

}
