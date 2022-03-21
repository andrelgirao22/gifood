package br.com.alg.giraofoodapi.api.v1.assembler;

import br.com.alg.giraofoodapi.api.v1.model.input.ProdutoInput;
import br.com.alg.giraofoodapi.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Produto toDomainObject(ProdutoInput produto) {
        return modelMapper.map(produto, Produto.class);
    }

    public void copyToDomainInObject(ProdutoInput input, Produto produto) {
        modelMapper.map(input, produto);
    }

}
