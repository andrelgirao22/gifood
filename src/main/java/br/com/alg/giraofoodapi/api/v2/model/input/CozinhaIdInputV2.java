package br.com.alg.giraofoodapi.api.v2.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CozinhaIdInputV2 {

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long id;

}
