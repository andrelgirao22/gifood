package br.com.alg.giraofoodapi.openapi.controller;

import br.com.alg.giraofoodapi.api.exceptionhandler.Problem;
import br.com.alg.giraofoodapi.api.model.dto.RestauranteDTO;
import br.com.alg.giraofoodapi.api.model.input.RestauranteInput;
import br.com.alg.giraofoodapi.openapi.model.RestauranteBasicoModelOpenApi;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

    @ApiOperation(value = "Lista restaurantes", response = RestauranteBasicoModelOpenApi.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "apenas-nome",
                    name = "projecao", paramType = "query", type = "string")
    })
    public List<RestauranteDTO> listarResumo();


    @ApiOperation(value = "Lista restaurantes", hidden = true)
    public List<RestauranteDTO> listarApenasNome();

    @ApiOperation(value = "Busca um Restaurante pelo ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Id do restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    public RestauranteDTO buscar(@ApiParam(value = "ID do restaurante", required = true) Long id);

    @ApiOperation(value = "Cadastra um restaurante")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Restaurante criado")
    })
    public RestauranteDTO adicionar(RestauranteInput restaurante);

    @ApiOperation(value = "Atualiza um restaurante pelo ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Restaurante atualizado"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado")
    })
    public RestauranteDTO atualizar(@ApiParam(example = "1", required = true) @PathVariable Long id,
                                    @ApiParam(name = "corpo", value = "Representação de um restaurante com novos dados", required = true)
                                    RestauranteInput restauranteInput);

    @ApiOperation("Ativa um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante ativado com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    public void ativar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
                    Long restauranteId);

    @ApiOperation("Inativa um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante inativado com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    public void inativar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
                    Long restauranteId);

    @ApiOperation("Ativa múltiplos restaurantes")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
    })
    public void ativarMultiplos(
            @ApiParam(name = "corpo", value = "IDs de restaurantes", required = true)
                    List<Long> restauranteIds);

    @ApiOperation("Inativa múltiplos restaurantes")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
    })
    public void inativarMultiplos(
            @ApiParam(name = "corpo", value = "IDs de restaurantes", required = true)
                    List<Long> restauranteIds);

    @ApiOperation("Abre um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante aberto com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    public void abrir(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
                    Long restauranteId);

    @ApiOperation("Fecha um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante fechado com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    public void fechar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
                    Long restauranteId);
}