package br.com.alg.giraofoodapi.api.v1.controller;

import br.com.alg.giraofoodapi.api.v1.GiLinksV1;
import br.com.alg.giraofoodapi.api.v1.assembler.FormaPagamentoModelAssembler;
import br.com.alg.giraofoodapi.api.v1.assembler.RestauranteInputDisassembler;
import br.com.alg.giraofoodapi.api.v1.model.dto.FormaPagamentoModel;
import br.com.alg.giraofoodapi.core.security.CheckSecurity;
import br.com.alg.giraofoodapi.domain.model.Restaurante;
import br.com.alg.giraofoodapi.domain.service.CadastrosRestauranteService;
import br.com.alg.giraofoodapi.api.v1.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurantes/{id}/formas-pagamento")
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

    @Autowired
    private CadastrosRestauranteService restauranteService;

    @Autowired
    private FormaPagamentoModelAssembler assembler;

    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;

    @Autowired
    private GiLinksV1 giLinks;

    @CheckSecurity.Restaurante.PodeConsultar
    @GetMapping
    public CollectionModel<FormaPagamentoModel> listar(@PathVariable Long id) {
        Restaurante restaurante = restauranteService.buscar(id);
        CollectionModel<FormaPagamentoModel> formasPagamentoModel =
                assembler.toCollectionModel(restaurante.getFormasPagamento())
                .removeLinks().add(giLinks.linkToRestaurantesFormasPagamento(id))
                .add(giLinks.linkToRestauranteFormaPagamentoAssociacao(id, "associacao"));;

        formasPagamentoModel.getContent().forEach(formaPagamentoModel -> {
            formaPagamentoModel.add(giLinks
                    .linkToRestauranteFormaPagamentoDesassociacao(id, formaPagamentoModel.getId(), "desassociar"));
        });

        return formasPagamentoModel;
    }

    @CheckSecurity.Restaurante.PodeGerenciarCadastro
    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long id, @PathVariable Long formaPagamentoId) {
        restauranteService.associarFormaPagamento(id, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurante.PodeGerenciarCadastro
    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long id, @PathVariable Long formaPagamentoId) {
        restauranteService.desassociarFormaPagamento(id, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }
}