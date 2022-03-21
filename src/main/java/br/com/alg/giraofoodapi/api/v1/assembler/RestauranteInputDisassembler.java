package br.com.alg.giraofoodapi.api.v1.assembler;

import br.com.alg.giraofoodapi.domain.model.Cozinha;
import br.com.alg.giraofoodapi.domain.model.Endereco;
import br.com.alg.giraofoodapi.domain.model.Restaurante;
import br.com.alg.giraofoodapi.api.v1.model.input.RestauranteInput;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toDomainObject(RestauranteInput restauranteInput) {
        return modelMapper.map(restauranteInput, Restaurante.class);
    }

    public void copyToDomainInObject(RestauranteInput restauranteInput, Restaurante restaurante) {
        //para evitar a exception: org.hibernate.HibernateException: identifier of an instance of br.com.alg.giraofoodapi.domain.model.Cozinha was altered from 1 to 2
        restaurante.setCozinha(new Cozinha());

        if(restaurante.getEndereco() != null) {
            restaurante.setEndereco(new Endereco());
        }

        modelMapper.map(restauranteInput, restaurante);
    }

}
