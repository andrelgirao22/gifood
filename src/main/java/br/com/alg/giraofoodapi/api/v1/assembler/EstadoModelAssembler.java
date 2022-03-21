package br.com.alg.giraofoodapi.api.v1.assembler;

import br.com.alg.giraofoodapi.api.v1.GiLinksV1;
import br.com.alg.giraofoodapi.api.v1.controller.EstadoController;
import br.com.alg.giraofoodapi.domain.model.Estado;
import br.com.alg.giraofoodapi.api.v1.model.dto.EstadoModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class EstadoModelAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EstadoModelAssembler assembler;

    @Autowired
    private GiLinksV1 giLinks;

    public EstadoModelAssembler() {
        super(EstadoController.class, EstadoModel.class);
    }

    @Override
    public EstadoModel toModel(Estado estado) {

        EstadoModel estadoModel = createModelWithId(estado.getId(), estado);
        modelMapper.map(estado, estadoModel);

        estadoModel.add(giLinks.linkToEstados("estados"));
        estadoModel.add(giLinks.linkToEstado(estado.getId(), "estados"));

        return estadoModel;
    }

    @Override
    public CollectionModel<EstadoModel> toCollectionModel(Iterable<? extends Estado> entities) {
        return super.toCollectionModel(entities).add(giLinks.linkToEstados());
    }
}
