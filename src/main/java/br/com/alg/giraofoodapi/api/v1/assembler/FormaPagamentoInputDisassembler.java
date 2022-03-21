package br.com.alg.giraofoodapi.api.v1.assembler;

import br.com.alg.giraofoodapi.domain.model.FormaPagamento;
import br.com.alg.giraofoodapi.api.v1.model.input.FormaPagamentoInput;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamento toDomainObject(FormaPagamentoInput input) {
        return modelMapper.map(input, FormaPagamento.class);
    }

    public void copyToDomainInObject(FormaPagamentoInput input, FormaPagamento formaPagamento) {
        modelMapper.map(input, formaPagamento);
    }

}
