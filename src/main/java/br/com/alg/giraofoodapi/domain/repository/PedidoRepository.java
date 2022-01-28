package br.com.alg.giraofoodapi.domain.repository;

import br.com.alg.giraofoodapi.domain.model.Pedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {

    Optional<Pedido> findByCodigo(String codigo);

    @Query("from Pedido p join fetch p.cliente c join fetch p.restaurante r join fetch r.cozinha co")
    List<Pedido> findAll();
}
