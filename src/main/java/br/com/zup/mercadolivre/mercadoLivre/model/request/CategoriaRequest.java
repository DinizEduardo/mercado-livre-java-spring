package br.com.zup.mercadolivre.mercadoLivre.model.request;

import br.com.zup.mercadolivre.mercadoLivre.model.Categoria;
import br.com.zup.mercadolivre.mercadoLivre.shared.ExistsId;
import br.com.zup.mercadolivre.mercadoLivre.shared.UniqueValue;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CategoriaRequest {

    @NotBlank
    @NotNull
    @UniqueValue(domainClass = Categoria.class, fieldName = "nome")
    private String nome;

    @ExistsId(domainClass = Categoria.class, fieldName = "id")
    private Long idCategoriaMae;

    @Deprecated
    public CategoriaRequest() {
    }

    public CategoriaRequest(@NotBlank @NotNull String nome, Long idCategoriaMae) {
        this.nome = nome;
        this.idCategoriaMae = idCategoriaMae;
    }

    public Categoria toModel(EntityManager manager) {
        Categoria categoria = new Categoria(nome);
        if(this.idCategoriaMae != null) {
            @NotNull Categoria categoriaMae = manager.find(Categoria.class, idCategoriaMae);

            Assert.state(categoriaMae != null, "Você está tentando cadastrar uma categoria com id mãe invalido");

            categoria.setCategoriaMae(categoriaMae);
        }

        return categoria;
    }

    public String getNome() {
        return nome;
    }

    public Long getIdCategoriaMae() {
        return idCategoriaMae;
    }
}
