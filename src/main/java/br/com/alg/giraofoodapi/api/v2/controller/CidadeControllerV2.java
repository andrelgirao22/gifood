package br.com.alg.giraofoodapi.api.v2.controller;

import br.com.alg.giraofoodapi.api.ResourceUriHelper;
import br.com.alg.giraofoodapi.api.v1.assembler.CidadeInputDisassembler;
import br.com.alg.giraofoodapi.api.v1.model.input.CidadeInput;
import br.com.alg.giraofoodapi.api.v1.openapi.controller.CidadeControllerOpenApi;
import br.com.alg.giraofoodapi.api.v2.assembler.CidadeInputDisassemblerV2;
import br.com.alg.giraofoodapi.api.v2.assembler.CidadeModelAssemblerV2;
import br.com.alg.giraofoodapi.api.v2.model.CidadeModelV2;
import br.com.alg.giraofoodapi.api.v2.model.input.CidadeInputV2;
import br.com.alg.giraofoodapi.api.web.GiMediaTypes;
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
@RequestMapping(path = "/cidades", produces = GiMediaTypes.V2_APPLICATION_JSON_VALUE)
public class CidadeControllerV2 {

    @Autowired
    private CidadeRepository repository;

    @Autowired
    private CadastroCidadeService service;

    @Autowired
    private CidadeModelAssemblerV2 assembler;

    @Autowired
    private CidadeInputDisassemblerV2 disassembler;

    @GetMapping(produces = GiMediaTypes.V2_APPLICATION_JSON_VALUE)
    public CollectionModel<CidadeModelV2> listar() {
        return assembler.toCollectionModel(this.repository.findAll());
    }

    @GetMapping(path = "/{cidadeId}", produces = GiMediaTypes.V2_APPLICATION_JSON_VALUE)
    public CidadeModelV2 buscarPorId(@PathVariable Long cidadeId) {
        Cidade cidade = service.buscar(cidadeId);
        CidadeModelV2 cidadeDTO = assembler.toModel(cidade);
        return cidadeDTO;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModelV2 salvar(@RequestBody @Valid CidadeInputV2 cidadeInput) {
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

    @PutMapping(path = "/{cidadeId}", produces = GiMediaTypes.V2_APPLICATION_JSON_VALUE)
    public Cidade atualizar(@PathVariable Long cidadeId,  @RequestBody @Valid CidadeInputV2 cidadeInput) {
        Cidade cidadeAtual = this.service.buscar(cidadeId);
        //BeanUtils.copyProperties(cidade, cidadeAtual, "id");
        disassembler.copyToDomainInObject(cidadeInput, cidadeAtual);
        try {
            return this.service.salvar(cidadeAtual);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(),e);
        }
    }

    @DeleteMapping(path = "{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long cidadeId) {
        this.service.remover(cidadeId);
    }
}
