package br.com.alg.giraofoodapi.api.v1.assembler;

import br.com.alg.giraofoodapi.api.v1.GiLinksV1;
import br.com.alg.giraofoodapi.api.v1.controller.RestauranteController;
import br.com.alg.giraofoodapi.api.v1.model.dto.RestauranteBasicoModel;
import br.com.alg.giraofoodapi.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteBasicoModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GiLinksV1 giLinks;

    public RestauranteBasicoModelAssembler() {
        super(RestauranteController.class, RestauranteBasicoModel.class);
    }

    @Override
    public RestauranteBasicoModel toModel(Restaurante restaurante) {
        RestauranteBasicoModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);

        restauranteModel.add(giLinks.linkToRestaurantes("restaurantes"));

        restauranteModel.getCozinha().add(
                giLinks.linkToCozinha(restaurante.getCozinha().getId()));

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteBasicoModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities).add(giLinks.linkToRestaurantes());
    }
}