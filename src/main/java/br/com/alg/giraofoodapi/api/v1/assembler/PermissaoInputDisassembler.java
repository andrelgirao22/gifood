package br.com.alg.giraofoodapi.api.v1.assembler;

import br.com.alg.giraofoodapi.api.v1.model.input.GrupoInput;
import br.com.alg.giraofoodapi.api.v1.model.input.PermissaoInput;
import br.com.alg.giraofoodapi.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissaoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Permissao toDomainObject(GrupoInput input) {
        return modelMapper.map(input, Permissao.class);
    }

    public void copyToDomainInObject(PermissaoInput input, Permissao permissao) {
        modelMapper.map(input, permissao);
    }

}
