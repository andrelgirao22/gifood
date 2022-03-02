package br.com.alg.giraofoodapi.api.controller;

import br.com.alg.giraofoodapi.api.ResourceUriHelper;
import br.com.alg.giraofoodapi.api.assembler.CidadeInputDisassembler;
import br.com.alg.giraofoodapi.api.assembler.CidadeModelAssembler;
import br.com.alg.giraofoodapi.openapi.controller.CidadeControllerOpenApi;
import br.com.alg.giraofoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.alg.giraofoodapi.domain.exception.EstadoNaoEncontradoException;
import br.com.alg.giraofoodapi.domain.exception.NegocioException;
import br.com.alg.giraofoodapi.domain.model.Cidade;
import br.com.alg.giraofoodapi.api.model.dto.CidadeDTO;
import br.com.alg.giraofoodapi.api.model.input.CidadeInput;
import br.com.alg.giraofoodapi.domain.repository.CidadeRepository;
import br.com.alg.giraofoodapi.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
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

    @GetMapping
    public CollectionModel<CidadeDTO> listar() {
        return assembler.toCollectionModel(this.repository.findAll());
    }

    @GetMapping(path = "/{cidadeId}")
    public CidadeDTO buscarPorId(@PathVariable Long cidadeId) {
        Cidade cidade = service.buscar(cidadeId);
        CidadeDTO cidadeDTO = assembler.toModel(cidade);
        return cidadeDTO;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDTO salvar(@RequestBody @Valid CidadeInput cidadeInput) {
        try{
            Cidade cidade = disassembler.toDomainObject(cidadeInput);
            cidade = service.salvar(cidade);

            CidadeDTO cidadeDTO = assembler.toModel(cidade);
            ResourceUriHelper.addUriInResponseHeader(cidadeDTO.getId());
            return cidadeDTO;
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