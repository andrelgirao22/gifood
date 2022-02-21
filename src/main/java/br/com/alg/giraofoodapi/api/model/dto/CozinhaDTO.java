package br.com.alg.giraofoodapi.api.model.dto;

import br.com.alg.giraofoodapi.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaDTO {

    @ApiModelProperty(example = "1")
    @JsonView(RestauranteView.Resumo.class)
    private Long id;

    @ApiModelProperty(example = "Tailandesa")
    @JsonView(RestauranteView.Resumo.class)
    private String nome;


}
