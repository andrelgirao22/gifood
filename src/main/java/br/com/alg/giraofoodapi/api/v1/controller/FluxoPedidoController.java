
package br.com.alg.giraofoodapi.api.v1.controller;

import br.com.alg.giraofoodapi.core.security.CheckSecurity;
import br.com.alg.giraofoodapi.domain.service.FluxoPedidoService;
import br.com.alg.giraofoodapi.api.v1.openapi.controller.FluxoPedidoControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/pedidos/{pedidoId}")
public class FluxoPedidoController implements FluxoPedidoControllerOpenApi {

    @Autowired
    private FluxoPedidoService pedidoService;

    @CheckSecurity.Pedido.PodeGerenciar
    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> confirmar(@PathVariable String pedidoId) {

        pedidoService.confirmar(pedidoId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Pedido.PodeGerenciar
    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> entregar(@PathVariable String pedidoId) {

        pedidoService.entregar(pedidoId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Pedido.PodeGerenciar
    @PutMapping("/cancela")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> cancelar(@PathVariable String pedidoId) {
        pedidoService.cancelar(pedidoId);
        return ResponseEntity.noContent().build();
    }
}
