package br.com.alg.giraofoodapi.api.v1.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Relation(collectionRelation = "pedidos")
@Getter
@Setter
public class PedidoModel extends RepresentationModel<PedidoModel> {

    private String codigo;

    @ApiModelProperty(example = "298.90")
    private BigDecimal subtotal;

    @ApiModelProperty(example = "11.90")
    private BigDecimal taxaFrete;

    @ApiModelProperty(example = "1238.90")
    private BigDecimal valorTotal;

    @ApiModelProperty(example = "CRIADO")
    private String status ;

    @ApiModelProperty(example = "2019-12-01T20:34:04Z")
    private OffsetDateTime dataCriacao;

    @ApiModelProperty(example = "2019-12-02T11:34:04Z")
    private OffsetDateTime dataConfirmacao;

    @ApiModelProperty(example = "2019-12-02T12:34:04Z")
    private OffsetDateTime dataCancelamento;

    @ApiModelProperty(example = "2019-12-02T20:34:04Z")
    private OffsetDateTime dataEntrega;

    private FormaPagamentoModel formaPagamento;
    private RestauranteApenasNomeModel restaurante;
    private UsuarioModel cliente;

    private List<ItemPedidoModel> itens;
    private EnderecoDTO endereco;
}