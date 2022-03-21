package br.com.alg.giraofoodapi.api.v1.assembler;

import br.com.alg.giraofoodapi.api.v1.GiLinksV1;
import br.com.alg.giraofoodapi.api.v1.controller.RestauranteController;
import br.com.alg.giraofoodapi.api.v1.model.dto.RestauranteModel;
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
    private GiLinksV1 giLinks;

    public RestauranteModelAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }

    @Override
    public RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);

        restauranteModel.add(giLinks.linkToRestaurantes());

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

        restauranteModel.getCozinha().add(giLinks.linkToCozinha(restauranteModel.getId()));
        if(restauranteModel.getEndereco() != null && restauranteModel.getEndereco().getCidade() != null) {
            restauranteModel.getEndereco().getCidade()
                    .add(giLinks.linkToCidade(restauranteModel.getEndereco().getCidade().getId()));
        }
        restauranteModel.add(giLinks.linkToRestaurantesFormasPagamento(restauranteModel.getId()));
        restauranteModel.add(giLinks.linkToRestaurantesResponsaveis(restauranteModel.getId(),"responsaveis"));
        restauranteModel.add(giLinks.linkToProdutos(restauranteModel.getId(), "produtos"));


        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities).add(giLinks.linkToRestaurantes("restaurantes"));
    }
}