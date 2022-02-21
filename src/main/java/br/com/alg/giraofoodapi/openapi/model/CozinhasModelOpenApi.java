package br.com.alg.giraofoodapi.openapi.model;

import br.com.alg.giraofoodapi.api.model.dto.CozinhaDTO;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhasModel")
@Getter
@Setter
public class CozinhasModelOpenApi extends PagedModelOpenApi<CozinhaDTO> {

}
