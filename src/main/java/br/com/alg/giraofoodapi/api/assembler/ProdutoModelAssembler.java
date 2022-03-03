package br.com.alg.giraofoodapi.api.assembler;

import br.com.alg.giraofoodapi.api.model.dto.ProdutoModel;
import br.com.alg.giraofoodapi.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProdutoModelAssembler assembler;


    public ProdutoModel toDTO(Produto produto) {
        return modelMapper.map(produto, ProdutoModel.class);
    }

    public List<ProdutoModel> toCollectionDTO(List<Produto> usuarios) {
        return usuarios.stream().map(produto -> assembler.toDTO(produto)).collect(Collectors.toList());
    }
}
