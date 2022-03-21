package br.com.alg.giraofoodapi.core.modelmapper;

import br.com.alg.giraofoodapi.api.v1.model.input.ItemPedidoInput;
import br.com.alg.giraofoodapi.api.v2.model.input.CidadeInputV2;
import br.com.alg.giraofoodapi.domain.model.Cidade;
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

        modelMapper.createTypeMap(CidadeInputV2.class, Cidade.class)
                .addMappings(mapper -> mapper.skip(Cidade::setId));

        /*
        var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(
                Endereco.class, EnderecoDTO.class);

        enderecoToEnderecoModelTypeMap.addMapping(endereco -> endereco.getCidade().getNome(),
                (enderecoDTODest, value) -> enderecoDTODest.getCidade().setEstado(value));*/

        return modelMapper;
    }

}
