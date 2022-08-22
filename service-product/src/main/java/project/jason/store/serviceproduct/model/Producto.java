package project.jason.store.serviceproduct.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbl_products")
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Producto{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre no debe de estar vacio")
    private String nombre;
    private String descripcion;

    @Positive(message = "El stock debe de ser mayor que cero")
    @NotNull(message = "El stock no debe de estar vacio")
    private Double stock;

    @NotNull(message = "El precio no debe de estar vacio")
    private Double precio;

    private String status;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_at")
    private Date createAt;

    @NotNull(message = "La categoria no debe de ser vacia")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Categoria categoria;

    @PrePersist
    public void prePersist(){
        this.createAt = new Date();
    }

}
