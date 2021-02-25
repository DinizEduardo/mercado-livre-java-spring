package br.com.zup.mercadolivre.mercadoLivre.model.request;

import br.com.zup.mercadolivre.mercadoLivre.model.Caracteristica;
import br.com.zup.mercadolivre.mercadoLivre.model.Categoria;
import br.com.zup.mercadolivre.mercadoLivre.model.Produto;
import br.com.zup.mercadolivre.mercadoLivre.shared.ExistsId;

import javax.persistence.EntityManager;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProdutoRequest {

    @NotBlank
    private String nome;

    @NotNull
    @Min(1)
    private double valor;

    @NotNull
    @Min(0)
    private int quantidade;

    @Size(min = 3)
    private List<CaracteristicaRequest> caracteristicas;

    @NotBlank
    private String descricao;

    @NotNull
    @ExistsId(domainClass = Categoria.class, fieldName = "id")
    private long idCategoria;

    public String getNome() {
        return nome;
    }

    public double getValor() {
        return valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public List<CaracteristicaRequest> getCaracteristicas() {
        return caracteristicas;
    }

    public String getDescricao() {
        return descricao;
    }

    public long getIdCategoria() {
        return idCategoria;
    }

    @Deprecated
    public ProdutoRequest() {
    }

    public ProdutoRequest(@NotBlank String nome, @NotNull @Min(1) double valor, @NotNull @Min(0) int quantidade, @Size(min = 3) List<CaracteristicaRequest> caracteristicas, @NotBlank String descricao, @NotNull long idCategoria) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.caracteristicas = caracteristicas;
        this.descricao = descricao;
        this.idCategoria = idCategoria;
    }

    @Override
    public String toString() {
        return "ProdutoRequest{" +
                "nome='" + nome + '\'' +
                ", valor=" + valor +
                ", quantidade=" + quantidade +
                ", caracteristicas=" + caracteristicas +
                ", descricao='" + descricao + '\'' +
                ", idCategoria=" + idCategoria +
                '}';
    }

    public Produto toModel(EntityManager manager) {
        @NotNull Categoria categoria = manager.find(Categoria.class, idCategoria);

        List<Caracteristica> caracteristicasModel = caracteristicas
                .stream()
                .map(Caracteristica::new)
                .collect(Collectors.toList());

        return new Produto(nome, valor, quantidade,
                caracteristicasModel, descricao, categoria);
    }
}
