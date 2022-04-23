package br.com.alg.giraofoodapi.api.v1.controller;

import br.com.alg.giraofoodapi.api.v1.assembler.PedidoInputDisassembler;
import br.com.alg.giraofoodapi.api.v1.assembler.PedidoModelAssembler;
import br.com.alg.giraofoodapi.api.v1.assembler.PedidoResumoModelAssembler;
import br.com.alg.giraofoodapi.api.v1.model.dto.PedidoModel;
import br.com.alg.giraofoodapi.api.v1.model.dto.PedidoResumoModel;
import br.com.alg.giraofoodapi.api.v1.model.input.PedidoInput;
import br.com.alg.giraofoodapi.core.data.PageWrapper;
import br.com.alg.giraofoodapi.core.data.PageableTranslator;
import br.com.alg.giraofoodapi.core.security.CheckSecurity;
import br.com.alg.giraofoodapi.core.security.GiSecurity;
import br.com.alg.giraofoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.alg.giraofoodapi.domain.exception.NegocioException;
import br.com.alg.giraofoodapi.domain.model.Pedido;
import br.com.alg.giraofoodapi.domain.model.Usuario;
import br.com.alg.giraofoodapi.domain.repository.PedidoRepository;
import br.com.alg.giraofoodapi.domain.repository.filter.PedidoFilter;
import br.com.alg.giraofoodapi.domain.service.EmissaoPedidoService;
import br.com.alg.giraofoodapi.infrastructure.repository.spec.PedidoSpec;
import br.com.alg.giraofoodapi.api.v1.openapi.controller.PedidoControllerOpenApi;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/pedidos")
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

    @Autowired
    private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

    @Autowired
    private GiSecurity giSecurity;

    @CheckSecurity.Pedido.PodePesquisar
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable) {

        Pageable pageableTraduzido = traduzirPageable(pageable);

        Page<Pedido> pedidosPage = repository.findAll(PedidoSpec.usandoFiltro(filtro), pageableTraduzido);

        pedidosPage = new PageWrapper<>(pedidosPage, pageable);

        CollectionModel<PedidoResumoModel> pedidosDto = assembler.toCollectionModel(pedidosPage.getContent());

        PagedModel<PedidoResumoModel> page = pagedResourcesAssembler.toModel(pedidosPage, assembler);
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

    @CheckSecurity.Pedido.PodeBuscar
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PedidoModel buscar(@PathVariable String id) {
        return pedidoModelAssembler.toModel(pedidoService.buscar(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = disassembler.toDomainObject(pedidoInput);

            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(giSecurity.getUsuarioId());

            novoPedido = pedidoService.emitir(novoPedido);

            return pedidoModelAssembler.toModel(novoPedido);
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
