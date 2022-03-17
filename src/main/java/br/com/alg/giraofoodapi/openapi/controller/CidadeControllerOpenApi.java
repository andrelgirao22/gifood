package br.com.alg.giraofoodapi.openapi.controller;

import br.com.alg.giraofoodapi.api.exceptionhandler.Problem;
import br.com.alg.giraofoodapi.api.model.dto.CidadeModel;
import br.com.alg.giraofoodapi.api.model.input.CidadeInput;
import br.com.alg.giraofoodapi.domain.model.Cidade;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

    @ApiOperation(value = "Lista das cidades")
    public CollectionModel<CidadeModel> listar();

    @ApiOperation(value = "Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Id da cidade inválida", response = Problem.class),
            @ApiResponse(code = 404, message = "cidade não encontrada", response = Problem.class)
    })
    CidadeModel buscarPorId(@ApiParam(value = "ID de uma cidade", example = "1", required = true)
                                 @PathVariable Long cidadeId);

    @ApiOperation(value = "Cadastra uma cidade")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cidade cadastrada")
    })
    CidadeModel salvar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true)
            @RequestBody @Valid CidadeInput cidade);

    @ApiOperation(value = "Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    Cidade atualizar(
            @ApiParam(name = "corpo", value = "Representação de uma cidade com novos dados")
            @PathVariable Long cidadeId,  @RequestBody @Valid CidadeInput cidadeInput);

    @ApiOperation(value = "Remove uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade excluida"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    void delete(@ApiParam(name = "ID de uma cidade", example = "1", required = true)
                       @PathVariable Long cidadeId);
}
