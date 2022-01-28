package br.com.alg.giraofoodapi.api.controller;

import br.com.alg.giraofoodapi.domain.service.FluxoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/{pedidoId}")
public class FluxoPedidoController {

    @Autowired
    private FluxoPedidoService pedidoService;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable String pedidoId) {
        pedidoService.confirmar(pedidoId);
    }

    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregar(@PathVariable String pedidoId) {
        pedidoService.entregar(pedidoId);
    }

    @PutMapping("/cancela")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable String pedidoId) {
        pedidoService.cancelar(pedidoId);
    }
}
