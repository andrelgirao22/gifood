package br.com.alg.giraofoodapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.sf.jasperreports.engine.export.draw.Offset;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class FormaPagamento {

    @EqualsAndHashCode.Include
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "forma_pagamento_seq")
    @SequenceGenerator(name = "forma_pagamento_seq", sequenceName = "forma_pagamento_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @UpdateTimestamp
    private OffsetDateTime dataAtualizacao;
}
