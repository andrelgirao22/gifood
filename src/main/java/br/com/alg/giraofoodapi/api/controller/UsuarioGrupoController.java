package br.com.alg.giraofoodapi.api.controller;

import br.com.alg.giraofoodapi.api.GiLinks;
import br.com.alg.giraofoodapi.api.assembler.GrupoModelAssembler;
import br.com.alg.giraofoodapi.api.model.dto.GrupoModel;
import br.com.alg.giraofoodapi.domain.model.Usuario;
import br.com.alg.giraofoodapi.domain.service.CadastroUsuarioService;
import br.com.alg.giraofoodapi.openapi.controller.UsuarioGrupoControllerOpenApi;
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
    private GiLinks giLinks;

    @GetMapping
    public CollectionModel<GrupoModel> listar(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscar(id);

        CollectionModel<GrupoModel> gruposModel = assembler.toCollectionModel(usuario.getGrupos())
                .removeLinks()
                .add(giLinks.linkToUsuarioGrupoAssociacao(id, "associar"));

        gruposModel.getContent().forEach(grupoModel -> {
            grupoModel.add(giLinks.linkToUsuarioGrupoDesassociacao(
                    id, grupoModel.getId(), "desassociar"));
        });

        return gruposModel;
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long id, @PathVariable Long grupoId) {
        usuarioService.desassociarGrupo(id, grupoId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long id, @PathVariable Long grupoId) {
        usuarioService.associarGrupo(id, grupoId);
        return ResponseEntity.noContent().build();
    }

}
