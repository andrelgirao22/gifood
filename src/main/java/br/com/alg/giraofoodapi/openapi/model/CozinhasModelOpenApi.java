package br.com.alg.giraofoodapi.openapi.model;

import br.com.alg.giraofoodapi.api.model.dto.CozinhaModel;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhasModel")
@Getter
@Setter
public class CozinhasModelOpenApi extends PagedModelOpenApi<CozinhaModel> {

}
