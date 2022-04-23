package br.com.alg.giraofoodapi.api.v1.controller;

import br.com.alg.giraofoodapi.api.v1.assembler.FotoProdutoModelAssembler;
import br.com.alg.giraofoodapi.api.v1.model.dto.FotoProdutoModel;
import br.com.alg.giraofoodapi.api.v1.model.input.FotoProdutoInput;
import br.com.alg.giraofoodapi.core.security.CheckSecurity;
import br.com.alg.giraofoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.alg.giraofoodapi.domain.model.FotoProduto;
import br.com.alg.giraofoodapi.domain.model.Produto;
import br.com.alg.giraofoodapi.domain.service.CadastroProdutoService;
import br.com.alg.giraofoodapi.domain.service.CatalogoFotoProdutoService;
import br.com.alg.giraofoodapi.domain.service.FotoStorageService;
import br.com.alg.giraofoodapi.domain.service.FotoStorageService.FotoRecuperada;
import br.com.alg.giraofoodapi.api.v1.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi {

    @Autowired
    private CatalogoFotoProdutoService produtoService;

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Autowired
    private FotoStorageService fotoStorageService;

    @Autowired
    private FotoProdutoModelAssembler assembler;

    @Override
    @CheckSecurity.Restaurante.PodeConsultar
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoModel buscar(@PathVariable Long restauranteId,
                                   @PathVariable Long produtoId) {
        FotoProduto fotoProduto = produtoService.buscar(restauranteId, produtoId);

        return assembler.toModel(fotoProduto);
    }

    @CheckSecurity.Restaurante.PodeConsultar
    @GetMapping(produces = MediaType.ALL_VALUE)
    public ResponseEntity<?> servir(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                                          @RequestHeader("accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
        try {
            FotoProduto fotoProduto = produtoService.buscar(restauranteId, produtoId);

            MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());

            List<MediaType> mediatypeAceitas = MediaType.parseMediaTypes(acceptHeader);

            verificaCompatibilidadeMediaType(mediaTypeFoto, mediatypeAceitas);

            FotoRecuperada fotoRecuperada =
                    fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
            if(fotoRecuperada.temUrl()) {
                return ResponseEntity.status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
                        .build();
            } else {
                return ResponseEntity.ok()
                        .contentType(mediaTypeFoto)
                        .body(new InputStreamResource(fotoRecuperada.getInputStream()));
            }

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private void verificaCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediatypeAceitas) throws HttpMediaTypeNotAcceptableException {
        boolean isCompativel = mediatypeAceitas.stream()
                .anyMatch(mediaType -> mediaType.isCompatibleWith(mediaTypeFoto));
        if(!isCompativel) {
            throw new HttpMediaTypeNotAcceptableException(mediatypeAceitas);
        }
    }

    @CheckSecurity.Restaurante.PodeGerenciarFuncionamento
    @PutMapping
    public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId,
                                          @PathVariable Long produtoId,
                                          @Valid FotoProdutoInput fotoProdutoInput,
                                          @RequestPart(required = true) MultipartFile arquivo)  throws IOException {

        Produto produto = cadastroProdutoService.buscarPeloRestaurante(restauranteId, produtoId);

        //MultipartFile arquivo = fotoProdutoInput.getArquivo();

        FotoProduto fotoProduto = new FotoProduto();
        fotoProduto.setProduto(produto);
        fotoProduto.setContentType(arquivo.getContentType());
        fotoProduto.setNomeArquivo(arquivo.getOriginalFilename());
        fotoProduto.setTamanho(arquivo.getSize());
        fotoProduto.setDescricao(fotoProdutoInput.getDescricao());

        fotoProduto = produtoService.salvar(fotoProduto, arquivo.getInputStream());

        return assembler.toModel(fotoProduto);
    }

    @CheckSecurity.Restaurante.PodeGerenciarFuncionamento
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        produtoService.excluir(restauranteId, produtoId);
    }

}
