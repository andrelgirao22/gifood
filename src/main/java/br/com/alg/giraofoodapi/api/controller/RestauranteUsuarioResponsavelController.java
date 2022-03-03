package br.com.alg.giraofoodapi.api.controller;

import br.com.alg.giraofoodapi.api.assembler.UsuarioModelAssembler;
import br.com.alg.giraofoodapi.api.model.dto.UsuarioModel;
import br.com.alg.giraofoodapi.domain.model.Restaurante;
import br.com.alg.giraofoodapi.domain.service.CadastroUsuarioService;
import br.com.alg.giraofoodapi.domain.service.CadastrosRestauranteService;
import br.com.alg.giraofoodapi.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurante/{id}/responsaveis")
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

    @Autowired
    private UsuarioModelAssembler assembler;

    @Autowired
    private CadastrosRestauranteService restauranteService;

    @Autowired
    private CadastroUsuarioService usuarioService;

    @GetMapping
    public CollectionModel<UsuarioModel> listar(@PathVariable Long id) {
        Restaurante restaurante = restauranteService.buscar(id);
        return assembler.toCollectionModel(restaurante.getUsuarios())
                .removeLinks()
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                        .methodOn(RestauranteUsuarioResponsavelController.class)
                        .listar(id)).withSelfRel());
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long id, @PathVariable Long usuarioId) {
        usuarioService.desassociarResponsavel(id, usuarioId);
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long id, @PathVariable Long usuarioId) {
        usuarioService.associarResponsavel(id, usuarioId);
    }

}
