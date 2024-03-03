package com.inventory.inventory.services;
import com.inventory.inventory.dao.ICategoryDao;
import com.inventory.inventory.dao.IProductDao;
import com.inventory.inventory.model.Category;
import com.inventory.inventory.model.Product;
import com.inventory.inventory.response.ProductResponseRest;
import com.inventory.inventory.util.Util;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {
    private ICategoryDao categoryDao;
    private IProductDao productDao;

    public ProductServiceImpl(ICategoryDao categoryDao, IProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    @Override
    @Transactional
    public ResponseEntity<ProductResponseRest> save(Product product, Long categoryID) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();
        try{
            // Search category to set in the product object
            Optional<Category> category = categoryDao.findById(categoryID);
            if(category.isPresent()){
                product.setCategory(category.get());
            }else{
                response.setMetadata("Respuesta fallida", "-1", "Categoría no encontrada");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
            }
            // Saving the product
            Product productSaved = productDao.save(product);

            if(productSaved != null){
                list.add(productSaved);
                response.getProduct().setProducts(list);
                response.setMetadata("Respuesta ok", "00", "Producto guardado");
            }else{
                response.setMetadata("Respuesta fallida", "-1", "Producto no guardado");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            e.getStackTrace();
            response.setMetadata("Respuesta fallida", "-1", "Error al guardar producto");
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> searchById(Long id) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();
        try{
            // Search product by Id
            Optional<Product> product = productDao.findById(id);
            if(product.isPresent()){
                byte[] imageDescompressed = Util.decompressZLib(product.get().getPicture());
                product.get().setPicture(imageDescompressed);
                list.add(product.get());
                response.getProduct().setProducts(list);
                response.setMetadata("Respuesta ok", "00", "Producto encontrado");
            }else{
                response.setMetadata("Respuesta fallida", "-1", "Producto no encontrado");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            e.getStackTrace();
            response.setMetadata("Respuesta fallida", "-1", "Error al buscar producto");
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> searchByName(String name) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();
        List<Product> listAux = new ArrayList<>();
        try{
            // Search product by Name
            listAux = productDao.findByNameContainingIgnoreCase(name);
            if(!listAux.isEmpty()){
                listAux.forEach(p ->{
                    byte[] imageDescompressed = Util.decompressZLib(p.getPicture());
                    p.setPicture(imageDescompressed);
                    list.add(p);
                });
                response.getProduct().setProducts(list);
                response.setMetadata("Respuesta ok", "00", "Producto encontrado");
            }else{
                response.setMetadata("Respuesta fallida", "-1", "Producto no encontrado");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            e.getStackTrace();
            response.setMetadata("Respuesta fallida", "-1", "Error al buscar producto");
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ProductResponseRest> deleteById(Long id) {
        ProductResponseRest response = new ProductResponseRest();
        try{
            // Delete product by Id
            productDao.deleteById(id);
            response.setMetadata("Respuesta ok", "00", "Producto eliminado");
        } catch (Exception e){
            e.getStackTrace();
            response.setMetadata("Respuesta fallida", "-1", "Error al eliminar el producto");
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> getProducts() {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();
        List<Product> listAux = new ArrayList<>();
        try{
            // Get all products
            listAux = (List<Product>) productDao.findAll();
            if(!listAux.isEmpty()){
                listAux.forEach(p -> {
                    byte[] imageDescompressed = Util.decompressZLib(p.getPicture());
                    p.setPicture(imageDescompressed);
                    list.add(p);
                });
                response.getProduct().setProducts(list);
                response.setMetadata("Respuesta ok", "00", "Productos encontrados");
            }else{
                response.setMetadata("Respuesta fallida", "-1", "Productos no encontrados");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            e.getStackTrace();
            response.setMetadata("Respuesta fallida", "-1", "Error al buscar producto");
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ProductResponseRest> update(Product product, Long categoryId, Long id) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();
        try{
            // Search product to set in the product object
            Optional<Category> category = categoryDao.findById(categoryId);
            if(category.isPresent()){
                product.setCategory(category.get());
            }else{
                response.setMetadata("Respuesta fallida", "-1", "Categoría asociada no encontrada");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
            }

            // Updanting the product
            Optional<Product> productSearched = productDao.findById(id);
            if(productSearched.isPresent()){
                // update data
                productSearched.get().setName(product.getName());
                productSearched.get().setPrice(product.getPrice());
                productSearched.get().setPicture(product.getPicture());
                productSearched.get().setQuantity(product.getQuantity());

                // Saving changes
                Product productToSave = productDao.save(productSearched.get());
                if(productToSave != null){
                    list.add(productToSave);
                    response.getProduct().setProducts(list);
                    response.setMetadata("Respuesta ok", "00", "Producto actualizado");
                }else{
                    response.setMetadata("Respuesta fallida", "-1", "Producto no actualizado");
                    return new ResponseEntity<ProductResponseRest>(response, HttpStatus.BAD_REQUEST);
                }
            }else{
                response.setMetadata("Respuesta fallida", "-1", "Producto para actualizar no encontrado");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            e.getStackTrace();
            response.setMetadata("Respuesta fallida", "-1", "Error al actualizar producto");
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }

}
