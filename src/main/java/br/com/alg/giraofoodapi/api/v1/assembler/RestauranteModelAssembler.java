package br.com.alg.giraofoodapi.api.v1.assembler;

import br.com.alg.giraofoodapi.api.v1.GiLinksV1;
import br.com.alg.giraofoodapi.api.v1.controller.RestauranteController;
import br.com.alg.giraofoodapi.api.v1.model.dto.RestauranteModel;
import br.com.alg.giraofoodapi.core.security.GiSecurity;
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

    @Autowired
    private GiSecurity giSecurity;

    public RestauranteModelAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }

    @Override
    public RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);

        if(giSecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(giLinks.linkToRestaurantes());
        }

        if(giSecurity.podeGerenciarCadastroRestaurantes()) {
            if(restaurante.ativacaoPermitida()) {
                restauranteModel.add(giLinks.linkToRestauranteAtivacao(restauranteModel.getId()));
            }

            if(restaurante.inativacaoPermitida()) {
                restauranteModel.add(giLinks.linkToRestauranteInativacao(restauranteModel.getId()));
            }
        }

        if(giSecurity.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {
            if(restaurante.fechamentoPermitido()) {
                restauranteModel.add(giLinks.linkToRestauranteFechamento(restauranteModel.getId()));
            }

            if(restaurante.aberturaPermitida()) {
                restauranteModel.add(giLinks.linkToRestauranteAbertura(restauranteModel.getId()));
            }
        }

        if(giSecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(giLinks.linkToProdutos(restauranteModel.getId(), "produtos"));
        }

        if(giSecurity.podeConsultarCozinhas()) {
            restauranteModel.getCozinha().add(giLinks.linkToCozinha(restauranteModel.getId()));
        }

        if(giSecurity.podeConsultarCidades()) {
            if(restauranteModel.getEndereco() != null && restauranteModel.getEndereco().getCidade() != null) {
                restauranteModel.getEndereco().getCidade()
                        .add(giLinks.linkToCidade(restauranteModel.getEndereco().getCidade().getId()));
            }
        }

        if(giSecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(giLinks.linkToRestaurantesFormasPagamento(restauranteModel.getId()));
        }

        if(giSecurity.podeGerenciarCadastroRestaurantes()) {
            restauranteModel.add(giLinks.linkToRestaurantesResponsaveis(restauranteModel.getId(),"responsaveis"));
        }


        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {

        CollectionModel<RestauranteModel> collectionModel = super.toCollectionModel(entities);

        if(giSecurity.podeConsultarRestaurantes()) {
            collectionModel.add(giLinks.linkToRestaurantes("restaurantes"));
        }

        return collectionModel;
    }
}