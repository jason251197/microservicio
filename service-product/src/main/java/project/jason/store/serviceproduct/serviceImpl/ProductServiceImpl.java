package project.jason.store.serviceproduct.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.jason.store.serviceproduct.model.Categoria;
import project.jason.store.serviceproduct.model.Producto;
import project.jason.store.serviceproduct.repository.ProductRepository;
import project.jason.store.serviceproduct.service.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Producto> allProductos() {
        return this.productRepository.findAll();
    }

    @Override
    public Producto getProduct(Long id) {
        var producto = this.productRepository.findById(id).orElse(null);
        return producto;
    }

    @Override
    public Producto newProduct(Producto producto) {
        var nuevoProducto = this.productRepository.save(producto);
        return nuevoProducto;
    }

    @Override
    public Producto updateProduct(Producto producto) {
        Producto foundProduct = this.productRepository.findById(producto.getId()).orElse(null);
        if (foundProduct == null){
            return null;
        }
        foundProduct.setNombre(producto.getNombre());
        foundProduct.setDescripcion(producto.getDescripcion());
        foundProduct.setStock(producto.getStock());
        foundProduct.setCategoria(producto.getCategoria());
        foundProduct.setPrecio(producto.getPrecio());
        return this.productRepository.save(foundProduct);

    }

    @Override
    public Producto deleteProduct(Long id) {
        Producto foundProduct = this.getProduct(id);
        if (foundProduct == null){
            return null;
        }
        foundProduct.setStatus("DELETED");
        return this.productRepository.save(foundProduct);
    }

    @Override
    public List<Producto> findByCategory(Categoria categoria) {
        return this.productRepository.findProductoByCategoria(categoria);
    }

    @Override
    public Producto updateStock(Long id, Double quantity) {
        Producto foundProduct = this.getProduct(id);
        if (foundProduct == null){
            return null;
        }
        Double stock = quantity + foundProduct.getStock();
        foundProduct.setStock(stock);
        return this.productRepository.save(foundProduct);
    }
}
