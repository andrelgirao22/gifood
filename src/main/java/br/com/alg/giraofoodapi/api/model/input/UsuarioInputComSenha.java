package br.com.alg.giraofoodapi.api.model.input;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UsuarioInputComSenha extends UsuarioInput{

    @NotBlank
    private String senha;
}
