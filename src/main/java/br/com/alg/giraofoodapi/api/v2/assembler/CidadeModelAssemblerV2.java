package br.com.alg.giraofoodapi.api.v2.assembler;

import br.com.alg.giraofoodapi.api.v1.GiLinksV1;
import br.com.alg.giraofoodapi.api.v1.controller.CidadeController;
import br.com.alg.giraofoodapi.api.v1.model.dto.CidadeModel;
import br.com.alg.giraofoodapi.api.v2.controller.CidadeControllerV2;
import br.com.alg.giraofoodapi.api.v2.model.CidadeModelV2;
import br.com.alg.giraofoodapi.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CidadeModelAssemblerV2 extends RepresentationModelAssemblerSupport<Cidade, CidadeModelV2> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CidadeModelAssemblerV2 assembler;

    @Autowired
    private GiLinksV1 giLinks;

    public CidadeModelAssemblerV2() {
        super(CidadeControllerV2.class, CidadeModelV2.class);
    }

    @Override
    public CollectionModel<CidadeModelV2> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(giLinks.linkToCidades());
    }

    @Override
    public CidadeModelV2 toModel(Cidade cidade) {
        CidadeModelV2 cidadeModel = createModelWithId(cidade.getId(), cidade);
        modelMapper.map(cidade, cidadeModel);

        cidadeModel.add(giLinks.linkToCidades("cidades"));

        return cidadeModel;
    }
}
