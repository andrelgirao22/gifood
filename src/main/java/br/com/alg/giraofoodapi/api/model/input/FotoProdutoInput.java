package br.com.alg.giraofoodapi.api.model.input;


import br.com.alg.giraofoodapi.core.validation.FileContentType;
import br.com.alg.giraofoodapi.core.validation.FileSize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FotoProdutoInput {

    @NotNull
    @FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    @FileSize(max = "3000KB")
    private MultipartFile arquivo;

    @NotBlank
    private String descricao;
}
