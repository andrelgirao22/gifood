package br.com.alg.giraofoodapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

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
}
