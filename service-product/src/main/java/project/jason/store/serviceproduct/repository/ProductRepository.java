package project.jason.store.serviceproduct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.jason.store.serviceproduct.model.Categoria;
import project.jason.store.serviceproduct.model.Producto;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Producto, Long> {

    List<Producto> findProductoByCategoria(Categoria categoria);
}
