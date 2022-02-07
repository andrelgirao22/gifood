package br.com.alg.giraofoodapi.infrastructure.service.query;

import br.com.alg.giraofoodapi.domain.model.FotoProduto;
import br.com.alg.giraofoodapi.domain.repository.ProdutoRepositoryQueries;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    @Override
    public FotoProduto save(FotoProduto fotoProduto) {
        return manager.merge(fotoProduto);
    }

    @Transactional
    @Override
    public void delete(FotoProduto fotoProduto) {
        manager.remove(fotoProduto);
    }
}
