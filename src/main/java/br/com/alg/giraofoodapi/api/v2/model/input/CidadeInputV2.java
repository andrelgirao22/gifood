package br.com.alg.giraofoodapi.api.v2.model.input;

import br.com.alg.giraofoodapi.api.v1.model.input.EstadoInput;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeInputV2 {

    @ApiModelProperty(example = "Uberlândia", required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long idEstado;
}
