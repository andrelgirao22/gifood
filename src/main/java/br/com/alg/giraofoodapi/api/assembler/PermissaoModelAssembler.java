package br.com.alg.giraofoodapi.api.assembler;

import br.com.alg.giraofoodapi.api.model.dto.PermissaoDTO;
import br.com.alg.giraofoodapi.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissaoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PermissaoModelAssembler assembler;


    public PermissaoDTO toDTO(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoDTO.class);
    }

    public List<PermissaoDTO> toCollectionDTO(Collection<Permissao> permissaos) {
        return permissaos.stream().map(permissao -> assembler.toDTO(permissao)).collect(Collectors.toList());
    }
}
