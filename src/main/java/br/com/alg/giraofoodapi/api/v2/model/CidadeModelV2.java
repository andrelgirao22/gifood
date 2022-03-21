

package br.com.alg.giraofoodapi.api.v2.model;

import br.com.alg.giraofoodapi.api.v1.model.dto.EstadoModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeModelV2 extends RepresentationModel<CidadeModelV2> {

    @ApiModelProperty(example = "1")
    private Long idCidade;

    @ApiModelProperty(example = "Uberlândia")
    private String nomeCidade;

    private Long idEstado;
    private String nomeEstado;

}
