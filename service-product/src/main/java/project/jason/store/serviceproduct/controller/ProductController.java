package project.jason.store.serviceproduct.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import project.jason.store.serviceproduct.model.Categoria;
import project.jason.store.serviceproduct.model.Producto;
import project.jason.store.serviceproduct.service.ProductService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/producto")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<Producto>> allProduct(@RequestParam(name = "categoryId", required = false) Long categoryId){
        List<Producto> productos = new ArrayList<>();
        if(categoryId == null){
            productos = this.productService.allProductos();
            if (productos.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }else{
            productos = this.productService.findByCategory(Categoria.builder().id(categoryId).build());
            if (productos.isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Producto> getPRoduct(@PathVariable Long id){
        var foundProducto = this.productService.getProduct(id);
        if (foundProducto==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundProducto);
    }

    @PostMapping("/new")
    public ResponseEntity<Producto> newProduct(@Valid @RequestBody Producto producto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(bindingResult));
        }
        var newProduct = this.productService.newProduct(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Producto> updateProduct(@PathVariable Long id,@RequestBody Producto producto){
        producto.setId(id);
        var updateProduct = this.productService.updateProduct(producto);
        if (updateProduct == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Producto> deleteProduct(@PathVariable Long id){
        var productDelete = this.productService.deleteProduct(id);
        if (productDelete == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDelete);
    }

    @PutMapping("/stock/{id}")
    public ResponseEntity<Producto> updateStock(@PathVariable Long id, @RequestParam Double quantity){
        var stockUpdate = this.productService.updateStock(id,quantity);
        if (stockUpdate == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(stockUpdate);
    }

    private String formatMessage(BindingResult bindingResult){
        List<Map<String, String>> errores = bindingResult.getFieldErrors().stream()
                .map(err -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
        }).collect(Collectors.toList());
        var errorMensaje = ErroMensaje.builder().code("01").mensajes(errores).build();
        var mapper = new ObjectMapper();
        String jsonString = "";
        try{
            jsonString = mapper.writeValueAsString(errorMensaje);
        }catch (JsonProcessingException jpe){
            jpe.printStackTrace();

        }
        return jsonString;
    }
}
