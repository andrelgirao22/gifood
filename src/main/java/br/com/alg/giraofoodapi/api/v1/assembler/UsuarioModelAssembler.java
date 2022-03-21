package br.com.alg.giraofoodapi.api.v1.assembler;

import br.com.alg.giraofoodapi.api.v1.GiLinksV1;
import br.com.alg.giraofoodapi.api.v1.controller.UsuarioController;
import br.com.alg.giraofoodapi.api.v1.model.dto.UsuarioModel;
import br.com.alg.giraofoodapi.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UsuarioModelAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UsuarioModelAssembler assembler;

    @Autowired
    private GiLinksV1 giLinks;

    public UsuarioModelAssembler() {
        super(UsuarioController.class, UsuarioModel.class);
    }


    @Override
    public UsuarioModel toModel(Usuario usuario) {

        UsuarioModel usuarioModel = createModelWithId(usuario.getId(), usuario);
        modelMapper.map(usuario, usuarioModel);

        usuarioModel.add(giLinks.linkToUsuarios("usuarios"));
        usuarioModel.add(giLinks.linkToGruposUsuario(usuarioModel.getId(), "grupos-usuarios"));

        return usuarioModel;
    }

    @Override
    public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> usuarios) {
        return super.toCollectionModel(usuarios)
                .add(giLinks.linkToUsuarios());
    }
}
