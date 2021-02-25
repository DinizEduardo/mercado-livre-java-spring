package br.com.zup.mercadolivre.mercadoLivre.shared.security;

import br.com.zup.mercadolivre.mercadoLivre.model.Cliente;
import br.com.zup.mercadolivre.mercadoLivre.model.request.ClienteRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;

import static br.com.zup.mercadolivre.mercadoLivre.utils.MockMvcRequest.performPost;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureDataJpa
@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
public class ClienteAuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestEntityManager manager;

    @Autowired
    ObjectMapper objectMapper;


    @BeforeEach
    public void before() {
        Cliente cliente = new Cliente("email@email.com", "123456");
        manager.persist(cliente);
    }

//
//
//    // Valida autenticador
//
    @Test
    public void deveriaRetornarStatus400LogandoSemASenha() throws Exception {
        ClienteRequest request = new ClienteRequest("email@email.com", "");

        performPost(mockMvc, "/auth", 400, objectMapper, request);

    }

    @Test
    public void deveriaRetornarStatus400LogandoSemASenhaESemEmail() throws Exception {
        ClienteRequest request = new ClienteRequest("", "");

        performPost(mockMvc, "/auth", 400, objectMapper, request);

    }

    @Test
    public void deveriaRetornarStatus400LogandoComDadosInvalidos() throws Exception {
        ClienteRequest request = new ClienteRequest("email@email.com", "1234567");

        performPost(mockMvc, "/auth", 400, objectMapper, request);

    }

    @Test
    public void deveriaRetornar200LogandoComDadosValidos() throws Exception {
        ClienteRequest request = new ClienteRequest("email@email.com", "123456");

        performPost(mockMvc, "/auth", 200, objectMapper, request);

    }

}
