package br.com.alg.giraofoodapi.api.v1.assembler;

import br.com.alg.giraofoodapi.api.v1.GiLinksV1;
import br.com.alg.giraofoodapi.api.v1.model.dto.PedidoModel;
import br.com.alg.giraofoodapi.api.v1.controller.PedidoController;
import br.com.alg.giraofoodapi.core.security.GiSecurity;
import br.com.alg.giraofoodapi.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GiLinksV1 giLinks;

    @Autowired
    private GiSecurity giSecurity;

    public PedidoModelAssembler() {
        super(PedidoController.class, PedidoModel.class);
    }

    @Override
    public PedidoModel toModel(Pedido pedido) {

        PedidoModel pedidoModel = createModelWithId(pedido.getId(), pedido);
        modelMapper.map(pedido, pedidoModel);

        if(giSecurity.podePesquisarPedidos()) {
            //pedidoModel.add(linkTo(PedidoController.class).withRel("pedidos"));
            pedidoModel.add(giLinks.linkToPedidos("pedidos"));
        }

        if(giSecurity.podeGerenciarPedidos(pedido.getCodigo())) {
            if(pedido.podeSerConfirmado()) {
                pedidoModel.add(giLinks.linkToConfirmacaoPedido(pedidoModel.getCodigo(), "confirmar"));
            }

            if(pedido.podeSerCancelado()) {
                pedidoModel.add(giLinks.linkToCancelarPedido(pedidoModel.getCodigo(), "cancelar"));
            }

            if(pedido.podeSerEntregue())  {
                pedidoModel.add(giLinks.linkToEntregaPedido(pedidoModel.getCodigo(), "entregar"));
            }
        }

        pedidoModel.getRestaurante()
                .add(giLinks.linkToRestaurante(pedidoModel.getRestaurante().getId()));

        if(giSecurity.podeConsultarUsuariosGruposPermissoes()) {
            pedidoModel.getCliente().add(giLinks.linkToUsuario(pedido.getCliente().getId()));
        }

        if(giSecurity.podeConsultarFormasPagamento()) {
            pedidoModel.getFormaPagamento().add(giLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
        }

        if(giSecurity.podeConsultarRestaurantes()) {
            pedidoModel.getItens().forEach(item -> {
                item.add(giLinks.linkToProduto(pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto"));
            });
        }
        return pedidoModel;
    }
}