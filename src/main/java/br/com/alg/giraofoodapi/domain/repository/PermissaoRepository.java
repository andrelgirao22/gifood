package br.com.alg.giraofoodapi.domain.repository;


import br.com.alg.giraofoodapi.domain.model.Permissao;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoRepository extends CustomJpaRepository<Permissao, Long> {
}
