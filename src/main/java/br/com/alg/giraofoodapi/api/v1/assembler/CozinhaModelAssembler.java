package br.com.alg.giraofoodapi.api.v1.assembler;

import br.com.alg.giraofoodapi.api.v1.GiLinksV1;
import br.com.alg.giraofoodapi.api.v1.controller.CozinhaController;
import br.com.alg.giraofoodapi.core.security.GiSecurity;
import br.com.alg.giraofoodapi.domain.model.Cozinha;
import br.com.alg.giraofoodapi.api.v1.model.dto.CozinhaModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaModelAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CozinhaModelAssembler assembler;

    @Autowired
    private GiLinksV1 giLinks;

    @Autowired
    private GiSecurity giSecurity;

    public CozinhaModelAssembler() {
        super(CozinhaController.class, CozinhaModel.class);
    }

    @Override
    public CozinhaModel toModel(Cozinha cozinha) {

        CozinhaModel cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModel);

        if(giSecurity.podeConsultarCozinhas()) {
            cozinhaModel.add(giLinks.linkToCozinhas("cozinhas"));
        }

        return cozinhaModel;
    }

}
