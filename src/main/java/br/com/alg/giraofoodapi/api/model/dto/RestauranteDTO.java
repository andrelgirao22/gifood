package br.com.alg.giraofoodapi.api.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteDTO {

    private Long id;
    private String nome;
    private BigDecimal taxaFrete;

    private Boolean ativo;
    private Boolean aberto;
    private CozinhaDTO cozinha;

    private EnderecoDTO endereco;


}
