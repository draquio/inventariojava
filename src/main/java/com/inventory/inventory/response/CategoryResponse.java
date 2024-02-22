package com.inventory.inventory.response;
import com.inventory.inventory.model.Category;
import lombok.Data;
import java.util.List;

@Data
public class CategoryResponse {
    private List<Category> category;
}
