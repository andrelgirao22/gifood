package br.com.alg.giraofoodapi.api.controller;

import br.com.alg.giraofoodapi.api.assembler.CidadeInputDisassembler;
import br.com.alg.giraofoodapi.api.assembler.CidadeModelAssembler;
import br.com.alg.giraofoodapi.api.exceptionhandler.Problem;
import br.com.alg.giraofoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.alg.giraofoodapi.domain.exception.EstadoNaoEncontradoException;
import br.com.alg.giraofoodapi.domain.exception.NegocioException;
import br.com.alg.giraofoodapi.domain.model.Cidade;
import br.com.alg.giraofoodapi.api.model.dto.CidadeDTO;
import br.com.alg.giraofoodapi.api.model.input.CidadeInput;
import br.com.alg.giraofoodapi.domain.repository.CidadeRepository;
import br.com.alg.giraofoodapi.domain.service.CadastroCidadeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository repository;

    @Autowired
    private CadastroCidadeService service;

    @Autowired
    private CidadeModelAssembler assembler;

    @Autowired
    private CidadeInputDisassembler disassembler;

    @ApiOperation(value = "Lista das cidades")
    @GetMapping
    public List<CidadeDTO> listar() {
        return assembler.toCollectionDTO(this.repository.findAll());
    }

    @ApiOperation(value = "Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Id da cidade inválida", response = Problem.class),
            @ApiResponse(code = 404, message = "cidade não encontrada", response = Problem.class)
    })
    @GetMapping("/{cidadeId}")
    public CidadeDTO buscarPorId(@ApiParam(value = "ID de uma cidade", example = "1")
                                     @PathVariable Long cidadeId) {
        return assembler.toDTO(service.buscar(cidadeId));
    }

    @ApiOperation(value = "Cadastra uma cidade")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cidade cadastrada")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade salvar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cidade")
            @RequestBody @Valid CidadeInput cidade) {
        try{
            return service.salvar(disassembler.toDomainObject(cidade));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @ApiOperation(value = "Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @PutMapping("/{cidadeId}")
    public Cidade atualizar(
            @ApiParam(name = "corpo", value = "Representação de uma cidade com novos dados")
            @PathVariable Long cidadeId,  @RequestBody @Valid CidadeInput cidadeInput) {
        Cidade cidadeAtual = this.service.buscar(cidadeId);
        //BeanUtils.copyProperties(cidade, cidadeAtual, "id");
        disassembler.copyToDomainInObject(cidadeInput, cidadeAtual);
        try {
            return this.service.salvar(cidadeAtual);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(),e);
        }
    }



    @ApiOperation(value = "Remove uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade excluida"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @DeleteMapping("{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@ApiParam(name = "ID de uma cidade", example = "1")
                           @PathVariable Long cidadeId) {
        this.service.remover(cidadeId);
    }
}