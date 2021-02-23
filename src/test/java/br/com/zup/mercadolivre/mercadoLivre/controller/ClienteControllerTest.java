package br.com.zup.mercadolivre.mercadoLivre.controller;
import br.com.zup.mercadolivre.mercadoLivre.utils.JsonDataBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureDataJpa
@Transactional
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private void performRequest(URI uri, String json, int status) throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(status));
    }

    @Test
    public void deveriaRetornarStatus400CasoDadosDeCadastroDeClienteInvalidos() throws Exception {
        URI uri = new URI("/clientes");

        String json = new JsonDataBuilder()
                .chaveValor("email", "")
                .chaveValor("senha", "")
                .constroi();

        performRequest(uri, json, 400);

    }

    @Test
    public void deveriaRetornarStatus400ComEmailInvalido() throws Exception {
        URI uri = new URI("/clientes");

        String json = new JsonDataBuilder()
                .chaveValor("email", "semEmail")
                .chaveValor("senha", "123456")
                .constroi();

        performRequest(uri, json, 400);
    }

    @Test
    public void deveriaRetornarStatus400ComSenhaPequena() throws  Exception {
        URI uri = new URI("/clientes");

        String json = new JsonDataBuilder()
                .chaveValor("email", "email@email.com")
                .chaveValor("senha", "12345")
                .constroi();

        performRequest(uri, json, 400);
    }

    @Test
    public void deveriaCadastrarUsuarioComDadosValidos() throws Exception {
        URI uri = new URI("/clientes");

        String json = new JsonDataBuilder()
                .chaveValor("email", "email@email.com")
                .chaveValor("senha", "123456")
                .constroi();

        performRequest(uri, json, 201);
    }

    @Test
    public void naoDeveriaCadastrarUsuarioComEmailDuplicado() throws Exception {
        URI uri = new URI("/clientes");

        String json = new JsonDataBuilder()
                .chaveValor("email", "email@email.com")
                .chaveValor("senha", "123456")
                .constroi();

        performRequest(uri, json, 201);

        performRequest(uri, json, 400);
    }

}