package br.com.alg.giraofoodapi.api.controller;

import br.com.alg.giraofoodapi.api.assembler.GrupoInputDisassembler;
import br.com.alg.giraofoodapi.api.assembler.GrupoModelAssembler;
import br.com.alg.giraofoodapi.api.assembler.PermissaoModelAssembler;
import br.com.alg.giraofoodapi.api.model.dto.GrupoDTO;
import br.com.alg.giraofoodapi.api.model.dto.PermissaoDTO;
import br.com.alg.giraofoodapi.api.model.input.GrupoInput;
import br.com.alg.giraofoodapi.domain.model.Grupo;
import br.com.alg.giraofoodapi.domain.model.Permissao;
import br.com.alg.giraofoodapi.domain.repository.GrupoRepository;
import br.com.alg.giraofoodapi.domain.service.CadastroGrupoService;
import br.com.alg.giraofoodapi.domain.service.CadastroPermissaoService;
import br.com.alg.giraofoodapi.openapi.controller.GrupoPermissaoControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grupos/{id}/permissoes")
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    @Autowired
    private GrupoRepository repository;

    @Autowired
    private CadastroGrupoService grupoService;

    @Autowired
    private CadastroPermissaoService permissaoService;

    @Autowired
    private PermissaoModelAssembler assembler;

    @GetMapping
    public List<PermissaoDTO> listar(@PathVariable Long id) {
        return  assembler.toCollectionDTO(grupoService.buscar(id).getPermissoes());
    }

    @GetMapping("/{permissaoId}")
    public PermissaoDTO buscar(@PathVariable Long permissaoId) {
        return  assembler.toDTO(permissaoService.buscar(permissaoId));
    }

    @PutMapping("/{permisaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long id,  @PathVariable Long permisaoId) {
        Grupo grupo = grupoService.buscar(id);
        Permissao permissao = permissaoService.buscar(permisaoId);
        grupoService.associarPermissao(id, permisaoId);
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long id, @PathVariable Long permissaoId) {
        grupoService.desassociarPermissao(id, permissaoId);
    }

}
