package br.com.zup.mercadolivre.mercadoLivre.controller;

import br.com.zup.mercadolivre.mercadoLivre.model.Categoria;
import br.com.zup.mercadolivre.mercadoLivre.model.Cliente;
import br.com.zup.mercadolivre.mercadoLivre.utils.JsonDataBuilder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
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
    private TestEntityManager manager;

    private Categoria categoriaMae;

    @BeforeEach
    public void before() {
        Cliente cliente = new Cliente("email@email.com", "123456");
        manager.persist(cliente);

        categoriaMae = new Categoria("testeCategoriaMae");

        manager.persist(categoriaMae);
    }

    private ResultActions performRequest(URI uri, String json, int status) throws Exception {
        return mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(status));
    }


    @Test
    @WithUserDetails("teste@logado.com")
    public void deveriaCadastrarUmNovoProduto() throws Exception {
        Cliente cliente = new Cliente("email@email.com", "123456");
        manager.persist(cliente);


        String[] caracteristicas = {
                new JsonDataBuilder()
                        .chaveValor("nome", "altura")
                        .chaveValor("valor", "10cm")
                        .constroi(),
                new JsonDataBuilder()
                        .chaveValor("nome", "largura")
                        .chaveValor("valor", "10cm")
                        .constroi(),
                new JsonDataBuilder()
                        .chaveValor("nome", "diametro")
                        .chaveValor("valor", "10cm")
                        .constroi(),
        };

        String json = new JsonDataBuilder()
                .chaveValor("nome", "mouse")
                .chaveValor("valor", 200)
                .chaveValor("quantidade", 10)
                .chaveValor("descricao", "Descrição do mouse")
                .chaveValor("idCategoria", this.categoriaMae.getId())
                .chaveValor("caracteristicas", caracteristicas)
                .constroi();

        URI uri = new URI("/produtos");

        ResultActions result = performRequest(uri, json, 201);

        result.andExpect(jsonPath("$.nome").value("mouse"));
        result.andExpect(jsonPath("$.categoria.id").value(this.categoriaMae.getId()));
        result.andExpect(jsonPath("$.cliente.id").value(1));

    }

}
