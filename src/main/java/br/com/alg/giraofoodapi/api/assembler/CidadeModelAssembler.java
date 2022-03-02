package br.com.alg.giraofoodapi.api.assembler;

import br.com.alg.giraofoodapi.api.controller.CidadeController;
import br.com.alg.giraofoodapi.api.controller.EstadoController;
import br.com.alg.giraofoodapi.domain.model.Cidade;
import br.com.alg.giraofoodapi.api.model.dto.CidadeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CidadeModelAssembler assembler;

    public CidadeModelAssembler() {
        super(CidadeController.class, CidadeDTO.class);
    }

    @Override
    public CollectionModel<CidadeDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(CidadeController.class).withSelfRel());
    }

    @Override
    public CidadeDTO toModel(Cidade cidade) {
        CidadeDTO cidadeModel = createModelWithId(cidade.getId(), cidade);
        modelMapper.map(cidade, cidadeModel);

        cidadeModel.add(linkTo(methodOn(CidadeController.class).listar())
                .withRel("cidades")
        );
        //cidadeDTO.add(Link.of("http://localhost:8080/cidades", IanaLinkRelations.COLLECTION));

        cidadeModel.getEstado().add(linkTo(methodOn(EstadoController.class)
                .buscar(cidadeModel.getEstado().getId()))
                .withSelfRel());

        cidadeModel.getEstado().add(linkTo(EstadoController.class)
                .withRel("estados"));

        return cidadeModel;
    }
}
