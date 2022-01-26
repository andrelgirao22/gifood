package br.com.alg.giraofoodapi.domain.repository;

import br.com.alg.giraofoodapi.domain.model.Pedido;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {
}
