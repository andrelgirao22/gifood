package br.com.alg.giraofoodapi.api.controller;

import br.com.alg.giraofoodapi.api.assembler.PedidoInputDisassembler;
import br.com.alg.giraofoodapi.api.assembler.PedidoModelAssembler;
import br.com.alg.giraofoodapi.api.assembler.PedidoResumoModelAssembler;
import br.com.alg.giraofoodapi.api.model.dto.PedidoDTO;
import br.com.alg.giraofoodapi.api.model.dto.PedidoResumoDTO;
import br.com.alg.giraofoodapi.api.model.input.PedidoInput;
import br.com.alg.giraofoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.alg.giraofoodapi.domain.exception.NegocioException;
import br.com.alg.giraofoodapi.domain.model.Pedido;
import br.com.alg.giraofoodapi.domain.model.Usuario;
import br.com.alg.giraofoodapi.domain.repository.PedidoRepository;
import br.com.alg.giraofoodapi.domain.service.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private EmissaoPedidoService pedidoService;

    @Autowired
    private PedidoResumoModelAssembler assembler;

    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;

    @Autowired
    private PedidoInputDisassembler disassembler;

    @GetMapping
    public List<PedidoResumoDTO> listar() {
        return assembler.toCollection(repository.findAll());
    }

    @GetMapping("/{id}")
    public PedidoDTO buscar(@PathVariable String id) {
        return pedidoModelAssembler.toDTO(pedidoService.buscar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = disassembler.toDomainObject(pedidoInput);

            // TODO pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            novoPedido = pedidoService.emitir(novoPedido);

            return pedidoModelAssembler.toDTO(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

}
