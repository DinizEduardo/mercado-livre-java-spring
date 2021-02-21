package br.com.zup.mercadolivre.mercadoLivre.model.response;

import br.com.zup.mercadolivre.mercadoLivre.model.Cliente;

import java.time.LocalDateTime;

public class ClienteResponse {

    private Long id;

    private String email;

    private LocalDateTime dataCriacao;

    public ClienteResponse(Cliente cliente) {
        this.email = cliente.getEmail();
        this.id = cliente.getId();
        this.dataCriacao = cliente.getDataCriacao();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
}
