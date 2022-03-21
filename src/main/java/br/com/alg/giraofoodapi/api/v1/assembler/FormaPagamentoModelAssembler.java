package br.com.alg.giraofoodapi.api.v1.assembler;

import br.com.alg.giraofoodapi.api.v1.GiLinksV1;
import br.com.alg.giraofoodapi.api.v1.controller.FormaPagamentoController;
import br.com.alg.giraofoodapi.domain.model.FormaPagamento;
import br.com.alg.giraofoodapi.api.v1.model.dto.FormaPagamentoModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoModelAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FormaPagamentoModelAssembler assembler;

    @Autowired
    private GiLinksV1 giLinks;

    public FormaPagamentoModelAssembler() {
        super(FormaPagamentoController.class, FormaPagamentoModel.class);
    }

    @Override
    public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
        FormaPagamentoModel formaPagamentoModel = createModelWithId(formaPagamento.getId(), formaPagamento);
        modelMapper.map(formaPagamento, formaPagamentoModel);
        formaPagamentoModel.add(giLinks.linkToFormasPagamento("formas-pagamento"));
        return formaPagamentoModel;
    }

    @Override
    public CollectionModel<FormaPagamentoModel> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
        return super.toCollectionModel(entities).add(giLinks.linkToFormasPagamento("formas-pagamento"));
    }
}
