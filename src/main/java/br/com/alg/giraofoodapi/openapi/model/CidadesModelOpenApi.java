package br.com.alg.giraofoodapi.openapi.model;

import br.com.alg.giraofoodapi.api.model.dto.CidadeModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CidadesModel")
@Data
public class CidadesModelOpenApi {

    private CidadeEmbeddedModelApi _embedded;
    private Links _links;

    @Getter
    @Setter
    @ApiModel("CidadesEmbeddedModel")
    public class  CidadeEmbeddedModelApi {

        private List<CidadeModel> cidades;

    }

}
