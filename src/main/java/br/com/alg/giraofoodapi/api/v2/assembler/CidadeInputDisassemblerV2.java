package br.com.alg.giraofoodapi.api.v2.assembler;

import br.com.alg.giraofoodapi.api.v1.model.input.CidadeInput;
import br.com.alg.giraofoodapi.api.v2.model.input.CidadeInputV2;
import br.com.alg.giraofoodapi.domain.model.Cidade;
import br.com.alg.giraofoodapi.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeInputDisassemblerV2 {

    @Autowired
    private ModelMapper modelMapper;

    public Cidade toDomainObject(CidadeInputV2 cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyToDomainInObject(CidadeInputV2 cidadeInput, Cidade cidade) {
        //para evitar a exception: org.hibernate.HibernateException: identifier of an instance of br.com.alg.giraofoodapi.domain.model.Cozinha was altered from 1 to 2
        cidade.setEstado(new Estado());
        modelMapper.map(cidadeInput, cidade);
    }

}
