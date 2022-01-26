package br.com.alg.giraofoodapi.domain.service;

import br.com.alg.giraofoodapi.domain.exception.CozinhaNaoEncontradaException;
import br.com.alg.giraofoodapi.domain.exception.EntidadeEmUsoException;
import br.com.alg.giraofoodapi.domain.model.Cozinha;
import br.com.alg.giraofoodapi.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
public class CadastroCozinhaService {

    private final String MSG_COZINHA_EM_USO= "Cozinha em uso";

    @Autowired
    private CozinhaRepository repository;

    @Transactional
    public Cozinha salvar(@Valid Cozinha cozinha) {
        return this.repository.save(cozinha);
    }

    public Cozinha buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CozinhaNaoEncontradaException(id));
    }

    @Transactional
    public void excluir(Long id) {
        try {
            repository.deleteById(id);
            repository.flush();
        } catch (EmptyResultDataAccessException ere) {
            throw new CozinhaNaoEncontradaException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(MSG_COZINHA_EM_USO);
        }
    }
}