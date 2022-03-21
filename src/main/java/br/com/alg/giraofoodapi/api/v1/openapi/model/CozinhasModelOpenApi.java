package br.com.alg.giraofoodapi.api.v1.openapi.model;

import br.com.alg.giraofoodapi.api.v1.model.dto.CozinhaModel;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CozinhasModel")
@Getter
@Setter
public class CozinhasModelOpenApi {

    private CozinhasEmbeddedModelApi _embedded;
    private Links _links;

    private PageModelOpenApi page;

    @Getter
    @Setter
    @ApiModel("CidadesEmbeddedModel")
    public class  CozinhasEmbeddedModelApi {

        private List<CozinhaModel> cozinhas;

    }

}
