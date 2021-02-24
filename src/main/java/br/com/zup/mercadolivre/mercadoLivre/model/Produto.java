package br.com.zup.mercadolivre.mercadoLivre.model;

import br.com.zup.mercadolivre.mercadoLivre.shared.ClienteLogado;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private double valor;

    private int quantidade;

    @OneToMany(mappedBy = "produto", cascade = {CascadeType.PERSIST})
    private List<Caracteristica> caracteristicas;

    private String descricao;

    @ManyToOne
    private Categoria categoria;

    private LocalDateTime criacao = LocalDateTime.now();

    @ManyToOne
    private Cliente cliente;

    public Cliente getCliente() {
        return cliente;
    }

    public Produto(String nome,
                   double valor,
                   int quantidade,
                   List<Caracteristica> caracteristicas,
                   String descricao,
                   Categoria categoria) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.caracteristicas = caracteristicas;
        this.descricao = descricao;
        this.categoria = categoria;

        ClienteLogado logado = (ClienteLogado) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        this.cliente = logado.get();

    }

    public Long getId() {
        return id;
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

    public List<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public String getDescricao() {
        return descricao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public LocalDateTime getCriacao() {
        return criacao;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", valor=" + valor +
                ", quantidade=" + quantidade +
                ", caracteristicas=" + caracteristicas +
                ", descricao='" + descricao + '\'' +
                ", categoria=" + categoria +
                ", criacao=" + criacao +
                '}';
    }
}
