package br.com.alg.giraofoodapi.openapi.model;

import br.com.alg.giraofoodapi.api.model.dto.PedidoModel;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PedidosModel")
@Getter
@Setter
public class PedidoModelOpenApi extends PagedModelOpenApi<PedidoModel> {

}
