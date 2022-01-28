package br.com.alg.giraofoodapi.domain.service;

import br.com.alg.giraofoodapi.domain.exception.NegocioException;
import br.com.alg.giraofoodapi.domain.exception.PedidoNaoEncontradaException;
import br.com.alg.giraofoodapi.domain.model.*;
import br.com.alg.giraofoodapi.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmissaoPedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private CadastrosRestauranteService restauranteService;

    @Autowired
    private CadastroFormaPagamentoService formaPagamentoService;

    @Autowired
    private CadastroProdutoService produtoService;

    @Autowired
    private CadastroUsuarioService usuarioService;

    @Autowired
    private CadastroCidadeService cidadeService;

    public Pedido buscar(String codigo) {
        return repository.findByCodigo(codigo).orElseThrow(() -> new PedidoNaoEncontradaException(codigo));
    }

    @Transactional
    public Pedido emitir(Pedido pedido) {

        validarPedido(pedido);
        validarItens(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calculaValorTotal();

        return repository.save(pedido);
    }

    private void validarPedido(Pedido pedido) {
        Restaurante restaurante = restauranteService.buscar(pedido.getRestaurante().getId());
        FormaPagamento formaPagamento = formaPagamentoService.buscar(pedido.getFormaPagamento().getId());
        Usuario cliente = usuarioService.buscar(pedido.getCliente().getId());
        Cidade cidade = cidadeService.buscar(pedido.getEndereco().getCidade().getId());

        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);
        pedido.setCliente(cliente);
        pedido.getEndereco().setCidade(cidade);

        if(restaurante.naoAceitaFormaPagamento(formaPagamento)) {
            throw new NegocioException(String.format("Forma de pagamento %s não aceita  pelo restaurante", formaPagamento.getId()));
        }
    }

    private void validarItens(Pedido pedido) {
        pedido.getItens().forEach(itemPedido -> {
            Produto produto = produtoService.buscar(itemPedido.getProduto().getId());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            itemPedido.setPrecoUnitario(produto.getPreco());
        });
    }

}
