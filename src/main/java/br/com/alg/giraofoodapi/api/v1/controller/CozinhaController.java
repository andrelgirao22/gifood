package br.com.alg.giraofoodapi.api.v1.controller;

import br.com.alg.giraofoodapi.api.v1.assembler.CozinhaInputDisassembler;
import br.com.alg.giraofoodapi.api.v1.assembler.CozinhaModelAssembler;
import br.com.alg.giraofoodapi.domain.model.Cozinha;
import br.com.alg.giraofoodapi.api.v1.model.dto.CozinhaModel;
import br.com.alg.giraofoodapi.api.v1.model.input.CozinhaInput;
import br.com.alg.giraofoodapi.domain.repository.CozinhaRepository;
import br.com.alg.giraofoodapi.domain.service.CadastroCozinhaService;
import br.com.alg.giraofoodapi.api.v1.openapi.controller.CozinhaControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/cozinhas")
public class CozinhaController implements CozinhaControllerOpenApi {

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CozinhaRepository repository;

    @Autowired
    private CozinhaModelAssembler assembler;

    @Autowired
    private CozinhaInputDisassembler disassembler;

    @Autowired
    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public PagedModel<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<Cozinha> cozinhasPage = repository.findAll(pageable);

        PagedModel<CozinhaModel> cozinhaPagedModel = pagedResourcesAssembler
                .toModel(cozinhasPage, assembler);

        return cozinhaPagedModel;
    }

    @GetMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CozinhaModel buscar(@PathVariable Long id){
        return assembler.toModel(this.cadastroCozinhaService.buscar(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModel salvar(@RequestBody @Valid CozinhaInput cozinha) {
        return assembler.toModel(this.cadastroCozinhaService.salvar(disassembler.toDomainObject(cozinha)));
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Cozinha atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinhaExistente = this.cadastroCozinhaService.buscar(id);
        //BeanUtils.copyProperties(cozinhaInput, cozinhaExistente, "id");
        disassembler.copyToDomainInObject(cozinhaInput, cozinhaExistente);
        return this.cadastroCozinhaService.salvar(cozinhaExistente);
    }

    @DeleteMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        this.cadastroCozinhaService.excluir(id);
    }
}
