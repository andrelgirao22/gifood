package br.com.alg.giraofoodapi.api.assembler;

import br.com.alg.giraofoodapi.api.GiLinks;
import br.com.alg.giraofoodapi.api.controller.UsuarioController;
import br.com.alg.giraofoodapi.api.controller.UsuarioGrupoController;
import br.com.alg.giraofoodapi.api.model.dto.UsuarioModel;
import br.com.alg.giraofoodapi.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.stereotype.Component;

@Component
public class UsuarioModelAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UsuarioModelAssembler assembler;

    @Autowired
    private GiLinks giLinks;

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
