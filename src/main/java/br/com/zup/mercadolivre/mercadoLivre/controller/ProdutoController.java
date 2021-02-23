package br.com.zup.mercadolivre.mercadoLivre.controller;

import br.com.zup.mercadolivre.mercadoLivre.model.Produto;
import br.com.zup.mercadolivre.mercadoLivre.model.request.ProdutoRequest;
import br.com.zup.mercadolivre.mercadoLivre.model.response.ProdutoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping
    @Transactional
    public ResponseEntity<ProdutoResponse> cadastrar(@RequestBody @Valid ProdutoRequest form,
                                                     UriComponentsBuilder uriBuilder) {
        Produto produto = form.toModel(manager);

        manager.persist(produto);

        URI uri = uriBuilder.path("/produtos/{id}").buildAndExpand(produto.getId()).toUri();

        return ResponseEntity.created(uri).body(new ProdutoResponse(produto));
    }

}
