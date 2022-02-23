package br.com.alg.giraofoodapi.openapi.controller;

import br.com.alg.giraofoodapi.api.exceptionhandler.Problem;
import br.com.alg.giraofoodapi.api.model.dto.PedidoDTO;
import br.com.alg.giraofoodapi.api.model.dto.PedidoResumoDTO;
import br.com.alg.giraofoodapi.api.model.input.PedidoInput;
import br.com.alg.giraofoodapi.domain.repository.filter.PedidoFilter;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

    @ApiOperation(value = "Lista de pedidos")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos", paramType = "query", type = "string")
    })
    public Page<PedidoResumoDTO> pesquisar(PedidoFilter filtro, Pageable pageable);

    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos", paramType = "query", type = "string")
    })
    @ApiOperation(value = "Busca um pedido pelo código")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    public PedidoDTO buscar(@ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55") String id);

    @ApiOperation(value = "Registra um pedido")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pedido registrado")
    })
    public PedidoDTO adicionar(@ApiParam(name = "corpo", value = "Representação de um novo pedido")
                                   PedidoInput pedidoInput);

}
