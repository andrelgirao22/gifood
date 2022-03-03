package br.com.alg.giraofoodapi.api.controller;

import br.com.alg.giraofoodapi.api.assembler.FormaPagamentoModelAssembler;
import br.com.alg.giraofoodapi.api.assembler.RestauranteInputDisassembler;
import br.com.alg.giraofoodapi.api.model.dto.FormaPagamentoModel;
import br.com.alg.giraofoodapi.domain.model.Restaurante;
import br.com.alg.giraofoodapi.domain.service.CadastrosRestauranteService;
import br.com.alg.giraofoodapi.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{id}/formas-pagamento")
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

    @Autowired
    private CadastrosRestauranteService restauranteService;

    @Autowired
    private FormaPagamentoModelAssembler assembler;

    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;

    @GetMapping
    public CollectionModel<FormaPagamentoModel> listar(@PathVariable Long id) {
        Restaurante restaurante = restauranteService.buscar(id);
        return assembler.toCollectionModel(restaurante.getFormasPagamento());
    }

    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long id, @PathVariable Long formaPagamentoId) {
        restauranteService.associarFormaPagamento(id, formaPagamentoId);
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long id, @PathVariable Long formaPagamentoId) {
        restauranteService.desassociarFormaPagamento(id, formaPagamentoId);
    }


}

