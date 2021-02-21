package br.com.zup.mercadolivre.mercadoLivre.model.request;

import br.com.zup.mercadolivre.mercadoLivre.model.Cliente;
import br.com.zup.mercadolivre.mercadoLivre.shared.UniqueValue;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ClienteRequest {

    @NotBlank
    @UniqueValue(domainClass = Cliente.class, fieldName = "email")
    @Email
    private String email;

    @NotBlank
    @NotNull
    @Length(min = 6)
    private String senha;

    public ClienteRequest(@NotBlank String email, @NotBlank String senha) {
        this.email = email;
        this.senha = senha;
    }

    @Deprecated
    public ClienteRequest() {
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    @Override
    public String toString() {
        return "ClienteRequest{" +
                "email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }

    public Cliente toModel() {
        return new Cliente(email, senha);
    }
}
