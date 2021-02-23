package br.com.zup.mercadolivre.mercadoLivre.controller;

import br.com.zup.mercadolivre.mercadoLivre.model.Categoria;
import br.com.zup.mercadolivre.mercadoLivre.model.Cliente;
import br.com.zup.mercadolivre.mercadoLivre.utils.JsonDataBuilder;
import org.apache.tomcat.util.json.JSONParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureDataJpa
@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
public class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestEntityManager manager;

    private Categoria categoriaMae;

    @Before
    public void before() {
        Cliente cliente = new Cliente("email@email.com", "123456");
        manager.persist(cliente);

        categoriaMae = new Categoria("testeCategoriaMae");

        manager.persist(categoriaMae);
    }

    @Test
    @WithMockUser(username = "emailemail@email.com", password = "123456")
    public void naoDeveriaCadastrarCategoriaComNomeDuplicado() throws Exception {
        URI uri = new URI("/categorias");

        String json = new JsonDataBuilder()
                .chaveValor("nome", "testeCategoriaMae")
                .constroi();

        performRequest(uri, json, 400);
    }

    @Test
    @WithMockUser(username = "emailemail@email.com", password = "123456")
    public void naoDeveriaCadastrarCategoriaComIdMaeInvalido() throws Exception {
        URI uri = new URI("/categorias");

        String json = new JsonDataBuilder()
                .chaveValor("nome", "Celulares")
                .chaveValor("idCategoriaMae", this.categoriaMae.getId() + 1)
                .constroi();

        performRequest(uri, json, 400);
    }

    @Test
    @WithMockUser(username = "emailemail@email.com", password = "123456")
    public void deveCadastrarUmaCategoriaComCategoriaMae() throws Exception {
        URI uri = new URI("/categorias");

        String jsonComMae = new JsonDataBuilder()
                .chaveValor("nome", "Celulares")
                .chaveValor("idCategoriaMae", this.categoriaMae.getId())
                .constroi();

        performRequest(uri, jsonComMae, 200);
    }

    @Test
    @WithMockUser(username = "email@email.com", password = "123456")
    public void deveCadastrarUmaCategoria() throws Exception{
        String json = new JsonDataBuilder()
                .chaveValor("nome", "Tecnologias")
                .constroi();

        URI uri = new URI("/categorias");

        performRequest(uri, json, 200);
    }



    @Test
    public void naoDeveCadastrarUmaCategoriaSemEstarLogado() throws Exception{
        String json = new JsonDataBuilder()
                .chaveValor("nome", "Tecnologias")
                .constroi();

        URI uri = new URI("/categorias");

        performRequest(uri, json, 403);
    }

    private MvcResult performRequest(URI uri, String json, int status) throws Exception {
        return (MvcResult) mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(status))
                .andReturn();
    }


}
