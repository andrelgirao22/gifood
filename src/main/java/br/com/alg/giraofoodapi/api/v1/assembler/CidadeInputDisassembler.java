package br.com.alg.giraofoodapi.api.v1.assembler;

import br.com.alg.giraofoodapi.domain.model.Cidade;
import br.com.alg.giraofoodapi.domain.model.Estado;
import br.com.alg.giraofoodapi.api.v1.model.input.CidadeInput;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Cidade toDomainObject(CidadeInput cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyToDomainInObject(CidadeInput cidadeInput, Cidade cidade) {
        //para evitar a exception: org.hibernate.HibernateException: identifier of an instance of br.com.alg.giraofoodapi.domain.model.Cozinha was altered from 1 to 2
        cidade.setEstado(new Estado());
        modelMapper.map(cidadeInput, cidade);
    }

}
