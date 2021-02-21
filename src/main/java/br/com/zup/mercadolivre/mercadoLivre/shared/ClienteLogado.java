package br.com.zup.mercadolivre.mercadoLivre.shared;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.com.zup.mercadolivre.mercadoLivre.model.Cliente;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class ClienteLogado implements UserDetails {

    private Cliente usuario;
    private User springUserDetails;

    public ClienteLogado(@NotNull @Valid Cliente usuario) {
        this.usuario = usuario;
        springUserDetails = new User(usuario.getEmail(), usuario.getSenha(), List.of());
    }



    public Collection<GrantedAuthority> getAuthorities() {
        return springUserDetails.getAuthorities();
    }



    public String getPassword() {
        return springUserDetails.getPassword();
    }



    public String getUsername() {
        return springUserDetails.getUsername();
    }



    public boolean isEnabled() {
        return springUserDetails.isEnabled();
    }



    public boolean isAccountNonExpired() {
        return springUserDetails.isAccountNonExpired();
    }



    public boolean isAccountNonLocked() {
        return springUserDetails.isAccountNonLocked();
    }



    public boolean isCredentialsNonExpired() {
        return springUserDetails.isCredentialsNonExpired();
    }



    public Cliente get() {
        return usuario;
    }

}
