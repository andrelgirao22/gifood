package br.com.alg.giraofoodapi.api.model.dto;

import br.com.alg.giraofoodapi.domain.model.FormaPagamento;
import br.com.alg.giraofoodapi.domain.model.ItemPedido;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoDTO {

    private Long id;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status ;

    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;

    private FormaPagamentoDTO formaPagamento;
    private RestauranteResumoDTO restaurante;
    private UsuarioDTO cliente;

    private List<ItemPedidoDTO> itens;
    private EnderecoDTO endereco;
}