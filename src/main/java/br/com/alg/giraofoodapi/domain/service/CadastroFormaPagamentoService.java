package br.com.alg.giraofoodapi.domain.service;

import br.com.alg.giraofoodapi.domain.exception.EntidadeEmUsoException;
import br.com.alg.giraofoodapi.domain.exception.FormaPagamentoNaoEncontradoException;
import br.com.alg.giraofoodapi.domain.model.FormaPagamento;
import br.com.alg.giraofoodapi.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroFormaPagamentoService {

    private static final String MSG_FORMA_PAGAMENTO_EM_USO
            = "Forma de pagamento de código %d não pode ser removida, pois está em uso";

    @Autowired
    private FormaPagamentoRepository repository;

    public List<FormaPagamento> lista() {
        return this.repository.findAll();
    }

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return this.repository.save(formaPagamento);
    }

    public FormaPagamento buscar(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new FormaPagamentoNaoEncontradoException(id));
    }

    @Transactional
    public void delete(Long id) {
        FormaPagamento formaPagamento = buscar(id);
        try {
            repository.delete(formaPagamento);
            repository.flush();
        }  catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_FORMA_PAGAMENTO_EM_USO, id));
        }
    }

}
