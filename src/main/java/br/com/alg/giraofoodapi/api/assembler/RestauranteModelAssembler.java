package br.com.alg.giraofoodapi.api.assembler;

import br.com.alg.giraofoodapi.domain.model.Restaurante;
import br.com.alg.giraofoodapi.api.model.dto.RestauranteDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestauranteModelAssembler assembler;


    public RestauranteDTO toDTO(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteDTO.class);
    }

    public List<RestauranteDTO> toCollectionDTO(List<Restaurante> restaurantes) {
        return restaurantes.stream().map(restaurante -> assembler.toDTO(restaurante)).collect(Collectors.toList());
    }
}
