package br.com.alg.giraofoodapi.domain.repository;


import br.com.alg.giraofoodapi.domain.model.FotoProduto;
import br.com.alg.giraofoodapi.domain.model.Produto;
import br.com.alg.giraofoodapi.domain.model.Restaurante;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends CustomJpaRepository<Produto, Long>, ProdutoRepositoryQueries {

    @Query("from Produto p where p.restaurante=:restaurante and p.ativo=true")
    List<Produto> findAtivosByRestaurantes(Restaurante restaurante);

    @Query("select f from FotoProduto f join f.produto p " +
            "where p.restaurante.id = :restauranteId and f.produto.id = :produtoId")
    Optional<FotoProduto> findFotoById(Long restauranteId, Long produtoId);

}
