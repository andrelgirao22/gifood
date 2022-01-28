package br.com.alg.giraofoodapi.domain.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ItemPedido {

    @EqualsAndHashCode.Include
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_pedido_seq")
    @SequenceGenerator(name = "item_pedido_seq", sequenceName = "item_pedido_seq", allocationSize = 1)
    private Long id;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    public void calculaValorTotal() {
        var precoUnitario = getPrecoUnitario();
        var quantidade = BigDecimal.valueOf(getQuantidade());

        if(precoUnitario == null) {
            precoUnitario = BigDecimal.ZERO;
        }

        if(quantidade == null) {
            quantidade = BigDecimal.ZERO;
        }

        precoTotal = precoUnitario.multiply(quantidade);
    }
}
