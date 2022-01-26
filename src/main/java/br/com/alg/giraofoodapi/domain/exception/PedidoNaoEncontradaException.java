package br.com.alg.giraofoodapi.domain.exception;

public class PedidoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public PedidoNaoEncontradaException(String message) {
        super(message);
    }

    public PedidoNaoEncontradaException(Long pedidoId) {
        this(String.format("Não existe um pedido com código %d", pedidoId));
    }
}