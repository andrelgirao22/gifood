package br.com.alg.giraofoodapi.domain.exception;

public class PedidoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public PedidoNaoEncontradaException(String pedidoId) {
        super(String.format("Não existe um pedido com código %s", pedidoId));
    }
}