package br.com.alg.giraofoodapi.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class ItemPedidoInput {

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long produtoId;

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    @PositiveOrZero
    private Integer quantidade;

    @ApiModelProperty(example = "Sem molho picante, por favor")
    private String observacao;
}
