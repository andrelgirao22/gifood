package br.com.alg.giraofoodapi.api.controller;

import br.com.alg.giraofoodapi.api.assembler.ProdutoInputDisassembler;
import br.com.alg.giraofoodapi.api.assembler.ProdutoModelAssembler;
import br.com.alg.giraofoodapi.api.assembler.RestauranteInputDisassembler;
import br.com.alg.giraofoodapi.api.model.dto.ProdutoDTO;
import br.com.alg.giraofoodapi.api.model.input.ProdutoInput;
import br.com.alg.giraofoodapi.domain.model.Produto;
import br.com.alg.giraofoodapi.domain.model.Restaurante;
import br.com.alg.giraofoodapi.domain.repository.ProdutoRepository;
import br.com.alg.giraofoodapi.domain.service.CadastroProdutoService;
import br.com.alg.giraofoodapi.domain.service.CadastrosRestauranteService;
import br.com.alg.giraofoodapi.openapi.controller.RestauranteProdutoControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{id}/produtos")
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

    @GetMapping
    public List<ProdutoDTO> listar(@PathVariable Long id, @RequestParam(required = false) boolean incluirInativos) {
        Restaurante restaurante = restauranteService.buscar(id);
        List<Produto> todosProdutos = null;
        if(incluirInativos) {
            todosProdutos = restaurante.getProdutos();
        } else {
            todosProdutos = produtoRepository.findAtivosByRestaurantes(restaurante);
        }
        return produtoModelAssembler.toCollectionDTO(todosProdutos);
    }

    @Override
    @GetMapping("/{produtoId}")
    public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = produtoService.buscarPeloRestaurante(produtoId, restauranteId);
        return produtoModelAssembler.toDTO(produto);
    }

    @PostMapping("/{produtoID}")
    public ProdutoDTO adicionar(@PathVariable Long id, @RequestBody @Valid ProdutoInput produtoInput) {
        Restaurante restaurante = restauranteService.buscar(id);
        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);
        return produtoModelAssembler.toDTO(produtoService.salvar(produto));
    }

    @PutMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ProdutoDTO atualizar(@PathVariable Long id, @PathVariable Long produtoId, @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoAtual = produtoService.buscar(produtoId);
        produtoInputDisassembler.copyToDomainInObject(produtoInput, produtoAtual);
        produtoAtual = produtoService.salvar(produtoAtual);
        return produtoModelAssembler.toDTO(produtoAtual);
    }

}

