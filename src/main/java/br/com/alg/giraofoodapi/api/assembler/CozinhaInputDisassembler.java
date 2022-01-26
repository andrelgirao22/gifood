package br.com.alg.giraofoodapi.api.assembler;

import br.com.alg.giraofoodapi.domain.model.Cozinha;
import br.com.alg.giraofoodapi.api.model.input.CozinhaInput;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CozinhaInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Cozinha toDomainObject(CozinhaInput cozinhaInput) {
        return modelMapper.map(cozinhaInput, Cozinha.class);
    }

    public void copyToDomainInObject(CozinhaInput input, Cozinha estado) {
        modelMapper.map(input, estado);
    }

}
