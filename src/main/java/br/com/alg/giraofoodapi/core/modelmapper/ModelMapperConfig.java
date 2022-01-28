package br.com.alg.giraofoodapi.core.modelmapper;

import br.com.alg.giraofoodapi.api.model.dto.EnderecoDTO;
import br.com.alg.giraofoodapi.api.model.input.ItemPedidoInput;
import br.com.alg.giraofoodapi.domain.model.Endereco;
import br.com.alg.giraofoodapi.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        /*
        var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(
                Endereco.class, EnderecoDTO.class);

        enderecoToEnderecoModelTypeMap.addMapping(endereco -> endereco.getCidade().getNome(),
                (enderecoDTODest, value) -> enderecoDTODest.getCidade().setEstado(value));*/

        return modelMapper;
    }

}
