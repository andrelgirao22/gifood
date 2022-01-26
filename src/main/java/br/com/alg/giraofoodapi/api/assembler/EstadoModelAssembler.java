package br.com.alg.giraofoodapi.api.assembler;

import br.com.alg.giraofoodapi.domain.model.Estado;
import br.com.alg.giraofoodapi.api.model.dto.EstadoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EstadoModelAssembler assembler;


    public EstadoDTO toDTO(Estado estado) {
        return modelMapper.map(estado, EstadoDTO.class);
    }

    public List<EstadoDTO> toCollectionDTO(List<Estado> estados) {
        return estados.stream().map(estado -> assembler.toDTO(estado)).collect(Collectors.toList());
    }
}
