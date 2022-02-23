package br.com.alg.giraofoodapi.api.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResumoDTO {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Fortaleza")
    private String nome;

    @ApiModelProperty(example = "Ceará")
    private String estado;

}
