package br.com.alg.giraofoodapi.domain.service;

import br.com.alg.giraofoodapi.domain.exception.EntidadeEmUsoException;
import br.com.alg.giraofoodapi.domain.exception.EstadoNaoEncontradoException;
import br.com.alg.giraofoodapi.domain.model.Estado;
import br.com.alg.giraofoodapi.domain.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CadastroEstadoService {

    @Autowired
    private EstadoRepository repository;

    public Estado buscar(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() ->  new EstadoNaoEncontradoException(id));
    }

    public Estado salvar(Estado estado) {
        return this.repository.save(estado);
    }

    public void excluir(Long id) {
        Estado estado = this.buscar(id);
        try {
            this.repository.delete(estado);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Estado de código %d não pode ser removido, pois está em uso", id));
        }
    }

    public Estado alterar(Long id, Estado estado) {
        Estado estadoAtual = this.buscar(id);
        BeanUtils.copyProperties(estado, estadoAtual, "id");
        return this.repository.save(estadoAtual);
    }
}
