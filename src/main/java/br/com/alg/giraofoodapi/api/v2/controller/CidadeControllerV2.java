package br.com.alg.giraofoodapi.api.v2.controller;

import br.com.alg.giraofoodapi.api.ResourceUriHelper;
import br.com.alg.giraofoodapi.api.v2.assembler.CidadeInputDisassemblerV2;
import br.com.alg.giraofoodapi.api.v2.assembler.CidadeModelAssemblerV2;
import br.com.alg.giraofoodapi.api.v2.model.CidadeModelV2;
import br.com.alg.giraofoodapi.api.v2.model.input.CidadeInputV2;
import br.com.alg.giraofoodapi.api.v2.openapi.CidadeControllerV2OpenApi;
import br.com.alg.giraofoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.alg.giraofoodapi.domain.exception.EstadoNaoEncontradoException;
import br.com.alg.giraofoodapi.domain.exception.NegocioException;
import br.com.alg.giraofoodapi.domain.model.Cidade;
import br.com.alg.giraofoodapi.domain.repository.CidadeRepository;
import br.com.alg.giraofoodapi.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2 implements CidadeControllerV2OpenApi {

    @Autowired
    private CidadeRepository repository;

    @Autowired
    private CadastroCidadeService service;

    @Autowired
    private CidadeModelAssemblerV2 assembler;

    @Autowired
    private CidadeInputDisassemblerV2 disassembler;

    @GetMapping
    public CollectionModel<CidadeModelV2> listar() {
        return assembler.toCollectionModel(this.repository.findAll());
    }

    @GetMapping(path = "/{cidadeId}")
    public CidadeModelV2 buscar(@PathVariable Long cidadeId) {
        Cidade cidade = service.buscar(cidadeId);
        CidadeModelV2 cidadeDTO = assembler.toModel(cidade);
        return cidadeDTO;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModelV2 adicionar(@RequestBody @Valid CidadeInputV2 cidadeInput) {
        try{
            Cidade cidade = disassembler.toDomainObject(cidadeInput);
            cidade = service.salvar(cidade);

            CidadeModelV2 cidadeDTO = assembler.toModel(cidade);
            ResourceUriHelper.addUriInResponseHeader(cidadeDTO.getIdCidade());
            return cidadeDTO;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping(path = "/{cidadeId}")
    public CidadeModelV2 atualizar(@PathVariable Long cidadeId,  @RequestBody @Valid CidadeInputV2 cidadeInput) {
        Cidade cidadeAtual = this.service.buscar(cidadeId);
        //BeanUtils.copyProperties(cidade, cidadeAtual, "id");
        disassembler.copyToDomainInObject(cidadeInput, cidadeAtual);
        try {
            return assembler.toModel(this.service.salvar(cidadeAtual));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(),e);
        }
    }

    @DeleteMapping(path = "{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        this.service.remover(cidadeId);
    }
}
