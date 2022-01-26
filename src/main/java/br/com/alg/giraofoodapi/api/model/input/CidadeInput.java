package br.com.alg.giraofoodapi.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeInput {

    @NotNull
    private Long id;

}
