package br.com.alg.giraofoodapi.api.v1.controller;

import br.com.alg.giraofoodapi.api.v1.assembler.GrupoInputDisassembler;
import br.com.alg.giraofoodapi.api.v1.assembler.GrupoModelAssembler;
import br.com.alg.giraofoodapi.api.v1.openapi.controller.GrupoControllerOpenApi;
import br.com.alg.giraofoodapi.api.v1.model.dto.GrupoModel;
import br.com.alg.giraofoodapi.api.v1.model.input.GrupoInput;
import br.com.alg.giraofoodapi.domain.model.Grupo;
import br.com.alg.giraofoodapi.domain.repository.GrupoRepository;
import br.com.alg.giraofoodapi.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/grupos")
public class GrupoController implements GrupoControllerOpenApi {

    @Autowired
    private GrupoRepository repository;

    @Autowired
    private CadastroGrupoService grupoService;

    @Autowired
    private GrupoModelAssembler assembler;

    @Autowired
    private GrupoInputDisassembler disassembler;

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<GrupoModel> listar() {
        return assembler.toCollectionModel(repository.findAll());
    }

    @GetMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public GrupoModel buscar(@PathVariable Long id) {
        return  assembler.toModel(grupoService.buscar(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModel salvar(@RequestBody @Valid GrupoInput grupo) {
        return assembler.toModel(grupoService.salvar(disassembler.toDomainObject(grupo)));
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public GrupoModel alterar(@RequestBody @Valid GrupoInput grupo, @PathVariable Long id) {
        Grupo grupoExistente = grupoService.buscar(id);
        disassembler.copyToDomainInObject(grupo, grupoExistente);
        return assembler.toModel(grupoService.salvar(grupoExistente));
    }

    @DeleteMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        grupoService.remove(id);
    }

}
