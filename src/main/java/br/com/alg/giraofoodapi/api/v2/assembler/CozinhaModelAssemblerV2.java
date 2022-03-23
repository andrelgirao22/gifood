package br.com.alg.giraofoodapi.api.v2.assembler;


import br.com.alg.giraofoodapi.api.v1.model.dto.CozinhaModel;
import br.com.alg.giraofoodapi.api.v2.GiLinksV2;
import br.com.alg.giraofoodapi.api.v2.controller.CozinhaControllerV2;
import br.com.alg.giraofoodapi.api.v2.model.CozinhaModelV2;
import br.com.alg.giraofoodapi.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaModelAssemblerV2 extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModelV2> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CozinhaModelAssemblerV2 assembler;

    @Autowired
    private GiLinksV2 giLinks;

    public CozinhaModelAssemblerV2() {
        super(CozinhaControllerV2.class, CozinhaModelV2.class);
    }

    @Override
    public CozinhaModelV2 toModel(Cozinha cozinha) {

        CozinhaModelV2 cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModel);

        cozinhaModel.add(giLinks.linkToCozinhas("cozinhas"));

        return cozinhaModel;
    }

}
