package dev.rianeduardo.estoque.models;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Ativos implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPatrimonio;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "idMaterial")
    private Materiais material;

    private String localizacao;

    private String codigoPatrimonio;

    private String estado;

    private String descricao;
}