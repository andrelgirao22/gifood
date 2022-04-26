package br.com.alg.giraofoodapi.api.v1.assembler;

import br.com.alg.giraofoodapi.api.v1.GiLinksV1;
import br.com.alg.giraofoodapi.api.v1.controller.EstadoController;
import br.com.alg.giraofoodapi.core.security.GiSecurity;
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

    @Autowired
    private GiSecurity giSecurity;

    public EstadoModelAssembler() {
        super(EstadoController.class, EstadoModel.class);
    }

    @Override
    public EstadoModel toModel(Estado estado) {

        EstadoModel estadoModel = createModelWithId(estado.getId(), estado);
        modelMapper.map(estado, estadoModel);
        if(giSecurity.podeConsultarEstados()) {
            estadoModel.add(giLinks.linkToEstados("estados"));
            estadoModel.add(giLinks.linkToEstado(estado.getId(), "estados"));
        }
        return estadoModel;
    }

    @Override
    public CollectionModel<EstadoModel> toCollectionModel(Iterable<? extends Estado> entities) {
        CollectionModel<EstadoModel> collection = super.toCollectionModel(entities);
        if(giSecurity.podeConsultarEstados()) {
            collection.add(giLinks.linkToEstados());
        }
        return collection;
    }
}
