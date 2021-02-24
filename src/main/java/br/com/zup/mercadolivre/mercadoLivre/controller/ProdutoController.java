package br.com.zup.mercadolivre.mercadoLivre.controller;

import br.com.zup.mercadolivre.mercadoLivre.model.Cliente;
import br.com.zup.mercadolivre.mercadoLivre.model.Produto;
import br.com.zup.mercadolivre.mercadoLivre.model.request.NovasImagensRequest;
import br.com.zup.mercadolivre.mercadoLivre.model.request.ProdutoRequest;
import br.com.zup.mercadolivre.mercadoLivre.model.response.ProdutoResponse;
import br.com.zup.mercadolivre.mercadoLivre.shared.ClienteLogado;
import br.com.zup.mercadolivre.mercadoLivre.utils.UploaderFake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private UploaderFake uploaderFake;

    @PostMapping
    @Transactional
    public ResponseEntity<ProdutoResponse> cadastrar(@RequestBody @Valid ProdutoRequest form,
                                                     UriComponentsBuilder uriBuilder) {
        Produto produto = form.toModel(manager);

        manager.persist(produto);

        URI uri = uriBuilder.path("/produtos/{id}").buildAndExpand(produto.getId()).toUri();

        return ResponseEntity.created(uri).body(new ProdutoResponse(produto));
    }

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity<ProdutoResponse> adicionarImagens(@PathVariable("id") Long id,
                                                            @Valid NovasImagensRequest form) {

        Produto produto = manager.find(Produto.class, id);

        if(!produto.pertenceAoClienteLogado()) {
            return ResponseEntity.badRequest().build();
        }

        Set<String> links = uploaderFake.envia(form.getImagens());

        produto.associaImagens(links);

        manager.merge(produto);

        return ResponseEntity.ok(new ProdutoResponse(produto));

    }

    private boolean validaDono(Produto produto) {

        ClienteLogado logado = (ClienteLogado) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Cliente cliente = logado.get();

        return produto.pertenceAoCliente(cliente);
    }

}
