package br.com.alg.giraofoodapi.domain.service;

import br.com.alg.giraofoodapi.domain.model.Pedido;
import br.com.alg.giraofoodapi.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.annotations.ApiIgnore;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService pedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public void confirmar(String pedidoId) {
        Pedido pedido = pedidoService.buscar(pedidoId);
        pedido.confirma();
        pedidoRepository.save(pedido);
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
        pedidoRepository.save(pedido);
    }
}
