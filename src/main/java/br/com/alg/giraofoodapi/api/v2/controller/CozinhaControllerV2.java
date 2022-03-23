package br.com.alg.giraofoodapi.api.v2.controller;

import br.com.alg.giraofoodapi.api.v2.assembler.CozinhaInputDisassemblerV2;
import br.com.alg.giraofoodapi.api.v2.assembler.CozinhaModelAssemblerV2;
import br.com.alg.giraofoodapi.api.v2.model.CozinhaModelV2;
import br.com.alg.giraofoodapi.api.v2.model.input.CozinhaInputV2;
import br.com.alg.giraofoodapi.domain.model.Cozinha;
import br.com.alg.giraofoodapi.domain.repository.CozinhaRepository;
import br.com.alg.giraofoodapi.domain.service.CadastroCozinhaService;
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
@RequestMapping(path = "/v2/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaControllerV2 {

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CozinhaRepository repository;

    @Autowired
    private CozinhaModelAssemblerV2 assembler;

    @Autowired
    private CozinhaInputDisassemblerV2 disassembler;

    @Autowired
    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @GetMapping
    public PagedModel<CozinhaModelV2> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<Cozinha> cozinhasPage = repository.findAll(pageable);

        PagedModel<CozinhaModelV2> cozinhaPagedModel = pagedResourcesAssembler
                .toModel(cozinhasPage, assembler);

        return cozinhaPagedModel;
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CozinhaModelV2 buscar(@PathVariable Long id){
        return assembler.toModel(this.cadastroCozinhaService.buscar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModelV2 salvar(@RequestBody @Valid CozinhaInputV2 cozinha) {
        return assembler.toModel(this.cadastroCozinhaService.salvar(disassembler.toDomainObject(cozinha)));
    }

    @PutMapping(path = "/{id}")
    public Cozinha atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInputV2 cozinhaInput) {
        Cozinha cozinhaExistente = this.cadastroCozinhaService.buscar(id);
        //BeanUtils.copyProperties(cozinhaInput, cozinhaExistente, "id");
        disassembler.copyToDomainInObject(cozinhaInput, cozinhaExistente);
        return this.cadastroCozinhaService.salvar(cozinhaExistente);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        this.cadastroCozinhaService.excluir(id);
    }
}
