package br.com.alg.giraofoodapi.domain.service;

import br.com.alg.giraofoodapi.domain.exception.CidadeNaoEncontradaException;
import br.com.alg.giraofoodapi.domain.exception.EntidadeEmUsoException;
import br.com.alg.giraofoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.alg.giraofoodapi.domain.model.Cidade;
import br.com.alg.giraofoodapi.domain.model.Estado;
import br.com.alg.giraofoodapi.domain.repository.CidadeRepository;
import br.com.alg.giraofoodapi.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository repository;

    @Autowired
    private CadastroEstadoService estadoService;

    public Cidade buscar(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new CidadeNaoEncontradaException(id));
    }

    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Estado estado = this.estadoService.buscar(estadoId);
        cidade.setEstado(estado);
        return this.repository.save(cidade);
    }

    public void remover(Long id) {
        Cidade cidade = this.buscar(id);
        try {
            this.repository.delete(cidade);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Cidade de código %d não pode ser removido, pois está em uso", id));
        }
    }
}
