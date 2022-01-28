package br.com.alg.giraofoodapi.domain.service;

import br.com.alg.giraofoodapi.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService pedidoService;

    @Transactional
    public void confirmar(String pedidoId) {
        Pedido pedido = pedidoService.buscar(pedidoId);
        pedido.confirma();
    }

    @Transactional
    public void entregar(String pedidoId) {
        Pedido pedido = pedidoService.buscar(pedidoId);
        pedido.entregue();
    }

    @Transactional
    public void cancelar(String pedidoId) {
        Pedido pedido = pedidoService.buscar(pedidoId);
        pedido.cancelar();
    }
}
