package br.com.alg.giraofoodapi.api.controller;

import br.com.alg.giraofoodapi.api.assembler.CidadeInputDisassembler;
import br.com.alg.giraofoodapi.api.assembler.CidadeModelAssembler;
import br.com.alg.giraofoodapi.api.controller.openapi.CidadeControllerOpenApi;
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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/cidades")
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired
    private CidadeRepository repository;

    @Autowired
    private CadastroCidadeService service;

    @Autowired
    private CidadeModelAssembler assembler;

    @Autowired
    private CidadeInputDisassembler disassembler;

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<CidadeDTO> listar() {
        return assembler.toCollectionDTO(this.repository.findAll());
    }

    @GetMapping(path = "/{cidadeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CidadeDTO buscarPorId(@PathVariable Long cidadeId) {
        return assembler.toDTO(service.buscar(cidadeId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade salvar(@RequestBody @Valid CidadeInput cidade) {
        try{
            return service.salvar(disassembler.toDomainObject(cidade));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping(path = "/{cidadeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Cidade atualizar(@PathVariable Long cidadeId,  @RequestBody @Valid CidadeInput cidadeInput) {
        Cidade cidadeAtual = this.service.buscar(cidadeId);
        //BeanUtils.copyProperties(cidade, cidadeAtual, "id");
        disassembler.copyToDomainInObject(cidadeInput, cidadeAtual);
        try {
            return this.service.salvar(cidadeAtual);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(),e);
        }
    }

    @DeleteMapping(path = "{cidadeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long cidadeId) {
        this.service.remover(cidadeId);
    }
}