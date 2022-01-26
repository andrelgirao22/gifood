package br.com.alg.giraofoodapi.api.assembler;

import br.com.alg.giraofoodapi.api.model.dto.ProdutoDTO;
import br.com.alg.giraofoodapi.api.model.dto.UsuarioDTO;
import br.com.alg.giraofoodapi.domain.model.Produto;
import br.com.alg.giraofoodapi.domain.model.Usuario;
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


    public ProdutoDTO toDTO(Produto produto) {
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    public List<ProdutoDTO> toCollectionDTO(List<Produto> usuarios) {
        return usuarios.stream().map(produto -> assembler.toDTO(produto)).collect(Collectors.toList());
    }
}
