package br.com.alg.giraofoodapi.api.model.dto;

import br.com.alg.giraofoodapi.domain.model.FormaPagamento;
import br.com.alg.giraofoodapi.domain.model.ItemPedido;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoDTO {

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

    private FormaPagamentoDTO formaPagamento;
    private RestauranteResumoDTO restaurante;
    private UsuarioDTO cliente;

    private List<ItemPedidoDTO> itens;
    private EnderecoDTO endereco;
}