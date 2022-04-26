package br.com.alg.giraofoodapi.api.v1.controller;

import br.com.alg.giraofoodapi.api.v1.GiLinksV1;
import br.com.alg.giraofoodapi.api.v1.assembler.UsuarioModelAssembler;
import br.com.alg.giraofoodapi.api.v1.model.dto.UsuarioModel;
import br.com.alg.giraofoodapi.core.security.CheckSecurity;
import br.com.alg.giraofoodapi.core.security.GiSecurity;
import br.com.alg.giraofoodapi.domain.model.Restaurante;
import br.com.alg.giraofoodapi.domain.service.CadastroUsuarioService;
import br.com.alg.giraofoodapi.domain.service.CadastrosRestauranteService;
import br.com.alg.giraofoodapi.api.v1.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurante/{id}/responsaveis")
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

    @Autowired
    private UsuarioModelAssembler assembler;

    @Autowired
    private CadastrosRestauranteService restauranteService;

    @Autowired
    private CadastroUsuarioService usuarioService;

    @Autowired
    private GiLinksV1 giLinks;

    @Autowired
    private GiSecurity giSecurity;

    @CheckSecurity.Restaurante.PodeConsultar
    @GetMapping
    public CollectionModel<UsuarioModel> listar(@PathVariable Long id) {
        Restaurante restaurante = restauranteService.buscar(id);

        CollectionModel<UsuarioModel> usuarioModels =
                assembler.toCollectionModel(restaurante.getUsuarios())
                .removeLinks()
                .add(giLinks.linkToResponsaveisRestaurante(id));

        if(giSecurity.podeGerenciarCadastroRestaurantes()) {
            usuarioModels.add(giLinks.linkToResponsaveisRestauranteAssociacao(id, "associar"));
        }

        if(giSecurity.podeGerenciarCadastroRestaurantes()) {
            usuarioModels.getContent().forEach(usuarioModel -> {
                usuarioModel.add(giLinks.linkToResponsaveisRestauranteDesassocicao(id, usuarioModel.getId(), "desassociar"));
            });
        }
        return usuarioModels;
    }

    @CheckSecurity.Restaurante.PodeGerenciarCadastro
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long id, @PathVariable Long usuarioId) {
        usuarioService.desassociarResponsavel(id, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurante.PodeGerenciarCadastro
    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long id, @PathVariable Long usuarioId) {
        usuarioService.associarResponsavel(id, usuarioId);
        return ResponseEntity.noContent().build();
    }

}
