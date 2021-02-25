package br.com.zup.mercadolivre.mercadoLivre.model;

import javax.persistence.*;

@Entity
@Table(name = "opinioes")
public class Opiniao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer nota;

    private String titulo;

    private String descricao;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private Cliente cliente;

    public Opiniao(Integer nota, String titulo, String descricao, Produto produto, Cliente cliente) {
        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
        this.produto = produto;
        this.cliente = cliente;
    }

    @Deprecated
    public Opiniao() {
    }

    public Long getId() {
        return id;
    }

    public Integer getNota() {
        return nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
