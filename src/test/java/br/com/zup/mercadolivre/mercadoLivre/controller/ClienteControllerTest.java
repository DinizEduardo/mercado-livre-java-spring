package br.com.zup.mercadolivre.mercadoLivre.controller;
import br.com.zup.mercadolivre.mercadoLivre.model.Cliente;
import br.com.zup.mercadolivre.mercadoLivre.model.request.ClienteRequest;
import br.com.zup.mercadolivre.mercadoLivre.utils.MockMvcRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.net.URI;
import java.util.List;

import static br.com.zup.mercadolivre.mercadoLivre.utils.MockMvcRequest.performPost;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureDataJpa
@Transactional
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager manager;

    @Test
    public void naoDeveriaCadastrarCasoNaoPasseNenhumEmailENenhumaSenha() throws Exception {
        ClienteRequest request = new ClienteRequest("", "");

        performPost(mockMvc, "/clientes", 400, objectMapper, request);
    }

//
    @Test
    public void deveriaRetornarStatus400ComEmailInvalido() throws Exception {
        ClienteRequest request = new ClienteRequest("sememail", "123456");

        performPost(mockMvc, "/clientes", 400, objectMapper, request);
    }

    @Test
    public void deveriaRetornarStatus400ComSenhaPequena() throws  Exception {
        ClienteRequest request = new ClienteRequest("email@email.com", "12345");

        performPost(mockMvc, "/clientes", 400, objectMapper, request);
    }

    @Test
    public void deveriaCadastrarUsuarioComDadosValidos() throws Exception {
        ClienteRequest request = new ClienteRequest("email@email.com", "123456");

        performPost(mockMvc, "/clientes", 201, objectMapper, request);
    }

    @Test
    public void naoDeveriaCadastrarUsuarioComEmailDuplicado() throws Exception {
        ClienteRequest request = new ClienteRequest("email@email.com", "123456");

        Cliente novo = request.toModel();
        manager.persist(novo);

        performPost(mockMvc, "/clientes", 400, objectMapper, request);
    }
}
