package br.com.alg.giraofoodapi.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CozinhaInput {

    @ApiModelProperty(example = "Tailandesa", required = true)
    @NotBlank
    private String nome;
}
