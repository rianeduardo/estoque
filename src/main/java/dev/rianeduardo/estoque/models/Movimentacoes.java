package dev.rianeduardo.estoque.models;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Data;
import java.time.*;

@Entity
@Data
public class Movimentacoes implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovimentacao;

    @ManyToOne
    @JoinColumn(name = "idMaterial")
    private Materiais material;

    public enum TipoMovimentacao {
        ENTRADA,
        SAIDA
    }

    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipo;

    private int quantidade;

    private LocalDateTime criadoEm = LocalDateTime.now();

    private String responsavelNif;

    private String observacao;
}
