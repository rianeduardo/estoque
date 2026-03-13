package dev.rianeduardo.estoque.models;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Categorias implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoria;

    private String nome;
}
