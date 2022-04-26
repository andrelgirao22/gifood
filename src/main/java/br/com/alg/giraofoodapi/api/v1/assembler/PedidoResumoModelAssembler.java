package br.com.alg.giraofoodapi.api.v1.assembler;

import br.com.alg.giraofoodapi.api.v1.GiLinksV1;
import br.com.alg.giraofoodapi.api.v1.controller.PedidoController;
import br.com.alg.giraofoodapi.api.v1.model.dto.PedidoResumoModel;
import br.com.alg.giraofoodapi.core.security.GiSecurity;
import br.com.alg.giraofoodapi.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;


@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GiLinksV1 giLinks;

    @Autowired
    private GiSecurity giSecurity;

    public PedidoResumoModelAssembler() {
        super(PedidoController.class, PedidoResumoModel.class);
    }

    @Override
    public PedidoResumoModel toModel(Pedido pedido) {

        PedidoResumoModel pedidoResumoModel = createModelWithId(pedido.getId(), pedido);
        modelMapper.map(pedido, pedidoResumoModel);

        if(giSecurity.podePesquisarPedidos()) {
            pedidoResumoModel.add(giLinks.linkToPedidos("pedidos"));
        }

        if(giSecurity.podeConsultarRestaurantes()) {
            pedidoResumoModel.getRestaurante().add(giLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        }

        if(giSecurity.podeConsultarUsuariosGruposPermissoes()) {
            pedidoResumoModel.getCliente().add(giLinks.linkToUsuario(pedido.getCliente().getId()));
        }

        return pedidoResumoModel;
    }


}
