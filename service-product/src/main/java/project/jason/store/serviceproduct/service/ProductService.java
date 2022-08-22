package project.jason.store.serviceproduct.service;

import project.jason.store.serviceproduct.model.Categoria;
import project.jason.store.serviceproduct.model.Producto;

import java.util.List;

public interface ProductService {
    List<Producto> allProductos();
    Producto getProduct(Long id);
    Producto newProduct(Producto producto);
    Producto updateProduct(Producto producto);
    Producto deleteProduct(Long id);
    List<Producto> findByCategory(Categoria categoria);
    Producto updateStock(Long id, Double quantity);

}
