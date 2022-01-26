package br.com.alg.giraofoodapi.api.controller;

import br.com.alg.giraofoodapi.api.assembler.EstadoInputDisassembler;
import br.com.alg.giraofoodapi.api.assembler.EstadoModelAssembler;
import br.com.alg.giraofoodapi.domain.model.Estado;
import br.com.alg.giraofoodapi.api.model.dto.EstadoDTO;
import br.com.alg.giraofoodapi.api.model.input.EstadoInput;
import br.com.alg.giraofoodapi.domain.repository.EstadoRepository;
import br.com.alg.giraofoodapi.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private CadastroEstadoService service;

    @Autowired
    private EstadoRepository repository;

    @Autowired
    private EstadoModelAssembler assembler;

    @Autowired
    private EstadoInputDisassembler disassembler;

    @GetMapping
    public List<EstadoDTO> listar() {
        return assembler.toCollectionDTO(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoDTO> buscar(@PathVariable Long id) {
        Estado estado = this.service.buscar(id);
        if(estado == null) ResponseEntity.notFound().build();
        return ResponseEntity.ok(assembler.toDTO(estado));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado salvar(@RequestBody @Valid EstadoInput estado) {
        return this.service.salvar(disassembler.toDomainObject(estado));
    }

    @PutMapping("/{id}")
    public EstadoDTO alterar(@PathVariable Long id, @RequestBody @Valid EstadoInput estado) {
        return assembler.toDTO(this.service.alterar(id, disassembler.toDomainObject(estado)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.service.excluir(id);
    }
}
