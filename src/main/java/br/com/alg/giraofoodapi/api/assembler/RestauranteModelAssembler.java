package br.com.alg.giraofoodapi.api.assembler;

import br.com.alg.giraofoodapi.api.GiLinks;
import br.com.alg.giraofoodapi.api.controller.RestauranteController;
import br.com.alg.giraofoodapi.api.model.dto.RestauranteModel;
import br.com.alg.giraofoodapi.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestauranteModelAssembler assembler;

    @Autowired
    private GiLinks giLinks;

    public RestauranteModelAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }

    @Override
    public RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);

        restauranteModel.getCozinha().add(giLinks.linkToCozinha(restauranteModel.getId()));

        restauranteModel.add(giLinks.linkToRestaurantes());
        restauranteModel.add(giLinks.linkToRestaurantesFormasPagamento(restauranteModel.getId()));
        restauranteModel.add(giLinks.linkToRestaurantesResponsaveis(restauranteModel.getId()));

        if(restaurante.fechamentoPermitido()) {
            restauranteModel.add(giLinks.linkToRestauranteFechamento(restauranteModel.getId()));
        }

        if(restaurante.aberturaPermitida()) {
            restauranteModel.add(giLinks.linkToRestauranteAbertura(restauranteModel.getId()));
        }

        if(restaurante.inativacaoPermitida()) {
            restauranteModel.add(giLinks.linkToRestauranteInativacao(restauranteModel.getId()));
        }

        if(restaurante.ativacaoPermitida()) {
            restauranteModel.add(giLinks.linkToRestauranteAtivacao(restauranteModel.getId()));
        }

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities).add(giLinks.linkToRestaurantes("restaurantes"));
    }
}