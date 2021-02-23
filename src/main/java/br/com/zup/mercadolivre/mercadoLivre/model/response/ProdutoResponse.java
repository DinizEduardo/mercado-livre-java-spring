package br.com.zup.mercadolivre.mercadoLivre.model.response;

import br.com.zup.mercadolivre.mercadoLivre.model.Caracteristica;
import br.com.zup.mercadolivre.mercadoLivre.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoResponse {

    private String nome;

    private double valor;

    private int quantidade;

    private List<CaracteristicaResponse> caracteristicas = new ArrayList<CaracteristicaResponse>();

    private String descricao;

    private CategoriaResponse categoria;


    public ProdutoResponse(Produto produto) {
        this.nome = produto.getNome();
        this.valor = produto.getValor();
        this.quantidade = produto.getQuantidade();

        for (Caracteristica caracteristica : produto.getCaracteristicas()) {
            this.caracteristicas.add(new CaracteristicaResponse(caracteristica));
        }

        this.descricao = produto.getDescricao();
        this.categoria = new CategoriaResponse(produto.getCategoria());

    }

    public String getNome() {
        return nome;
    }

    public double getValor() {
        return valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public List<CaracteristicaResponse> getCaracteristicas() {
        return caracteristicas;
    }

    public String getDescricao() {
        return descricao;
    }

    public CategoriaResponse getCategoria() {
        return categoria;
    }
}
