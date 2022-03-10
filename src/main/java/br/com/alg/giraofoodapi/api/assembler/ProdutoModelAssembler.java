package br.com.alg.giraofoodapi.api.assembler;

import br.com.alg.giraofoodapi.api.GiLinks;
import br.com.alg.giraofoodapi.api.controller.RestauranteProdutoController;
import br.com.alg.giraofoodapi.api.model.dto.ProdutoModel;
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
    private GiLinks giLinks;

    public ProdutoModelAssembler() {
        super(RestauranteProdutoController.class, ProdutoModel.class);
    }

    public ProdutoModel toModel(Produto produto) {

        ProdutoModel produtoModel = createModelWithId(produto.getId(), produto, produto.getRestaurante().getId());
        modelMapper.map(produto, produtoModel);
        produtoModel.add(giLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));

        return produtoModel;
    }
}
