package br.com.alg.giraofoodapi.openapi.controller;

import br.com.alg.giraofoodapi.api.exceptionhandler.Problem;
import br.com.alg.giraofoodapi.api.model.dto.CozinhaDTO;
import br.com.alg.giraofoodapi.api.model.input.CozinhaInput;
import br.com.alg.giraofoodapi.domain.model.Cozinha;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {


    @ApiOperation(value = "Lista de cozinhas")
    public Page<CozinhaDTO> listar(@PageableDefault(size = 10) Pageable pageable);


    @ApiOperation(value = "Busca uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Id da cozinha inválida", response = Problem.class),
            @ApiResponse(code = 404, message = "cozinha não encontrada", response = Problem.class)
    })
    public CozinhaDTO buscar(@ApiParam(example = "1", value = "ID da cozinha") @PathVariable Long id);

    @ApiOperation(value = "Cria uma nova cozinha")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cozinha criada")
    })
    public CozinhaDTO salvar(@ApiParam(name = "Corpo", value = "Representação de uma nova cozinha")
                                 @RequestBody @Valid CozinhaInput cozinha);


    @ApiOperation(value = "Atualiza uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Id da cozinha inválida", response = Problem.class),
            @ApiResponse(code = 200, message = "Cozinha atualizada")
    })
    public Cozinha atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInput cozinhaInput);

    @ApiOperation(value = "Exclui uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cozinha excluida"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    public void remover(@PathVariable Long id);
}
