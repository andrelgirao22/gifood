package br.com.alg.giraofoodapi.domain.repository;

import br.com.alg.giraofoodapi.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {

    public FotoProduto save(FotoProduto fotoProduto);

    public void delete(FotoProduto fotoProduto);

}
