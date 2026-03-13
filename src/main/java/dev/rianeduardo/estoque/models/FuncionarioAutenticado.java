package dev.rianeduardo.estoque.models;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class FuncionarioAutenticado implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String nif;

    private boolean ativo = true;
}