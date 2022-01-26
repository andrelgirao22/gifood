package br.com.alg.giraofoodapi.domain.service;

import br.com.alg.giraofoodapi.domain.exception.PedidoNaoEncontradaException;
import br.com.alg.giraofoodapi.domain.model.Pedido;
import br.com.alg.giraofoodapi.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmissaoPedidoService {

    @Autowired
    private PedidoRepository repository;

    public Pedido buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new PedidoNaoEncontradaException(id));
    }

}
