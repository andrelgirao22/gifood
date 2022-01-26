package br.com.alg.giraofoodapi.api.assembler;

import br.com.alg.giraofoodapi.api.model.input.UsuarioInput;
import br.com.alg.giraofoodapi.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Usuario toDomainObject(UsuarioInput usuario) {
        return modelMapper.map(usuario, Usuario.class);
    }

    public void copyToDomainInObject(UsuarioInput input, Usuario usuario) {
        modelMapper.map(input, usuario);
    }

}
