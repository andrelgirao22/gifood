package br.com.alg.giraofoodapi.api.controller;

import br.com.alg.giraofoodapi.api.GiLinks;
import br.com.alg.giraofoodapi.api.assembler.UsuarioModelAssembler;
import br.com.alg.giraofoodapi.api.model.dto.UsuarioModel;
import br.com.alg.giraofoodapi.domain.model.Restaurante;
import br.com.alg.giraofoodapi.domain.service.CadastroUsuarioService;
import br.com.alg.giraofoodapi.domain.service.CadastrosRestauranteService;
import br.com.alg.giraofoodapi.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
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
    private GiLinks giLinks;

    @GetMapping
    public CollectionModel<UsuarioModel> listar(@PathVariable Long id) {
        Restaurante restaurante = restauranteService.buscar(id);

        CollectionModel<UsuarioModel> usuarioModels =
                assembler.toCollectionModel(restaurante.getUsuarios())
                .removeLinks()
                .add(giLinks.linkToResponsaveisRestaurante(id));

        usuarioModels.add(giLinks.linkToResponsaveisRestauranteAssociacao(id, "associar"));

        usuarioModels.getContent().forEach(usuarioModel -> {
            usuarioModel.add(giLinks.linkToResponsaveisRestauranteDesassocicao(id, usuarioModel.getId(), "desassociar"));
        });

        return usuarioModels;
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long id, @PathVariable Long usuarioId) {
        usuarioService.desassociarResponsavel(id, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long id, @PathVariable Long usuarioId) {
        usuarioService.associarResponsavel(id, usuarioId);
        return ResponseEntity.noContent().build();
    }

}
