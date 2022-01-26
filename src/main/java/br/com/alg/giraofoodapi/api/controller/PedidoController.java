package br.com.alg.giraofoodapi.api.controller;

import br.com.alg.giraofoodapi.api.assembler.PedidoModelAssembler;
import br.com.alg.giraofoodapi.api.model.dto.PedidoDTO;
import br.com.alg.giraofoodapi.domain.repository.PedidoRepository;
import br.com.alg.giraofoodapi.domain.service.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private EmissaoPedidoService pedidoService;

    @Autowired
    private PedidoModelAssembler assembler;

    @GetMapping
    public List<PedidoDTO> listar() {
        return assembler.toCollection(repository.findAll());
    }

    @GetMapping("/{id}")
    public PedidoDTO buscar(@PathVariable Long id) {
        return assembler.toDTO(pedidoService.buscar(id));
    }

}
