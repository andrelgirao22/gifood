package br.com.alg.giraofoodapi.api.controller;

import br.com.alg.giraofoodapi.api.assembler.GrupoInputDisassembler;
import br.com.alg.giraofoodapi.api.assembler.GrupoModelAssembler;
import br.com.alg.giraofoodapi.api.model.dto.GrupoDTO;
import br.com.alg.giraofoodapi.api.model.input.GrupoInput;
import br.com.alg.giraofoodapi.domain.model.Grupo;
import br.com.alg.giraofoodapi.domain.repository.GrupoRepository;
import br.com.alg.giraofoodapi.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoRepository repository;

    @Autowired
    private CadastroGrupoService grupoService;

    @Autowired
    private GrupoModelAssembler assembler;

    @Autowired
    private GrupoInputDisassembler disassembler;

    @GetMapping
    public List<GrupoDTO> listar() {
        return assembler.toCollectionDTO(repository.findAll());
    }

    @GetMapping("/{id}")
    public GrupoDTO buscar(@PathVariable Long id) {
        return  assembler.toDTO(grupoService.buscar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO salvar(@RequestBody @Valid GrupoInput grupo) {
        return assembler.toDTO(grupoService.salvar(disassembler.toDomainObject(grupo)));
    }

    @PutMapping("/{id}")
    public GrupoDTO alterar(@RequestBody @Valid GrupoInput grupo, @PathVariable Long id) {
        Grupo grupoExistente = grupoService.buscar(id);
        disassembler.copyToDomainInObject(grupo, grupoExistente);
        return assembler.toDTO(grupoService.salvar(grupoExistente));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        grupoService.remove(id);
    }

}
