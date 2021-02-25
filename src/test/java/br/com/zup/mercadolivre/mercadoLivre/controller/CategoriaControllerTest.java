package br.com.zup.mercadolivre.mercadoLivre.controller;

import br.com.zup.mercadolivre.mercadoLivre.model.Categoria;
import br.com.zup.mercadolivre.mercadoLivre.model.Cliente;
import br.com.zup.mercadolivre.mercadoLivre.model.request.CategoriaRequest;
import br.com.zup.mercadolivre.mercadoLivre.model.response.CategoriaResponse;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.net.URI;
import java.util.List;

import static br.com.zup.mercadolivre.mercadoLivre.utils.MockMvcRequest.performPost;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

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
    private EntityManager manager;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void before() {
        Cliente cliente = new Cliente("email@email.com", "123456");
        manager.persist(cliente);
    }

    @Test
    @WithMockUser(username = "email@email.com", password = "123456")
    public void naoDeveriaCadastrarCategoriaComNomeDuplicado() throws Exception {

        CategoriaRequest request = new CategoriaRequest("categoria nome", null);

        manager.persist(request.toModel(manager));

        performPost(mockMvc, "/categorias", 400, objectMapper, request);

        List<Categoria> categorias = manager.createQuery("SELECT c FROM Categoria c", Categoria.class).getResultList();

        assertTrue(categorias.size() == 1);

        Categoria categoria = categorias.get(0);

        assertAll(
                () -> assertEquals(categoria.getNome(), request.getNome())
        );
    }

    @Test
    @WithMockUser(username = "emailemail@email.com", password = "123456")
    public void naoDeveriaCadastrarCategoriaComIdMaeInvalido() throws Exception {
        CategoriaRequest request = new CategoriaRequest("categoria nome", 1L);

        performPost(mockMvc, "/categorias", 400, objectMapper, request);

        List<Categoria> categorias = manager.createQuery("SELECT c FROM Categoria c", Categoria.class).getResultList();

        assertTrue(categorias.size() == 0);
    }

//
    @Test
    @WithMockUser(username = "emailemail@email.com", password = "123456")
    public void deveCadastrarUmaCategoriaComCategoriaMae() throws Exception {
        Categoria categoria = new Categoria("Categoria mae");
        manager.persist(categoria);

        CategoriaRequest request = new CategoriaRequest("categoria nome", categoria.getId());

        performPost(mockMvc, "/categorias", 200, objectMapper, request);

        List<Categoria> categorias = manager.createQuery("SELECT c FROM Categoria c", Categoria.class).getResultList();

        assertTrue(categorias.size() == 2);
    }

    @Test
    @WithMockUser(username = "email@email.com", password = "123456")
    public void deveCadastrarUmaCategoria() throws Exception{
        CategoriaRequest request = new CategoriaRequest("categoria nome", null);

        performPost(mockMvc, "/categorias", 200, objectMapper, request);

        List<Categoria> categorias = manager.createQuery("SELECT c FROM Categoria c", Categoria.class).getResultList();

        assertTrue(categorias.size() == 1);

        Categoria categoria = categorias.get(0);

        assertAll(
                () -> assertEquals(categoria.getNome(), request.getNome())
        );
    }

//
    @Test
    public void naoDeveCadastrarUmaCategoriaSemEstarLogado() throws Exception{
        CategoriaRequest request = new CategoriaRequest("categoria nome", null);

        performPost(mockMvc, "/categorias", 403, objectMapper, request);

        List<Categoria> categorias = manager.createQuery("SELECT c FROM Categoria c", Categoria.class).getResultList();

        assertTrue(categorias.size() == 0);

    }
}
