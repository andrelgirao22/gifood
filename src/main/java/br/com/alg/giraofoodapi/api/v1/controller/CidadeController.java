package br.com.alg.giraofoodapi.api.v1.controller;

import br.com.alg.giraofoodapi.api.ResourceUriHelper;
import br.com.alg.giraofoodapi.api.v1.assembler.CidadeInputDisassembler;
import br.com.alg.giraofoodapi.api.v1.assembler.CidadeModelAssembler;
import br.com.alg.giraofoodapi.api.v1.openapi.controller.CidadeControllerOpenApi;
import br.com.alg.giraofoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.alg.giraofoodapi.domain.exception.EstadoNaoEncontradoException;
import br.com.alg.giraofoodapi.domain.exception.NegocioException;
import br.com.alg.giraofoodapi.domain.model.Cidade;
import br.com.alg.giraofoodapi.api.v1.model.dto.CidadeModel;
import br.com.alg.giraofoodapi.api.v1.model.input.CidadeInput;
import br.com.alg.giraofoodapi.domain.repository.CidadeRepository;
import br.com.alg.giraofoodapi.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired
    private CidadeRepository repository;

    @Autowired
    private CadastroCidadeService service;

    @Autowired
    private CidadeModelAssembler assembler;

    @Autowired
    private CidadeInputDisassembler disassembler;

    @Deprecated
    @GetMapping
    public CollectionModel<CidadeModel> listar() {
        return assembler.toCollectionModel(this.repository.findAll());
    }

    @Deprecated
    @GetMapping(path = "/{cidadeId}")
    public CidadeModel buscarPorId(@PathVariable Long cidadeId) {
        Cidade cidade = service.buscar(cidadeId);
        CidadeModel cidadeDTO = assembler.toModel(cidade);
        return cidadeDTO;

    }

    @Deprecated
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel salvar(@RequestBody @Valid CidadeInput cidadeInput) {
        try{
            Cidade cidade = disassembler.toDomainObject(cidadeInput);
            cidade = service.salvar(cidade);

            CidadeModel cidadeDTO = assembler.toModel(cidade);
            ResourceUriHelper.addUriInResponseHeader(cidadeDTO.getId());
            return cidadeDTO;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @Deprecated
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