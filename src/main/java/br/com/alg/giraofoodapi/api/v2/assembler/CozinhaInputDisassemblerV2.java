package br.com.alg.giraofoodapi.api.v2.assembler;

import br.com.alg.giraofoodapi.api.v2.model.input.CozinhaInputV2;
import br.com.alg.giraofoodapi.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CozinhaInputDisassemblerV2 {

    @Autowired
    private ModelMapper modelMapper;

    public Cozinha toDomainObject(CozinhaInputV2 cozinhaInput) {
        return modelMapper.map(cozinhaInput, Cozinha.class);
    }

    public void copyToDomainInObject(CozinhaInputV2 input, Cozinha estado) {
        modelMapper.map(input, estado);
    }

}
