package br.com.alg.giraofoodapi.openapi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedModelOpenApi<T> {

    @ApiModelProperty(value = "Conteúdo")
    private List<T> content;

    @ApiModelProperty(example = "10", value = "Quantidade de  registros por página")
    private Long size;

    @ApiModelProperty(example = "50", value = "Total de registros")
    private Long totalElements;

    @ApiModelProperty(example = "5", value = "Total de páginas")
    private Long totalPages;

    @ApiModelProperty(example = "0", value = "Número da página (começa com 0)")
    private Long number;

}