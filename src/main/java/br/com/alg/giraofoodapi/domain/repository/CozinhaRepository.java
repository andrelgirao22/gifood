package br.com.alg.giraofoodapi.domain.repository;

import br.com.alg.giraofoodapi.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {
    public List<Cozinha> findByNome(String name);

    public List<Cozinha> findByNomeContaining(String name);
}
