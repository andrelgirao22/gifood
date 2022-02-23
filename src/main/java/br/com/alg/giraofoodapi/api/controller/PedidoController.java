package br.com.alg.giraofoodapi.api.controller;

import br.com.alg.giraofoodapi.api.assembler.PedidoInputDisassembler;
import br.com.alg.giraofoodapi.api.assembler.PedidoModelAssembler;
import br.com.alg.giraofoodapi.api.assembler.PedidoResumoModelAssembler;
import br.com.alg.giraofoodapi.api.model.dto.PedidoDTO;
import br.com.alg.giraofoodapi.api.model.dto.PedidoResumoDTO;
import br.com.alg.giraofoodapi.api.model.input.PedidoInput;
import br.com.alg.giraofoodapi.core.data.PageableTranslator;
import br.com.alg.giraofoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.alg.giraofoodapi.domain.exception.NegocioException;
import br.com.alg.giraofoodapi.domain.model.Pedido;
import br.com.alg.giraofoodapi.domain.model.Usuario;
import br.com.alg.giraofoodapi.domain.repository.PedidoRepository;
import br.com.alg.giraofoodapi.domain.repository.filter.PedidoFilter;
import br.com.alg.giraofoodapi.domain.service.EmissaoPedidoService;
import br.com.alg.giraofoodapi.infrastructure.repository.spec.PedidoSpec;
import br.com.alg.giraofoodapi.openapi.controller.PedidoControllerOpenApi;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController implements PedidoControllerOpenApi {

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

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Page<PedidoResumoDTO> pesquisar(PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable) {

        pageable = traduzirPageable(pageable);

        Page<Pedido> pedidoPage = repository.findAll(PedidoSpec.usandoFiltro(filtro), pageable);
        List<PedidoResumoDTO> pedidosDto = assembler.toCollection(pedidoPage.getContent());
        Page<PedidoResumoDTO> page = new PageImpl(pedidosDto, pageable, pedidoPage.getTotalElements());
        return page;
    }

//    @GetMapping
//    public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
//        List<PedidoResumoDTO> pedidos = assembler.toCollection(repository.findAll());
//        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(pedidos);
//
//        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//
//        if(!StringUtils.isBlank(campos)) {
//            filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//        }
//
//        mappingJacksonValue.setFilters(filterProvider);
//        return mappingJacksonValue;
//    }

    @GetMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public PedidoDTO buscar(@PathVariable String id) {
        return pedidoModelAssembler.toDTO(pedidoService.buscar(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
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

    private Pageable traduzirPageable(Pageable pageable) {
        var mapeamento = ImmutableMap.of(
                "codigo", "codigo",
                "restaurante.nome", "restaurante.nome",
                "nomeCliente", "cliente.nome",
                "valorTotal", "valorTotal"
        );

        return PageableTranslator.tranlate(pageable, mapeamento);
    }

}
