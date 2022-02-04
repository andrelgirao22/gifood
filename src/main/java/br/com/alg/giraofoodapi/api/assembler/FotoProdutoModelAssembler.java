package br.com.alg.giraofoodapi.api.assembler;

import br.com.alg.giraofoodapi.api.model.dto.CidadeDTO;
import br.com.alg.giraofoodapi.api.model.dto.FotoProdutoDTO;
import br.com.alg.giraofoodapi.domain.model.Cidade;
import br.com.alg.giraofoodapi.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FotoProdutoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FotoProdutoModelAssembler assembler;


    public FotoProdutoDTO toDTO(FotoProduto fotoProduto) {
        return modelMapper.map(fotoProduto, FotoProdutoDTO.class);
    }

}
