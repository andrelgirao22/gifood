package br.com.alg.giraofoodapi.domain.repository;


import br.com.alg.giraofoodapi.domain.model.Produto;
import br.com.alg.giraofoodapi.domain.model.Restaurante;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepository extends CustomJpaRepository<Produto, Long>, ProdutoRepositoryQueries {

    @Query("from Produto p where p.restaurante=:restaurante and p.ativo=true")
    List<Produto> findAtivosByRestaurantes(Restaurante restaurante);

}
