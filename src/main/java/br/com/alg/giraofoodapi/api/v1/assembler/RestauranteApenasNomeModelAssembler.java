package br.com.alg.giraofoodapi.api.v1.assembler;

import br.com.alg.giraofoodapi.api.v1.GiLinksV1;
import br.com.alg.giraofoodapi.api.v1.controller.RestauranteController;
import br.com.alg.giraofoodapi.api.v1.model.dto.RestauranteApenasNomeModel;
import br.com.alg.giraofoodapi.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class RestauranteApenasNomeModelAssembler
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GiLinksV1 giLinks;

    public RestauranteApenasNomeModelAssembler() {
        super(RestauranteController.class, RestauranteApenasNomeModel.class);
    }

    @Override
    public RestauranteApenasNomeModel toModel(Restaurante restaurante) {
        RestauranteApenasNomeModel restauranteModel = createModelWithId(
                restaurante.getId(), restaurante);

        modelMapper.map(restaurante, restauranteModel);

        restauranteModel.add(giLinks.linkToRestaurantes("restaurantes"));

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteApenasNomeModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(giLinks.linkToRestaurantes());
    }
}