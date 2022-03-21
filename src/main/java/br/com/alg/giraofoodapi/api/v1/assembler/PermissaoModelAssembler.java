package br.com.alg.giraofoodapi.api.v1.assembler;

import br.com.alg.giraofoodapi.api.v1.GiLinksV1;
import br.com.alg.giraofoodapi.api.v1.model.dto.PermissaoModel;
import br.com.alg.giraofoodapi.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PermissaoModelAssembler implements RepresentationModelAssembler<Permissao, PermissaoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GiLinksV1 giLinks;

    @Override
    public PermissaoModel toModel(Permissao permissao) {
        PermissaoModel permissaoModel = modelMapper.map(permissao, PermissaoModel.class);
        return permissaoModel;
    }

    @Override
    public CollectionModel<PermissaoModel> toCollectionModel(Iterable<? extends Permissao> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(giLinks.linkToPermissoes());
    }
}
