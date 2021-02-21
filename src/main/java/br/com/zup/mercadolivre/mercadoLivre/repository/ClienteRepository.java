package br.com.zup.mercadolivre.mercadoLivre.repository;

import br.com.zup.mercadolivre.mercadoLivre.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {


}
