package br.com.zup.mercadolivre.mercadoLivre.model;

import br.com.zup.mercadolivre.mercadoLivre.model.request.CaracteristicaRequest;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "caracteristicas")
public class Caracteristica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String valor;

    @ManyToOne
    private Produto produto;

    @Deprecated
    public Caracteristica() {
    }

    public Caracteristica(@NotBlank String nome, @NotBlank String valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Caracteristica(CaracteristicaRequest caracteristicaRequest) {
        this.nome = caracteristicaRequest.getNome();
        this.valor = caracteristicaRequest.getValor();
    }
}
