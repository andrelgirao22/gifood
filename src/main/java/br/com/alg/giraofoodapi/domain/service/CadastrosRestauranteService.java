package br.com.alg.giraofoodapi.domain.service;


import br.com.alg.giraofoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.alg.giraofoodapi.domain.exception.RestauranteNaoEncontradoException;
import br.com.alg.giraofoodapi.domain.model.*;
import br.com.alg.giraofoodapi.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastrosRestauranteService {

    @Autowired
    private RestauranteRepository repository;

    @Autowired
    private CadastroFormaPagamentoService formaPagamentoService;

    @Autowired
    private CadastroProdutoService produtoService;

    @Autowired
    private CadastroCozinhaService cozinhaService;

    @Autowired
    private CadastroCidadeService cidadeService;

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Cozinha cozinha = cozinhaService.buscar(restaurante.getCozinha().getId());
        restaurante.setCozinha(cozinha);
        Cidade cidade = cidadeService.buscar(restaurante.getEndereco().getCidade().getId());
        restaurante.getEndereco().setCidade(cidade);

        return repository.save(restaurante);
    }

    public Restaurante buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

    @Transactional
    public void desassociarFormaPagamento(Long id, Long formaPagamentoId) {
        Restaurante restaurante = buscar(id);
        FormaPagamento formaPagamento = formaPagamentoService.buscar(formaPagamentoId);
        restaurante.removerFormaPagamento(formaPagamento);
    }

    @Transactional
    public void associarFormaPagamento(Long id, Long formaPagamentoId) {
        Restaurante restaurante = buscar(id);
        FormaPagamento formaPagamento = formaPagamentoService.buscar(formaPagamentoId);
        restaurante.adicionarFormaPagamento(formaPagamento);
    }

    @Transactional
    public void ativar(Long restauranteId) {
        Restaurante restaurante = buscar(restauranteId);
        restaurante.ativar();
    }

    @Transactional
    public void inativar(Long restauranteId) {
        Restaurante restaurante = buscar(restauranteId);
        restaurante.inativar();
    }

    @Transactional
    public void abrirRestaurante(Long id) {
        Restaurante restaurante = buscar(id);
        restaurante.abrirRestaurante();
    }

    @Transactional
    public void fecharRestaurante(Long id) {
        Restaurante restaurante = buscar(id);
        restaurante.fecharRestaurante();
    }
}