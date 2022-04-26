package br.com.alg.giraofoodapi.api.v1.assembler;

import br.com.alg.giraofoodapi.api.v1.GiLinksV1;
import br.com.alg.giraofoodapi.api.v1.controller.RestauranteProdutoController;
import br.com.alg.giraofoodapi.api.v1.model.dto.ProdutoModel;
import br.com.alg.giraofoodapi.core.security.GiSecurity;
import br.com.alg.giraofoodapi.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProdutoModelAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProdutoModelAssembler assembler;

    @Autowired
    private GiLinksV1 giLinks;

    @Autowired
    private GiSecurity giSecurity;

    public ProdutoModelAssembler() {
        super(RestauranteProdutoController.class, ProdutoModel.class);
    }

    public ProdutoModel toModel(Produto produto) {

        ProdutoModel produtoModel = createModelWithId(produto.getId(), produto, produto.getRestaurante().getId());
        modelMapper.map(produto, produtoModel);

        if(giSecurity.podeConsultarRestaurantes()) {
            produtoModel.add(giLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));

            produtoModel.add(giLinks.linkToFotoProduto(
                    produto.getRestaurante().getId(), produto.getId(), "foto"));
        }

        return produtoModel;
    }
}
