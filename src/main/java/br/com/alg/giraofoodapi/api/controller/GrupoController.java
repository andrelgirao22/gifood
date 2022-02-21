package br.com.alg.giraofoodapi.api.controller;

import br.com.alg.giraofoodapi.api.assembler.GrupoInputDisassembler;
import br.com.alg.giraofoodapi.api.assembler.GrupoModelAssembler;
import br.com.alg.giraofoodapi.openapi.controller.GrupoControllerOpenApi;
import br.com.alg.giraofoodapi.api.model.dto.GrupoDTO;
import br.com.alg.giraofoodapi.api.model.input.GrupoInput;
import br.com.alg.giraofoodapi.domain.model.Grupo;
import br.com.alg.giraofoodapi.domain.repository.GrupoRepository;
import br.com.alg.giraofoodapi.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/grupos")
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
    public List<GrupoDTO> listar() {
        return assembler.toCollectionDTO(repository.findAll());
    }

    @GetMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public GrupoDTO buscar(@PathVariable Long id) {
        return  assembler.toDTO(grupoService.buscar(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO salvar(@RequestBody @Valid GrupoInput grupo) {
        return assembler.toDTO(grupoService.salvar(disassembler.toDomainObject(grupo)));
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public GrupoDTO alterar(@RequestBody @Valid GrupoInput grupo, @PathVariable Long id) {
        Grupo grupoExistente = grupoService.buscar(id);
        disassembler.copyToDomainInObject(grupo, grupoExistente);
        return assembler.toDTO(grupoService.salvar(grupoExistente));
    }

    @DeleteMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        grupoService.remove(id);
    }

}
