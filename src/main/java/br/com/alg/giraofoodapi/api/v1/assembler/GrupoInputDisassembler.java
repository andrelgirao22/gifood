package br.com.alg.giraofoodapi.api.v1.assembler;

import br.com.alg.giraofoodapi.api.v1.model.input.GrupoInput;
import br.com.alg.giraofoodapi.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrupoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Grupo toDomainObject(GrupoInput input) {
        return modelMapper.map(input, Grupo.class);
    }

    public void copyToDomainInObject(GrupoInput input, Grupo grupo) {
        modelMapper.map(input, grupo);
    }

}
