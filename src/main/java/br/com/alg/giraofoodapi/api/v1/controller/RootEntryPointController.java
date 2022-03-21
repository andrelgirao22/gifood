package br.com.alg.giraofoodapi.api.v1.controller;

import br.com.alg.giraofoodapi.api.v1.GiLinksV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @Autowired
    private GiLinksV1 giLinks;

    @GetMapping
    public RootEntryPointModel root() {
        var rootEntryPointModel = new RootEntryPointModel();

        rootEntryPointModel.add(giLinks.linkToCozinhas("cozinhas"));
        rootEntryPointModel.add(giLinks.linkToPedidos("pedidos"));
        rootEntryPointModel.add(giLinks.linkToRestaurantes("restaurantes"));
        rootEntryPointModel.add(giLinks.linkToGrupos("grupos"));
        rootEntryPointModel.add(giLinks.linkToUsuarios("usuarios"));
        rootEntryPointModel.add(giLinks.linkToPermissoes("permissoes"));
        rootEntryPointModel.add(giLinks.linkToFormasPagamento("formas-pagamento"));
        rootEntryPointModel.add(giLinks.linkToEstados("estados"));
        rootEntryPointModel.add(giLinks.linkToCidades("cidades"));
        rootEntryPointModel.add(giLinks.linkToEstatisticas("estatisticas"));

        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
    }
}
