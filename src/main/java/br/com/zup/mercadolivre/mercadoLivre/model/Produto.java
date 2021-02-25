package br.com.zup.mercadolivre.mercadoLivre.model;

import br.com.zup.mercadolivre.mercadoLivre.model.response.OpiniaoProdutoResponse;
import br.com.zup.mercadolivre.mercadoLivre.shared.ClienteLogado;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
    private Set<ImagemProduto> imagens;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
    private List<Opiniao> opinioes = new ArrayList<Opiniao>();

    public List<Opiniao> adicionaOpiniao(@NotNull Opiniao opiniao) {
        opinioes.add(opiniao);
        return opinioes;
    }

    public List<Opiniao> getOpinioes() {
        return opinioes;
    }

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

    public Set<ImagemProduto> getImagens() {
        return imagens;
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

    @Deprecated
    public Produto() {
    }

    public boolean pertenceAoCliente(Cliente cliente) {
        return this.cliente.equals(cliente);

        // igual     -> true
        // diferente -> false

    }

    public void associaImagens(Set<String> links) {
        Set<ImagemProduto> imagens = links.stream()
                .map(link -> new ImagemProduto(this, link))
                .collect(Collectors.toSet());

        this.imagens.addAll(imagens);

    }

    public boolean pertenceAoClienteLogado() {
        ClienteLogado logado = (ClienteLogado) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Cliente cliente = logado.get();

        return this.cliente.equals(cliente);
    }

    public List<OpiniaoProdutoResponse> toOpinioesResponse() {

        List<OpiniaoProdutoResponse> response = new ArrayList<>();

        this.opinioes.forEach(o -> {
            response.add(new OpiniaoProdutoResponse(o));
        });

        return response;

    }
}
