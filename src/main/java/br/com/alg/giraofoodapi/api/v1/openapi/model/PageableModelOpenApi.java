package br.com.alg.giraofoodapi.api.v1.openapi.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Api(value = "Pageable")
public class PageableModelOpenApi {

    @ApiModelProperty(value = "Número da página (começa  com 0)", example = "0")
    private int page;

    @ApiModelProperty(value = "Quantidade de elementos por página", example = "10")
    private int size;

    @ApiModelProperty(value = "Nome da propriedade  para ordenação ", example = "nome,asc")
    private List<String> sort;

}
