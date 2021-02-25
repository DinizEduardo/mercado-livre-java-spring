package br.com.zup.mercadolivre.mercadoLivre.controller;

import br.com.zup.mercadolivre.mercadoLivre.model.*;
import br.com.zup.mercadolivre.mercadoLivre.model.request.CaracteristicaRequest;
import br.com.zup.mercadolivre.mercadoLivre.model.request.OpiniaoRequest;

import static br.com.zup.mercadolivre.mercadoLivre.utils.MockMvcRequest.performPost;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import br.com.zup.mercadolivre.mercadoLivre.model.request.ProdutoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureDataJpa
@AutoConfigureTestEntityManager
@Transactional
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager manager;

    @Autowired
    ObjectMapper objectMapper;

    private Categoria categoriaMae;

    @BeforeEach
    public void before() {
        Cliente cliente = new Cliente("email@email.com", "123456");
        manager.persist(cliente);

        categoriaMae = new Categoria("testeCategoriaMae");
        manager.persist(categoriaMae);
    }

    private List<Caracteristica> geraCaracteristicas(int n) {
        List<Caracteristica> caracteristicas = new ArrayList<Caracteristica>();
        for (int i = 0; i < n; i++)
            caracteristicas.add(new Caracteristica("Nome" + i, "Valor"));

        return caracteristicas;
    }

    private List<CaracteristicaRequest> geraCaracteristicasRequest(int n) {
        List<CaracteristicaRequest> caracteristicas = new ArrayList<CaracteristicaRequest>();
        for (int i = 0; i < n; i++)
            caracteristicas.add(new CaracteristicaRequest("Nome" + i, "Valor"));

        return caracteristicas;
    }

    @Test
    @WithUserDetails("teste@logado.com")
    public void deveriaCadastrarUmaOpiniaoSobreUmProduto() throws Exception {
        List<Caracteristica> caracteristicas = geraCaracteristicas(3);

        Produto produto = new Produto("Mouse", 10.5, 5, caracteristicas,
                "Um produto top", categoriaMae);

        manager.persist(produto);

        OpiniaoRequest request = new OpiniaoRequest(5, "Titulo opiniao", "Descricao opiniao");

        MvcResult result = performPost(mockMvc, "/produtos/"+produto.getId()+"/opinioes", 200, objectMapper, request);

        List<Opiniao> opinioes = manager.createQuery("SELECT o FROM Opiniao o", Opiniao.class).getResultList();

        assertTrue(opinioes.size() == 1);

        Opiniao opiniao = opinioes.get(0);

        assertAll(
                () -> assertEquals(request.getDescricao(), opiniao.getDescricao()),
                () -> assertEquals(request.getTitulo(), opiniao.getTitulo()),
                () -> assertEquals(request.getNota(), opiniao.getNota())
        );
    }

    @Test
    public void naoDeveriaCadastrarProdutoComUsuarioInexistente() throws Exception {
        List<CaracteristicaRequest> caracteristicas = geraCaracteristicasRequest(3);

        ProdutoRequest request = new ProdutoRequest("mouse", 200, 10,
                caracteristicas, "Descrição mouse", this.categoriaMae.getId());

        performPost(mockMvc, "/produtos", 403, objectMapper, request);

        List<Produto> produtos = manager.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();

        assertTrue(produtos.size() == 0);
    }

    @Test
    @WithMockUser("teste@logado.com")
    public void naoDeveriaCadastrarProdutoSemNome() throws Exception {
        List<CaracteristicaRequest> caracteristicas = geraCaracteristicasRequest(3);

        ProdutoRequest request = new ProdutoRequest("", 200, 10,
                caracteristicas, "Descrição mouse", this.categoriaMae.getId());

        performPost(mockMvc, "/produtos", 400, objectMapper, request);

        List<Produto> produtos = manager.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();

        assertTrue(produtos.size() == 0);
    }

    @Test
    @WithMockUser("teste@logado.com")
    public void naoDeveriaCadastrarProdutoComValorZerado() throws  Exception {
        List<CaracteristicaRequest> caracteristicas = geraCaracteristicasRequest(3);

        ProdutoRequest request = new ProdutoRequest("mouse", 0, 10,
                caracteristicas, "Descrição mouse", this.categoriaMae.getId());

        performPost(mockMvc, "/produtos", 400, objectMapper, request);

        List<Produto> produtos = manager.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();

        assertTrue(produtos.size() == 0);
    }

    @Test
    @WithMockUser("teste@logado.com")
    public void naoDeveriaCadastrarProdutoComQtdNegativa() throws Exception {
        List<CaracteristicaRequest> caracteristicas = geraCaracteristicasRequest(3);

        ProdutoRequest request = new ProdutoRequest("mouse", 100, -1,
                caracteristicas, "Descrição mouse", this.categoriaMae.getId());

        performPost(mockMvc, "/produtos", 400, objectMapper, request);

        List<Produto> produtos = manager.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();

        assertTrue(produtos.size() == 0);
    }
    @Test
    @WithUserDetails("teste@logado.com")
    public void naoDeveriaCadastrarProdutoSemCategoria() throws Exception {
        List<CaracteristicaRequest> caracteristicas = geraCaracteristicasRequest(3);

        ProdutoRequest request = new ProdutoRequest("mouse", 100, 0,
                caracteristicas, "Descrição mouse", this.categoriaMae.getId() + 1);

        performPost(mockMvc, "/produtos", 400, objectMapper, request);

        List<Produto> produtos = manager.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();

        assertTrue(produtos.size() == 0);
    }

    @Test
    @WithUserDetails("teste@logado.com")
    public void naoDeveriaCadastrarProdutoSemCaracteristicas() throws Exception {
        List<CaracteristicaRequest> caracteristicas = new ArrayList<CaracteristicaRequest>();

        ProdutoRequest request = new ProdutoRequest("mouse", 100, 0,
                caracteristicas, "Descrição mouse", this.categoriaMae.getId() + 1);

        performPost(mockMvc, "/produtos", 400, objectMapper, request);

        List<Produto> produtos = manager.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();

        assertTrue(produtos.size() == 0);
    }

    @Test
    @WithUserDetails("teste@logado.com")
    public void deveriaCadastrarUmNovoProduto() throws Exception {
        List<CaracteristicaRequest> caracteristicas = geraCaracteristicasRequest(3);

        ProdutoRequest request = new ProdutoRequest("mouse", 100, 1,
                caracteristicas, "Descrição mouse", this.categoriaMae.getId());

        performPost(mockMvc, "/produtos", 201, objectMapper, request);

        List<Produto> produtos = manager.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();

        assertTrue(produtos.size() == 1);

        Produto produto = produtos.get(0);

        assertAll(
                () -> assertEquals(request.getDescricao(), produto.getDescricao()),
                () -> assertEquals(request.getNome(), produto.getNome()),
                () -> assertEquals(request.getIdCategoria(), produto.getCategoria().getId()),
                () -> assertEquals(request.getQuantidade(), produto.getQuantidade()),
                () -> assertEquals(request.getValor(), produto.getValor())
        );
    }
}
