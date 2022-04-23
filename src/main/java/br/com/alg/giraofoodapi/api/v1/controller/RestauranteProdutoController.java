package br.com.alg.giraofoodapi.api.v1.controller;

import br.com.alg.giraofoodapi.api.v1.assembler.ProdutoInputDisassembler;
import br.com.alg.giraofoodapi.api.v1.assembler.ProdutoModelAssembler;
import br.com.alg.giraofoodapi.api.v1.assembler.RestauranteInputDisassembler;
import br.com.alg.giraofoodapi.api.v1.model.dto.ProdutoModel;
import br.com.alg.giraofoodapi.api.v1.model.input.ProdutoInput;
import br.com.alg.giraofoodapi.core.security.CheckSecurity;
import br.com.alg.giraofoodapi.domain.model.Produto;
import br.com.alg.giraofoodapi.domain.model.Restaurante;
import br.com.alg.giraofoodapi.domain.repository.ProdutoRepository;
import br.com.alg.giraofoodapi.domain.service.CadastroProdutoService;
import br.com.alg.giraofoodapi.domain.service.CadastrosRestauranteService;
import br.com.alg.giraofoodapi.api.v1.openapi.controller.RestauranteProdutoControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

    @Autowired
    private CadastrosRestauranteService restauranteService;

    @Autowired
    private ProdutoModelAssembler produtoModelAssembler;

    @Autowired
    private ProdutoInputDisassembler produtoInputDisassembler;

    @Autowired
    private CadastroProdutoService produtoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;

    @CheckSecurity.Restaurante.PodeConsultar
    @GetMapping
    public CollectionModel<ProdutoModel> listar(@PathVariable Long restauranteId, @RequestParam(required = false) Boolean incluirInativos) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);
        List<Produto> todosProdutos = null;
        if(incluirInativos) {
            todosProdutos = restaurante.getProdutos();
        } else {
            todosProdutos = produtoRepository.findAtivosByRestaurantes(restaurante);
        }
        return produtoModelAssembler.toCollectionModel(todosProdutos);
    }

    @Override
    @CheckSecurity.Restaurante.PodeConsultar
    @GetMapping("/{produtoId}")
    public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = produtoService.buscarPeloRestaurante(produtoId, restauranteId);
        return produtoModelAssembler.toModel(produto);
    }

    @CheckSecurity.Restaurante.PodeGerenciarFuncionamento
    @PostMapping
    public ProdutoModel adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);
        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);
        return produtoModelAssembler.toModel(produtoService.salvar(produto));
    }

    @CheckSecurity.Restaurante.PodeGerenciarFuncionamento
    @PutMapping(value = "/{produtoId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ProdutoModel atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoAtual = produtoService.buscar(produtoId);
        produtoInputDisassembler.copyToDomainInObject(produtoInput, produtoAtual);
        produtoAtual = produtoService.salvar(produtoAtual);
        return produtoModelAssembler.toModel(produtoAtual);
    }

}

