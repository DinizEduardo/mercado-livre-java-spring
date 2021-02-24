package br.com.zup.mercadolivre.mercadoLivre.shared.security;

import br.com.zup.mercadolivre.mercadoLivre.model.Cliente;
import br.com.zup.mercadolivre.mercadoLivre.utils.JsonDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

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

    @BeforeEach
    public void before() {
        Cliente cliente = new Cliente("email@email.com", "123456");
        manager.persist(cliente);
    }

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


    // Valida autenticador

    @Test
    public void deveriaRetornarStatus400LogandoSemASenha() throws Exception {
        URI uri = new URI("/auth");

        String json = new JsonDataBuilder()
                .chaveValor("email", "email@email.com")
                .chaveValor("senha", "")
                .constroi();

        performRequest(uri, json, 400);
    }

    // Valida autenticador

    @Test
    public void deveriaRetornarStatus400LogandoSemOEmail() throws Exception {
        URI uri = new URI("/auth");

        String json = "{\"email\": \"\", \"senha\": \"123456\"}";

        performRequest(uri, json, 400);
    }

    // Valida autenticador

    @Test
    public void naoDeveriaLogarComDadosIncorretos() throws Exception {
        URI uri = new URI("/auth");

        String json = "{\"email\": \"email@email.com\", \"senha\": \"1234567\"}";

        performRequest(uri, json, 400);
    }

    // Valida autenticador

    @Test
    public void deveriaLogarComDadosCorretos() throws Exception {
        String json = new JsonDataBuilder()
                .chaveValor("email", "email@email.com")
                .chaveValor("senha", "123456")
                .constroi();

        URI uri = new URI("/auth");

        performRequest(uri, json, 200);
    }

    @Test
    public void naoDeveriaLogarComSenhaIncorreta() throws Exception {
        URI uri = new URI("/auth");

        String json = new JsonDataBuilder()
                .chaveValor("email", "email@email.com")
                .chaveValor("senha", "1234567")
                .constroi();

        performRequest(uri, json, 400);
    }

}
