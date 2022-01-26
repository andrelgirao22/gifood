package br.com.alg.giraofoodapi.domain.service;

import br.com.alg.giraofoodapi.domain.exception.EntidadeEmUsoException;
import br.com.alg.giraofoodapi.domain.exception.ProdutoNaoEncontradoException;
import br.com.alg.giraofoodapi.domain.model.Produto;
import br.com.alg.giraofoodapi.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public Produto buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new ProdutoNaoEncontradoException(String.format("Produto %d não encontrado", id)));
    }

    public Produto buscarPeloRestaurante(Long id, Long restauranteId) {
        return repository.findById(id).orElseThrow(() -> new ProdutoNaoEncontradoException(restauranteId, id));
    }

    @Transactional
    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }

    public void remover(Long id) {
        Produto produto = buscar(id);
        try {
            repository.delete(produto);
        }catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException("Produto em uso " + id);
        }
    }

}
