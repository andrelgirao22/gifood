package br.com.alg.giraofoodapi.domain.exception;

public class FormaPagamentoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public FormaPagamentoNaoEncontradoException(String message) {
        super(message);
    }

    public FormaPagamentoNaoEncontradoException(Long id) {
        this(String.format("Forma de Pagamento não encontrado %d", id));
    }
}