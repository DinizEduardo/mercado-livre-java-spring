package br.com.zup.mercadolivre.mercadoLivre.controller;
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

import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureDataJpa
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

        String json = "{\"email\": \"\", \"senha\": \"\"}";

        performRequest(uri, json, 400);

    }

    @Test
    public void deveriaRetornarStatus400ComEmailInvalido() throws Exception {
        URI uri = new URI("/clientes");

        String json = "{\"email\": \"invalido\", \"senha\": \"123456\"}";

        performRequest(uri, json, 400);
    }

    @Test
    public void deveriaRetornarStatus400ComSenhaPequena() throws  Exception {
        URI uri = new URI("/clientes");

        String json = "{\"email\": \"email@email.com\", \"senha\": \"12345\"}";

        performRequest(uri, json, 400);
    }

    @Test
    public void deveriaCadastrarUsuarioComDadosValidos() throws Exception {
        URI uri = new URI("/clientes");

        String json = "{\"email\": \"email@email.com\", \"senha\": \"123456\"}";

        performRequest(uri, json, 201);
    }

}
