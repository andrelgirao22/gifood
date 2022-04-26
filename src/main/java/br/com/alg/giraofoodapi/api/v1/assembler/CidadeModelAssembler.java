package br.com.alg.giraofoodapi.api.v1.assembler;

import br.com.alg.giraofoodapi.api.v1.GiLinksV1;
import br.com.alg.giraofoodapi.api.v1.controller.CidadeController;
import br.com.alg.giraofoodapi.core.security.GiSecurity;
import br.com.alg.giraofoodapi.domain.model.Cidade;
import br.com.alg.giraofoodapi.api.v1.model.dto.CidadeModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CidadeModelAssembler assembler;

    @Autowired
    private GiLinksV1 giLinks;

    @Autowired
    private GiSecurity giSecurity;

    public CidadeModelAssembler() {
        super(CidadeController.class, CidadeModel.class);
    }

    @Override
    public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
        CollectionModel<CidadeModel> collection = super.toCollectionModel(entities);
        if(giSecurity.podeConsultarCidades()) {
            collection.add(giLinks.linkToCidades());
        }
        return collection;
    }

    @Override
    public CidadeModel toModel(Cidade cidade) {
        CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);
        modelMapper.map(cidade, cidadeModel);

        if(giSecurity.podeConsultarCidades()) {
            cidadeModel.add(giLinks.linkToCidades("cidades"));
        }

        if(giSecurity.podeConsultarEstados()) {
            cidadeModel.getEstado().add(giLinks.linkToCidade(cidadeModel.getEstado().getId()));
            //cidadeDTO.add(Link.of("http://localhost:8080/cidades", IanaLinkRelations.COLLECTION));
            cidadeModel.getEstado().add(giLinks.linkToCidades("estados"));
        }

        return cidadeModel;
    }
}
