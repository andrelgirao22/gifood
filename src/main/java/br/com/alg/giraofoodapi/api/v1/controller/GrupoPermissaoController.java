package br.com.alg.giraofoodapi.api.v1.controller;

import br.com.alg.giraofoodapi.api.v1.GiLinksV1;
import br.com.alg.giraofoodapi.api.v1.assembler.PermissaoModelAssembler;
import br.com.alg.giraofoodapi.api.v1.model.dto.PermissaoModel;
import br.com.alg.giraofoodapi.domain.model.Grupo;
import br.com.alg.giraofoodapi.domain.model.Permissao;
import br.com.alg.giraofoodapi.domain.repository.GrupoRepository;
import br.com.alg.giraofoodapi.domain.service.CadastroGrupoService;
import br.com.alg.giraofoodapi.domain.service.CadastroPermissaoService;
import br.com.alg.giraofoodapi.api.v1.openapi.controller.GrupoPermissaoControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    @Autowired
    private GrupoRepository repository;

    @Autowired
    private CadastroGrupoService grupoService;

    @Autowired
    private CadastroPermissaoService permissaoService;

    @Autowired
    private PermissaoModelAssembler assembler;

    @Autowired
    private GiLinksV1 giLinks;

    @GetMapping
    public CollectionModel<PermissaoModel> listar(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.buscar(grupoId);

        CollectionModel<PermissaoModel> permissoesModel = assembler.toCollectionModel(grupo.getPermissoes())
                .removeLinks()
                .add(giLinks.linkToGrupoPermissoes(grupoId))
                .add(giLinks.linkToGrupoPermissaoAssociacao(grupoId, "associar"));

        permissoesModel.getContent().forEach(permissaoModel -> {
            permissaoModel.add(giLinks.linkToGrupoPermissaoDesassociacao(
                    grupoId, permissaoModel.getId(), "desassociar"));
        });

        return permissoesModel;
    }

    @GetMapping("/{permissaoId}")
    public PermissaoModel buscar(@PathVariable Long permissaoId) {
        return  assembler.toModel(permissaoService.buscar(permissaoId));
    }

    @PutMapping("/{permisaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permisaoId) {
        Grupo grupo = grupoService.buscar(grupoId);
        Permissao permissao = permissaoService.buscar(permisaoId);
        grupoService.associarPermissao(grupoId, permisaoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.desassociarPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }

}
