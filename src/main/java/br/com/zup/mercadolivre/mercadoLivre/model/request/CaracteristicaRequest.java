package br.com.zup.mercadolivre.mercadoLivre.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CaracteristicaRequest {
    @NotBlank
    private String nome;

    @NotBlank
    private String valor;

    public CaracteristicaRequest(@NotBlank String nome, @NotBlank String valor) {
        this.nome = nome;
        this.valor = valor;
    }

    @Deprecated
    public CaracteristicaRequest() {
    }

    public String getNome() {
        return nome;
    }

    public String getValor() {
        return valor;
    }
}
