package project.jason.store.shoppingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Entity
@Table(name = "tbl_invoide_item")
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class InvoiceItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive(message = "El Stock debe de ser mayor que cero")
    private Double quantity;
    private Double price;

    @Column(name = "product_id")
    private Long productoId;

    @Transient
    private Double subTotal;

    public Double getSubTotal(){
        if (this.price > 0 && this.quantity > 0){
            return this.quantity * this.price;
        }else{
            return (double) 0;
        }
    }
}
