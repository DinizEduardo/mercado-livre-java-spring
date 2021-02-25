package br.com.zup.mercadolivre.mercadoLivre.model.response;

import br.com.zup.mercadolivre.mercadoLivre.model.Opiniao;

import java.util.ArrayList;
import java.util.List;

public class OpiniaoProdutoResponse {

    private Long id;

    private Integer nota;

    private String titulo;

    private String descricao;

    private ClienteResponse cliente;

    @Deprecated
    public OpiniaoProdutoResponse() {
    }

    public OpiniaoProdutoResponse(Opiniao opiniao) {
        this.cliente = new ClienteResponse(opiniao.getCliente());
        this.descricao = opiniao.getDescricao();
        this.id = opiniao.getId();
        this.nota = opiniao.getNota();
        this.titulo = opiniao.getTitulo();
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

    public ClienteResponse getCliente() {
        return cliente;
    }
}
