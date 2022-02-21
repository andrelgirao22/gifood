package br.com.alg.giraofoodapi.api.controller.openapi;

import br.com.alg.giraofoodapi.api.exceptionhandler.Problem;
import br.com.alg.giraofoodapi.api.model.dto.GrupoDTO;
import br.com.alg.giraofoodapi.api.model.input.GrupoInput;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

    @ApiOperation(value = "Lista de grupos")
    public List<GrupoDTO> listar();

    @ApiOperation(value = "Busca um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do grupo inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    public GrupoDTO buscar(@ApiParam(value = "ID do Grupo", example = "1") @PathVariable Long id);

    @ApiOperation(value = "Cria um novo grupo")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Grupo criado")
    })
    public GrupoDTO salvar(@ApiParam(name = "corpo",value = "Representação do grupo")
                               @RequestBody @Valid GrupoInput grupo);

    @ApiOperation(value = "Atualiza um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Grupo atualizado"),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    public GrupoDTO alterar(@ApiParam(name = "corpo",value = "Representação do grupo")
            @RequestBody @Valid GrupoInput grupo, @PathVariable Long id);

    @ApiOperation(value = "Exclui um grupo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Grupo excluido")
    })
    public void remover(@PathVariable Long id);
}
