package br.com.alg.giraofoodapi.domain.service;

import br.com.alg.giraofoodapi.domain.exception.PermissaoNaoEncontradaException;
import br.com.alg.giraofoodapi.domain.model.Permissao;
import br.com.alg.giraofoodapi.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroPermissaoService {

    @Autowired
    private PermissaoRepository repository;

    public Permissao buscar(Long permissaoId) {
        return repository.findById(permissaoId)
                .orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
    }



}
