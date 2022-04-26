package br.com.alg.giraofoodapi.api.v1.controller;

import br.com.alg.giraofoodapi.api.v1.assembler.EstadoInputDisassembler;
import br.com.alg.giraofoodapi.api.v1.assembler.EstadoModelAssembler;
import br.com.alg.giraofoodapi.core.security.CheckSecurity;
import br.com.alg.giraofoodapi.domain.model.Estado;
import br.com.alg.giraofoodapi.api.v1.model.dto.EstadoModel;
import br.com.alg.giraofoodapi.api.v1.model.input.EstadoInput;
import br.com.alg.giraofoodapi.domain.repository.EstadoRepository;
import br.com.alg.giraofoodapi.domain.service.CadastroEstadoService;
import br.com.alg.giraofoodapi.api.v1.openapi.controller.EstadoControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/estados")
public class EstadoController implements EstadoControllerOpenApi {

    @Autowired
    private CadastroEstadoService service;

    @Autowired
    private EstadoRepository repository;

    @Autowired
    private EstadoModelAssembler assembler;

    @Autowired
    private EstadoInputDisassembler disassembler;

    @CheckSecurity.Estados.PodeConsultar
    @GetMapping
    public CollectionModel<EstadoModel> listar() {
        return assembler.toCollectionModel(repository.findAll());
    }

    @CheckSecurity.Estados.PodeConsultar
    @GetMapping("/{id}")
    public EstadoModel buscar(@PathVariable Long id) {
        Estado estado = this.service.buscar(id);
        if(estado == null) ResponseEntity.notFound().build();
        return assembler.toModel(estado);
    }

    @CheckSecurity.Estados.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoModel adicionar(@RequestBody @Valid EstadoInput estado) {
        return assembler.toModel(this.service.salvar(disassembler.toDomainObject(estado)));
    }

    @CheckSecurity.Estados.PodeEditar
    @PutMapping("/{id}")
    public EstadoModel atualizar(@PathVariable Long id, @RequestBody @Valid EstadoInput estado) {
        return assembler.toModel(this.service.alterar(id, disassembler.toDomainObject(estado)));
    }

    @CheckSecurity.Estados.PodeEditar
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        this.service.excluir(id);
    }
}
