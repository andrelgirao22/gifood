package br.com.alg.giraofoodapi.openapi.controller;

import br.com.alg.giraofoodapi.api.exceptionhandler.Problem;
import br.com.alg.giraofoodapi.api.model.dto.CidadeDTO;
import br.com.alg.giraofoodapi.api.model.dto.FormaPagamentoDTO;
import br.com.alg.giraofoodapi.api.model.input.CidadeInput;
import br.com.alg.giraofoodapi.api.model.input.FormaPagamentoInput;
import br.com.alg.giraofoodapi.domain.model.Cidade;
import br.com.alg.giraofoodapi.domain.model.FormaPagamento;
import io.swagger.annotations.*;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Api(tags = "Formas Pagamento")
public interface FormasPagamentoControllerOpenApi {

    @ApiOperation(value = "Lista de formas de pagamento")
    public ResponseEntity<List<FormaPagamentoDTO>> listar(ServletWebRequest request);

    @ApiOperation(value = "Busca uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Id da forma de pagamento inválida", response = Problem.class),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    public ResponseEntity<FormaPagamentoDTO> buscar(ServletWebRequest request, @ApiParam(example = "1") @PathVariable Long id);

    @ApiOperation(value = "Cria uma nova forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Forma de pagamento criada")
    })
    public FormaPagamentoDTO salvar(@ApiParam(name = "Corpo", value = "Representação de uma nova forma de pagamento")
                                        @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) ;

    @ApiOperation(value = "Atualiza uma forma de pagamento pelo ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID de uma forma de pagamento inválida"),
            @ApiResponse(code = 200, message = "Cidade atualizada")
    })
    public FormaPagamentoDTO alterar(@RequestBody FormaPagamentoInput formaPagamentoInput, @PathVariable Long id);

    @ApiOperation(value = "Remove uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Forma de pagamento excluida"),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    public void remover(@ApiParam(example = "id") @PathVariable Long id);
}
