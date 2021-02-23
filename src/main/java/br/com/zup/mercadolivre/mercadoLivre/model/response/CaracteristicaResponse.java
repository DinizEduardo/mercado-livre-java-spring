package br.com.zup.mercadolivre.mercadoLivre.model.response;

import br.com.zup.mercadolivre.mercadoLivre.model.Caracteristica;

public class CaracteristicaResponse {

    private long id;

    private String nome;

    private String valor;

    public CaracteristicaResponse(long id, String nome, String valor) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
    }

    public CaracteristicaResponse(Caracteristica caracteristica) {
        this.id = caracteristica.getId();
        this.nome = caracteristica.getNome();
        this.valor = caracteristica.getValor();
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getValor() {
        return valor;
    }
}
