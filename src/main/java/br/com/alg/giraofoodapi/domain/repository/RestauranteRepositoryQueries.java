package br.com.alg.giraofoodapi.domain.repository;

import br.com.alg.giraofoodapi.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteRepositoryQueries {

    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

    public List<Restaurante> findComFreteGratis(String nome);
}
