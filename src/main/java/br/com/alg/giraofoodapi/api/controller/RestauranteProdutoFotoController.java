package br.com.alg.giraofoodapi.api.controller;

import br.com.alg.giraofoodapi.api.assembler.FotoProdutoModelAssembler;
import br.com.alg.giraofoodapi.api.model.dto.FotoProdutoDTO;
import br.com.alg.giraofoodapi.api.model.input.FotoProdutoInput;
import br.com.alg.giraofoodapi.domain.model.FotoProduto;
import br.com.alg.giraofoodapi.domain.model.Produto;
import br.com.alg.giraofoodapi.domain.service.CadastroProdutoService;
import br.com.alg.giraofoodapi.domain.service.CatalogoFotoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    @Autowired
    private CatalogoFotoProdutoService produtoService;

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Autowired
    private FotoProdutoModelAssembler assembler;

    @GetMapping
    public FotoProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        FotoProduto fotoProduto = produtoService.buscar(restauranteId, produtoId);
        return assembler.toDTO(fotoProduto);
    }

    @PutMapping
    public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId,
                                        @PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) throws IOException {

        Produto produto = cadastroProdutoService.buscarPeloRestaurante(restauranteId, produtoId);

        MultipartFile arquivo = fotoProdutoInput.getArquivo();

        FotoProduto fotoProduto = new FotoProduto();
        fotoProduto.setProduto(produto);
        fotoProduto.setContentType(arquivo.getContentType());
        fotoProduto.setNomeArquivo(arquivo.getOriginalFilename());
        fotoProduto.setTamanho(arquivo.getSize());
        fotoProduto.setDescricao(fotoProdutoInput.getDescricao());

        fotoProduto = produtoService.salvar(fotoProduto, arquivo.getInputStream());

        return assembler.toDTO(fotoProduto);
    }

}
