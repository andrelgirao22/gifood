package br.com.alg.giraofoodapi.api.controller;

import br.com.alg.giraofoodapi.api.model.input.FotoProdutoInput;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    @PutMapping
    public void atualizarFoto(@PathVariable Long restauranteId,
                              @PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) {
        var nomeArquivo = UUID.randomUUID().toString() + "_" + fotoProdutoInput.getArquivo().getOriginalFilename();
        var arquivoFoto = Path.of("", nomeArquivo);
        try {
            System.out.println(fotoProdutoInput.getDescricao());
            fotoProdutoInput.getArquivo().transferTo(arquivoFoto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
