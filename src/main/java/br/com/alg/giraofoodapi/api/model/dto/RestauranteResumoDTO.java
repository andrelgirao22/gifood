package br.com.alg.giraofoodapi.api.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteResumoDTO {

    @ApiModelProperty(example = "1", required = true)
    private Long id;

    @ApiModelProperty(example = "Santa Grelha", required = true)
    private String nome;

}
