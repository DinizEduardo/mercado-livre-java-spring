package br.com.zup.mercadolivre.mercadoLivre.model.response;

import br.com.zup.mercadolivre.mercadoLivre.model.Categoria;

public class CategoriaResponse {

    private Long id;

    private String nome;

    private Categoria categoria;

    public CategoriaResponse(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
        this.categoria = categoria.getCategoriaMae();
    }

    public CategoriaResponse() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Categoria getCategoria() {
        return categoria;
    }
}
