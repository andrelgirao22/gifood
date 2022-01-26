package br.com.alg.giraofoodapi.domain.exception;

public class UsuarioNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public UsuarioNaoEncontradaException(String message) {
        super(message);
    }

    public UsuarioNaoEncontradaException(Long id) {
        this(String.format("Usuário não encontrado %d", id));
    }
}