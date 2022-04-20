package br.com.alg.giraofoodapi.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
@ApiModel("Problema")
public class Problem {

    @ApiModelProperty(example = "400", position = 1)
    private Integer status;

    @ApiModelProperty(example = "http://www.gifood.com.br//dados-invalidos", position = 5)
    private String type;

    @ApiModelProperty(example = "Dados inválidos",position = 6)
    private String title;

    @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
    private String detail;

    @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
    private String userMessage;

    @ApiModelProperty(example = "2022-02-20T17:41:00.0713145Z")
    private OffsetDateTime timestamp;
    private List<Object> objects;

    @ApiModel("ObjetoModelo")
    @Getter
    @Builder
    public static class Object {
        private String name;
        private String userMessage;
    }
}
