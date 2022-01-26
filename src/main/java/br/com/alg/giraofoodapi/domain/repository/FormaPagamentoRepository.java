package br.com.alg.giraofoodapi.domain.repository;

import br.com.alg.giraofoodapi.domain.model.Cozinha;
import br.com.alg.giraofoodapi.domain.model.FormaPagamento;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {
}
