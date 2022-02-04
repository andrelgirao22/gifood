package br.com.alg.giraofoodapi.api.model.input;


import br.com.alg.giraofoodapi.core.validation.FileSize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FotoProdutoInput {

    @NotNull
    @FileSize(max = "100KB")
    private MultipartFile arquivo;

    @NotBlank
    private String descricao;
}
