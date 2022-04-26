package br.com.alg.giraofoodapi.api.v1.controller;

import br.com.alg.giraofoodapi.api.v1.GiLinksV1;
import br.com.alg.giraofoodapi.api.v1.assembler.GrupoModelAssembler;
import br.com.alg.giraofoodapi.api.v1.model.dto.GrupoModel;
import br.com.alg.giraofoodapi.core.security.CheckSecurity;
import br.com.alg.giraofoodapi.core.security.GiSecurity;
import br.com.alg.giraofoodapi.domain.model.Usuario;
import br.com.alg.giraofoodapi.domain.service.CadastroUsuarioService;
import br.com.alg.giraofoodapi.api.v1.openapi.controller.UsuarioGrupoControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios/{id}/grupos")
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {


    @Autowired
    private CadastroUsuarioService usuarioService;

    @Autowired
    private GrupoModelAssembler assembler;

    @Autowired
    private GiLinksV1 giLinks;

    @Autowired
    private GiSecurity giSecurity;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping
    public CollectionModel<GrupoModel> listar(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscar(id);

        CollectionModel<GrupoModel> gruposModel = assembler.toCollectionModel(usuario.getGrupos())
                .removeLinks();


        if(giSecurity.podeEditarUsuariosGruposPermissoes()) {
            gruposModel.add(giLinks.linkToUsuarioGrupoAssociacao(id, "associar"));
            gruposModel.getContent().forEach(grupoModel -> {
                grupoModel.add(giLinks.linkToUsuarioGrupoDesassociacao(
                        id, grupoModel.getId(), "desassociar"));
            });
        }


        return gruposModel;
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long id, @PathVariable Long grupoId) {
        usuarioService.desassociarGrupo(id, grupoId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long id, @PathVariable Long grupoId) {
        usuarioService.associarGrupo(id, grupoId);
        return ResponseEntity.noContent().build();
    }

}
