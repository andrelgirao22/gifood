package br.com.alg.giraofoodapi.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UsuarioInputComSenha extends UsuarioInput{

    @ApiModelProperty(example = "123", required = true)
    @NotBlank
    private String senha;
}
