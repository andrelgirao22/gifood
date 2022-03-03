package br.com.alg.giraofoodapi.api.assembler;

import br.com.alg.giraofoodapi.api.GiLinks;
import br.com.alg.giraofoodapi.api.controller.PedidoController;
import br.com.alg.giraofoodapi.api.controller.RestauranteController;
import br.com.alg.giraofoodapi.api.controller.UsuarioController;
import br.com.alg.giraofoodapi.api.model.dto.PedidoResumoModel;
import br.com.alg.giraofoodapi.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.stereotype.Component;


@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GiLinks giLinks;

    public PedidoResumoModelAssembler() {
        super(PedidoController.class, PedidoResumoModel.class);
    }

    @Override
    public PedidoResumoModel toModel(Pedido pedido) {

        PedidoResumoModel pedidoResumoModel = createModelWithId(pedido.getId(), pedido);
        modelMapper.map(pedido, pedidoResumoModel);

        pedidoResumoModel.add(giLinks.linkToPedidos());
        pedidoResumoModel.getRestaurante().add(giLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        pedidoResumoModel.getCliente().add(giLinks.linkToUsuario(pedido.getCliente().getId()));

        return pedidoResumoModel;
    }


}
