package project.jason.store.customerservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "tbl_customers")
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El numero de documento no puese ser vacio")
    @Size(min = 8, max = 8, message = "El tamaño del documento es 8")
    @Column(name = "number_id",unique = true,length = 8,nullable = false)
    private String numberID;

    @NotEmpty(message = "El nombre no puede ser vacio")
    @Column(name = "first_name",nullable = false)
    private String firstName;

    @NotEmpty(message = "El apellido no puede ser vacio")
    @Column(name = "last_name",nullable = false)
    private String lastName;

    @NotEmpty(message = "El correo no debe de ser vacio")
    @Email(message = "No esta con el formato correcto")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "photo_url")
    private String photoUrl;

    @NotNull(message = "La region no puede estar vacia")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Region region;

    private String state;
}
