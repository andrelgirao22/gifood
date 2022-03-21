package br.com.alg.giraofoodapi.api.v1.openapi.controller;

import br.com.alg.giraofoodapi.api.exceptionhandler.Problem;
import br.com.alg.giraofoodapi.api.v1.model.dto.PedidoModel;
import br.com.alg.giraofoodapi.api.v1.model.dto.PedidoResumoModel;
import br.com.alg.giraofoodapi.api.v1.model.input.PedidoInput;
import br.com.alg.giraofoodapi.domain.repository.filter.PedidoFilter;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

    @ApiOperation(value = "Lista de pedidos")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos", paramType = "query", type = "string")
    })
    public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable);

    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos", paramType = "query", type = "string")
    })
    @ApiOperation(value = "Busca um pedido pelo código")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    public PedidoModel buscar(@ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55") String id);

    @ApiOperation(value = "Registra um pedido")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pedido registrado")
    })
    public PedidoModel adicionar(@ApiParam(name = "corpo", value = "Representação de um novo pedido")
                                   PedidoInput pedidoInput);

}
