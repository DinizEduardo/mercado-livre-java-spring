package br.com.zup.mercadolivre.mercadoLivre.model;

import br.com.zup.mercadolivre.mercadoLivre.shared.ClienteLogado;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;

@Entity
@Table(name = "opinioes")
public class Opiniao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer nota;

    private String titulo;

    private String descricao;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private Cliente cliente;

    public Opiniao(Integer nota, String titulo, String descricao, Produto produto, Cliente cliente) {
        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
        this.produto = produto;

        if(cliente == null) {
            ClienteLogado logado = (ClienteLogado) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            cliente = logado.get();
        }

        this.cliente = cliente;
    }

    @Deprecated
    public Opiniao() {
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

    public Cliente getCliente() {
        return cliente;
    }
}
